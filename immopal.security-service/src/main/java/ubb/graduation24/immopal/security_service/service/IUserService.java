package ubb.graduation24.immopal.security_service.service;

import ubb.graduation24.immopal.security_service.domain.User;
import ubb.graduation24.immopal.security_service.model.UserDto;

import java.util.List;

public interface IUserService {
    List<User> getUsers();
    User getUserById(Long id);
    User updateUser(Long id, UserDto userPersonDto);
    void deleteUser(Long id);

    List<User> getUsersByRole(String roleString);
    User getUserByEmail(String email);
}
