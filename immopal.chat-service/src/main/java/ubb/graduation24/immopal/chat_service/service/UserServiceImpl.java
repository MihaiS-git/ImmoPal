package ubb.graduation24.immopal.chat_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.chat_service.domain.Status;
import ubb.graduation24.immopal.chat_service.domain.ChatUser;
import ubb.graduation24.immopal.chat_service.model.ConnectUserRequest;
import ubb.graduation24.immopal.chat_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.chat_service.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public ChatUser saveUser(ChatUser user) {
        log.info("Saving user --- method entered");
        try {
            user.setStatus(Status.ONLINE);
            ChatUser savedUser = userRepository.save(user);
            messagingTemplate.convertAndSend("/topic/user/updates", savedUser);
            log.info("Saving user --- method exited");
            return savedUser;
        } catch (Exception e) {
            log.error("Error saving user: {}", e.getMessage());
            throw new RuntimeException("Could not save user", e);
        }
    }

    @Override
    public ChatUser disconnect(String email) {
        log.info("disconnect() --- method entered");
        ChatUser storedUser = userRepository.findByEmail(email);
        if (storedUser == null) {
            throw new ResourceNotFoundException("User not found");
        }
        storedUser.setStatus(Status.OFFLINE);
        return userRepository.save(storedUser);
    }

    @Override
    public List<ChatUser> findAllUsers() {
        log.info("findAllUsers() --- method entered");
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            log.error("Error finding all users: {}", e.getMessage());
            throw new ResourceNotFoundException("Could not retrieve users", e);
        }
    }

    @Override
    public ChatUser findByEmail(String email) {
        log.info("findByEmail() --- method entered");
        ChatUser user = userRepository.findByEmail(email);
        if (user == null) {
            log.error("Error finding user by email: User not found");
            return null;
        }
        return user;
    }

    @Override
    public ChatUser connectUser(ConnectUserRequest request) {
        log.debug("Connecting user: {}", request);
        ChatUser user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            log.debug("User not found, creating new user.");
            user = ChatUser.builder()
                    .email(request.getEmail())
                    .fullName(request.getFullName())
                    .status(Status.valueOf(request.getStatus()))
                    .pictureUrl(request.getPictureUrl())
                    .build();
            user = userRepository.save(user);
        } else {
            log.debug("User found, updating status.");
            user.setStatus(Status.valueOf(request.getStatus()));
            user = userRepository.save(user);
        }
        messagingTemplate.convertAndSend("/topic/user/updates", user);
        log.debug("User connected and message sent to topic.");
        return user;
    }


    @Override
    public ChatUser findById(String id) {
        log.info("findById() --- method entered");
        Optional<ChatUser> user = userRepository.findById(id);
        if (user.isEmpty()) {
            log.error("Error finding user by ID: User not found");
            throw new ResourceNotFoundException("User not found");
        }
        return user.get();
    }
}
