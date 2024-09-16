package ubb.graduation24.immopal.security_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ubb.graduation24.immopal.security_service.domain.PasswordResetToken;
import ubb.graduation24.immopal.security_service.domain.User;
import ubb.graduation24.immopal.security_service.model.*;
import ubb.graduation24.immopal.security_service.repository.PasswordResetTokenRepository;
import ubb.graduation24.immopal.security_service.repository.UserRepository;
import ubb.graduation24.immopal.security_service.service.AuthenticationService;
import ubb.graduation24.immopal.security_service.service.IUserService;
import ubb.graduation24.immopal.security_service.service.RabbitMQSender;
import ubb.graduation24.immopal.security_service.service.TokenGenerator;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticateUser {
    private static final String RESET_LINK = "http://localhost:4200/recover/reset-password/";

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private RabbitMQSender rabbitMQSender;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PatchMapping("/recover-password/{email}")
    public ResponseEntity<?> changePasswordByEmail(@PathVariable String email) {
        log.info("changePasswordByEmail() --- endpoint");
        try {
            User user = userService.getUserByEmail(email);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            String token = TokenGenerator.generateToken();

            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setToken(token);
            resetToken.setUser(user);
            resetToken.setExpiryDate(LocalDateTime.now().plusHours(24));
            passwordResetTokenRepository.save(resetToken);

            String resetLink = RESET_LINK + token;
            log.info("Password reset confirmation email reset link: {}", resetLink);

            EmailMessageRequest request = EmailMessageRequest.builder()
                    .subject("ImmoPal reset password confirmation")
                    .to(email)
                    .body(resetLink)
                    .isHtml(false)
                    .build();
            rabbitMQSender.sendEmail(request);
            log.info("AuthenticateUser changePasswordByEmail() Reset password confirmation email sent");

            Map<String, String> response = new HashMap<>();
            response.put("message", "The Password reset link was sent to your email.");

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send the reset email.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token,
                                           @RequestBody ChangePasswordRequest request) {
        log.info("Reset password request received: {}", request);
        Optional<PasswordResetToken> optional = passwordResetTokenRepository.findByToken(token);
        if (optional.isEmpty()) {
            log.info("Password reset token not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
        }

        PasswordResetToken resetToken = optional.get();
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            log.info("Password reset token expired");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token has expired");
        }

        User user = resetToken.getUser();
        log.info("User retrieved from token");
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            log.info("Password does not match");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password does not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        log.info("User updated with new password");

        passwordResetTokenRepository.delete(resetToken);
        log.info("Password reset token deleted");

        Map<String, String> response = new HashMap<>();
        response.put("message", "Password successfully changed.");

        log.info("Password changed successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
