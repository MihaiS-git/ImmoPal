package ubb.graduation24.immopal.security_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ubb.graduation24.immopal.grpc.PersonOuterClass;
import ubb.graduation24.immopal.grpc.PersonServiceRPCGrpc;
import ubb.graduation24.immopal.security_service.domain.Role;
import ubb.graduation24.immopal.security_service.domain.User;
import ubb.graduation24.immopal.security_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.security_service.model.UserDto;
import ubb.graduation24.immopal.security_service.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PersonServiceRPCGrpc.PersonServiceRPCBlockingStub personServiceStub;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUsers_ShouldReturnUsers() {
        // Arrange
        User user = new User();
        when(userRepository.findAll()).thenReturn(List.of(user));

        // Act
        List<User> users = userService.getUsers();

        // Assert
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
    }

    @Test
    void getUsers_ShouldThrowExceptionWhenNoUsersFound() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> userService.getUsers());
        assertEquals("Users not found.", thrown.getMessage());
    }

    @Test
    void getUserById_ShouldReturnUser() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void getUserById_ShouldThrowExceptionWhenUserNotFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(userId));
        assertEquals("User not found.", thrown.getMessage());
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() {
        // Arrange
        Long userId = 1L;
        User existingUser = new User();
        UserDto userDto = UserDto.builder().role(Role.ADMIN).build();
        User updatedUser = new User();
        updatedUser.setRole(userDto.getRole());
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // Act
        User result = userService.updateUser(userId, userDto);

        // Assert
        assertNotNull(result);
        assertEquals(userDto.getRole(), result.getRole());
    }

    @Test
    void updateUser_ShouldThrowExceptionWhenUserNotFound() {
        // Arrange
        Long userId = 1L;
        UserDto userDto = UserDto.builder().build();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(userId, userDto));
        assertEquals("User not found.", thrown.getMessage());
    }

    @Test
    void deleteUser_ShouldDeleteUser() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        user.setPersonId(2L);

        // Prepare the response to simulate successful deletion in gRPC
        PersonOuterClass.DeletePersonResponse response = PersonOuterClass.DeletePersonResponse.newBuilder()
                .setMessage("Person deleted successfully")
                .build();

        // Mock repository and gRPC stub behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(personServiceStub.deletePersonById(any(PersonOuterClass.DeletePersonRequest.class))).thenReturn(response);

        // Act
        userService.deleteUser(userId);

        // Assert
        // Verify deleteById was called with the correct userId
        verify(userRepository).deleteById(userId);
    }

    @Test
    void deleteUser_ShouldThrowExceptionWhenPersonDeletionFails() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setPersonId(2L);
        PersonOuterClass.DeletePersonResponse response = PersonOuterClass.DeletePersonResponse.newBuilder()
                .setMessage("Person not found")
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(personServiceStub.deletePersonById(any(PersonOuterClass.DeletePersonRequest.class))).thenReturn(response);

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(userId));
        assertEquals("Linked Person could not be deleted. Person not found", thrown.getMessage());
    }

    @Test
    void deleteUser_ShouldThrowExceptionWhenUserNotFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(userId));
        assertEquals("User not found.", thrown.getMessage());
    }

    @Test
    void getUsersByRole_ShouldReturnUsers() {
        // Arrange
        String roleString = "CUSTOMER";
        Role role = Role.CUSTOMER;
        User user = new User();
        when(userRepository.findByRole(role)).thenReturn(List.of(user));

        // Act
        List<User> users = userService.getUsersByRole(roleString);

        // Assert
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
    }

    @Test
    void getUsersByRole_ShouldThrowExceptionWhenRoleIsNotSupported() {
        // Arrange
        String roleString = "INVALID_ROLE";

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> userService.getUsersByRole(roleString));
        assertEquals("Role is not supported.", thrown.getMessage());
    }

    @Test
    void getUserByEmail_ShouldReturnUser() {
        // Arrange
        String email = "test@example.com";
        User user = new User();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserByEmail(email);

        // Assert
        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void getUserByEmail_ShouldThrowExceptionWhenUserNotFound() {
        // Arrange
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> userService.getUserByEmail(email));
        assertEquals("User not found.", thrown.getMessage());
    }
}

