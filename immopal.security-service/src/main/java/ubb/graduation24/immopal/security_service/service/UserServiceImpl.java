package ubb.graduation24.immopal.security_service.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.grpc.PersonOuterClass;
import ubb.graduation24.immopal.grpc.PersonServiceRPCGrpc;
import ubb.graduation24.immopal.security_service.domain.User;
import ubb.graduation24.immopal.security_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.security_service.model.UserDto;
import ubb.graduation24.immopal.security_service.repository.UserRepository;
import ubb.graduation24.immopal.security_service.domain.Role;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class UserServiceImpl implements IUserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PersonServiceRPCGrpc.PersonServiceRPCBlockingStub personServiceStub;

    @Override
    public List<User> getUsers() {
        log.info("getUsers() --- method entered");
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            log.info("getUsers() --- no users found");
            throw new ResourceNotFoundException("Users not found.");
        }
        log.info("getUsers() size = {}", users.size());
        return users;
    }

    @Override
    public User getUserById(Long id) {
        log.info("getUserById() --- method entered --- userId={}", id);
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            log.info("getUserById() --- found user {}", user);
            return user;
        } else {
            log.info("getUserById() --- no user found");
            throw new ResourceNotFoundException("User not found.");
        }
    }

    @Override
    public User updateUser(Long id, UserDto userDto) {
        log.info("updateUser() --- method entered");
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            User userToUpdate = optional.get();
            log.info("updateUser() --- found userPersonDto {}", userToUpdate);
            userToUpdate.setRole(userDto.getRole());
            userToUpdate.setAccountNonExpired(true);
            userToUpdate.setAccountNonLocked(true);
            userToUpdate.setCredentialsNonExpired(true);
            userToUpdate.setEnabled(true);

            User savedUser = userRepository.save(userToUpdate);

            log.info("updateUser() -> User --- updated user {}", savedUser);
            return savedUser;
        } else {
            log.info("updateUser() --- no userPersonDto found");
            throw new ResourceNotFoundException("User not found.");
        }
    }

    @Override
    public void deleteUser(Long id) {
        log.info("deleteUser() --- method entered");
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            Long personId = user.getPersonId();

            PersonOuterClass.DeletePersonRequest request = PersonOuterClass.DeletePersonRequest.newBuilder()
                    .setId(personId)
                    .build();

            PersonOuterClass.DeletePersonResponse response = personServiceStub.deletePersonById(request);

            if (response.getMessage().equals("Person deleted successfully")) {
                log.info("deleteUser() -> Person --- deleted person id {}", personId);
                log.info("deleteUser() -> User --- deleted user {}", user);
                userRepository.deleteById(user.getUserId());
            } else {
                log.info("deleteUser() -> Person not found");
                throw new ResourceNotFoundException("Linked Person could not be deleted. Person not found");
            }
        } else {
            log.info("deleteUser() --- no user found");
            throw new ResourceNotFoundException("User not found.");
        }
    }

    @Override
    public List<User> getUsersByRole(String roleString) {
        log.info("getUsersByRole() --- method entered");

        Role role;
        try {
            role = Role.valueOf(roleString.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.info("getUsersByRole() --- Role is not supported");
            throw new ResourceNotFoundException("Role is not supported.");
        }

        List<User> users = userRepository.findByRole(role);
        if (users.isEmpty()) {
            log.info("getUsersByRole() --- no users found");
            throw new ResourceNotFoundException("Users not found.");
        }
        log.info("getUsersByRole() size = {}", users.size());

        return users;
    }

    @Override
    public User getUserByEmail(String email) {
        log.info("getUserByEmail() --- method entered");
        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isPresent()) {
            User user = optional.get();
            log.info("getUserByEmail() --- found user {}", user);
            return user;
        } else {
            log.info("getUserByEmail() --- no user found");
            throw new ResourceNotFoundException("User not found.");
        }
    }
}
