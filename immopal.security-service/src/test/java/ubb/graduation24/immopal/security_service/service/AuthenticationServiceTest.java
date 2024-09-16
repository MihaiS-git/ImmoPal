package ubb.graduation24.immopal.security_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import ubb.graduation24.immopal.grpc.PersonOuterClass;
import ubb.graduation24.immopal.grpc.PersonServiceRPCGrpc;
import ubb.graduation24.immopal.security_service.domain.Role;
import ubb.graduation24.immopal.security_service.domain.User;
import ubb.graduation24.immopal.security_service.exception.InvalidAuthException;
import ubb.graduation24.immopal.security_service.exception.InvalidRegistrationException;
import ubb.graduation24.immopal.security_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.security_service.model.AuthenticationRequest;
import ubb.graduation24.immopal.security_service.model.AuthenticationResponse;
import ubb.graduation24.immopal.security_service.model.RegisterRequest;
import ubb.graduation24.immopal.security_service.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PersonServiceRPCGrpc.PersonServiceRPCBlockingStub personServiceStub;

    @InjectMocks
    private AuthenticationService authenticationService;

    private RegisterRequest registerRequest;
    private AuthenticationRequest authenticationRequest;
    private User user;
    private String jwtToken;
    private PersonOuterClass.Person personProto;

    @BeforeEach
    void setUp() {
        registerRequest = RegisterRequest.builder()
                .email("test@example.com")
                .password("password")
                .firstName("First")
                .lastName("Last")
                .phoneNumber("1234567890")
                .dateOfBirth(LocalDate.parse("1990-12-12"))
                .address("Address")
                .pictureUrl("test")
                .build();

        authenticationRequest = new AuthenticationRequest("test@example.com", "password");
        user = User.builder()
                .userId(1L)
                .email("test@example.com")
                .password("encodedPassword")
                .role(Role.CUSTOMER)
                .personId(1L)
                .build();
        jwtToken = "jwtToken";
        personProto = PersonOuterClass.Person.newBuilder()
                .setId(1L)
                .setFirstName("First")
                .setLastName("Last")
                .setPhoneNumber("1234567890")
                .setAddress("Address")
                .build();
    }

    @Test
    void register_ShouldReturnAuthenticationResponse_WhenSuccessful() {
        when(personServiceStub.addPerson(any(PersonOuterClass.AddPersonRequest.class)))
                .thenReturn(PersonOuterClass.AddPersonResponse.newBuilder().setPerson(personProto).build());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(personServiceStub.updatePerson(any(PersonOuterClass.UpdatePersonRequest.class)))
                .thenReturn(PersonOuterClass.UpdatePersonResponse.newBuilder().setPerson(personProto).build());
        when(jwtService.generateToken(any(User.class))).thenReturn(jwtToken);

        AuthenticationResponse response = authenticationService.register(registerRequest);

        assertEquals(jwtToken, response.getToken());
    }


    @Test
    void register_ShouldThrowInvalidRegistrationException_WhenGrpcResponseHasNoPerson() {
        when(personServiceStub.addPerson(any(PersonOuterClass.AddPersonRequest.class)))
                .thenReturn(PersonOuterClass.AddPersonResponse.newBuilder().build());

        assertThrows(InvalidRegistrationException.class, () -> authenticationService.register(registerRequest));
    }

    @Test
    void register_ShouldThrowResourceNotFoundException_WhenGrpcUpdatePersonFails() {
        when(personServiceStub.addPerson(any(PersonOuterClass.AddPersonRequest.class)))
                .thenReturn(PersonOuterClass.AddPersonResponse.newBuilder().setPerson(personProto).build());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(personServiceStub.updatePerson(any(PersonOuterClass.UpdatePersonRequest.class)))
                .thenReturn(PersonOuterClass.UpdatePersonResponse.newBuilder().build());

        assertThrows(ResourceNotFoundException.class, () -> authenticationService.register(registerRequest));
    }

    @Test
    void authenticate_ShouldReturnAuthenticationResponse_WhenCredentialsAreValid() {
        when(userRepository.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(jwtToken);

        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);

        assertEquals(jwtToken, response.getToken());
    }

    @Test
    void authenticate_ShouldThrowInvalidAuthException_WhenCredentialsAreInvalid() {
        doThrow(BadCredentialsException.class).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(InvalidAuthException.class, () -> authenticationService.authenticate(authenticationRequest));
    }

    @Test
    void authenticate_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authenticationService.authenticate(authenticationRequest));
    }
}
