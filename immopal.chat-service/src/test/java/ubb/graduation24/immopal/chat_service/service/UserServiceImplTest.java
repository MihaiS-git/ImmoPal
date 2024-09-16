package ubb.graduation24.immopal.chat_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ubb.graduation24.immopal.chat_service.domain.ChatUser;
import ubb.graduation24.immopal.chat_service.domain.Status;
import ubb.graduation24.immopal.chat_service.model.ConnectUserRequest;
import ubb.graduation24.immopal.chat_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.chat_service.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    private ChatUser user;

    @BeforeEach
    void setUp() {
        user = ChatUser.builder()
                .email("test@example.com")
                .fullName("Test User")
                .status(Status.ONLINE)
                .pictureUrl("http://example.com/pic.jpg")
                .build();
    }

    @Test
    void saveUser_Success() {
        // Arrange
        when(userRepository.save(any(ChatUser.class))).thenReturn(user);

        // Act
        ChatUser savedUser = userService.saveUser(user);

        // Assert
        assertNotNull(savedUser);
        assertEquals(Status.ONLINE, savedUser.getStatus());
        verify(userRepository, times(1)).save(any(ChatUser.class));
        verify(messagingTemplate, times(1)).convertAndSend(anyString(), any(ChatUser.class));
    }

    @Test
    void saveUser_Exception() {
        // Arrange
        when(userRepository.save(any(ChatUser.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.saveUser(user);
        });
        assertEquals("Could not save user", thrown.getMessage());
        verify(userRepository, times(1)).save(any(ChatUser.class));
        verify(messagingTemplate, never()).convertAndSend(anyString(), any(ChatUser.class));
    }

    @Test
    void disconnect_UserExists() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        when(userRepository.save(any(ChatUser.class))).thenReturn(user);

        // Act
        ChatUser disconnectedUser = userService.disconnect(user.getEmail());

        // Assert
        assertNotNull(disconnectedUser);
        assertEquals(Status.OFFLINE, disconnectedUser.getStatus());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(1)).save(any(ChatUser.class));
    }

    @Test
    void disconnect_UserNotFound() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            userService.disconnect(user.getEmail());
        });
        assertEquals("User not found", thrown.getMessage());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, never()).save(any(ChatUser.class));
    }

    @Test
    void disconnect_Exception() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenThrow(new RuntimeException("Could not disconnect user"));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.disconnect(user.getEmail());
        });
        assertEquals("Could not disconnect user", thrown.getMessage());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void findAllUsers_Success() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        // Act
        List<ChatUser> users = userService.findAllUsers();

        // Assert
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findAllUsers_NoUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<ChatUser> users = userService.findAllUsers();

        // Assert
        assertNotNull(users);
        assertTrue(users.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findAllUsers_Exception() {
        // Arrange
        when(userRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.findAllUsers();
        });
        assertEquals("Could not retrieve users", thrown.getMessage());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findByEmail_Success() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(user);

        // Act
        ChatUser foundUser = userService.findByEmail(user.getEmail());

        // Assert
        assertNotNull(foundUser);
        assertEquals(user, foundUser);
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void findByEmail_NotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        // Act
        ChatUser foundUser = userService.findByEmail(email);

        // Assert
        assertNull(foundUser);
        verify(userRepository, times(1)).findByEmail(anyString());
    }


    @Test
    void findByEmail_Exception() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenThrow(new RuntimeException("Could not find user by email"));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.findByEmail(user.getEmail());
        });
        assertEquals("Could not find user by email", thrown.getMessage());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void findById_Success() {
        // Arrange
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        // Act
        ChatUser foundUser = userService.findById(user.getEmail());

        // Assert
        assertNotNull(foundUser);
        assertEquals(user, foundUser);
        verify(userRepository, times(1)).findById(anyString());
    }

    @Test
    void findById_NotFound() {
        // Arrange
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            userService.findById(user.getEmail());
        });
        assertEquals("User not found", thrown.getMessage());
        verify(userRepository, times(1)).findById(anyString());
    }

    @Test
    void findById_Exception() {
        // Arrange
        when(userRepository.findById(anyString())).thenThrow(new RuntimeException("Could not find user by ID"));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.findById(user.getEmail());
        });
        assertEquals("Could not find user by ID", thrown.getMessage());
        verify(userRepository, times(1)).findById(anyString());
    }

    @Test
    void connectUser_NewUser() {
        // Arrange
        ConnectUserRequest request = ConnectUserRequest.builder()
                .email("newuser@example.com")
                .fullName("New User")
                .status("ONLINE")
                .pictureUrl("http://example.com/newpic.jpg")
                .build();

        ChatUser newUser = ChatUser.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .status(Status.ONLINE)
                .pictureUrl(request.getPictureUrl())
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(userRepository.save(any(ChatUser.class))).thenReturn(newUser);

        // Act
        ChatUser connectedUser = userService.connectUser(request);

        // Assert
        assertNotNull(connectedUser);
        assertEquals(newUser.getEmail(), connectedUser.getEmail());
        assertEquals(Status.ONLINE, connectedUser.getStatus());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(1)).save(any(ChatUser.class));
        verify(messagingTemplate, times(1)).convertAndSend("/topic/user/updates", connectedUser);
    }

    @Test
    void connectUser_ExistingUser() {
        // Arrange
        ConnectUserRequest request = ConnectUserRequest.builder()
                .email("existinguser@example.com")
                .status("OFFLINE")
                .build();

        ChatUser existingUser = ChatUser.builder()
                .email(request.getEmail())
                .status(Status.ONLINE) // Assume the existing status
                .build();

        // Mock repository responses
        when(userRepository.findByEmail(anyString())).thenReturn(existingUser);
        when(userRepository.save(any(ChatUser.class))).thenReturn(existingUser);

        // Act
        ChatUser updatedUser = userService.connectUser(request);

        // Assert
        assertNotNull(updatedUser);
        assertEquals(request.getEmail(), updatedUser.getEmail());
        assertEquals(Status.OFFLINE, updatedUser.getStatus());

        // Verify interactions
        verify(userRepository, times(1)).findByEmail(anyString()); // Check that findByEmail is called once
        verify(userRepository, times(1)).save(any(ChatUser.class)); // Check that save is called once
        verify(messagingTemplate, times(1)).convertAndSend("/topic/user/updates", updatedUser); // Check messaging
    }



    @Test
    void connectUser_Exception() {
        // Arrange
        ConnectUserRequest request = ConnectUserRequest.builder()
                .email(user.getEmail())
                .status("ONLINE")
                .build();
        when(userRepository.findByEmail(anyString())).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.connectUser(request);
        });
        assertEquals("Database error", thrown.getMessage());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, never()).save(any(ChatUser.class));
        verify(messagingTemplate, never()).convertAndSend(anyString(), any(ChatUser.class));
    }
}
