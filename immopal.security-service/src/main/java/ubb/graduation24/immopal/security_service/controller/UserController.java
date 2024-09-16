package ubb.graduation24.immopal.security_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.graduation24.immopal.security_service.domain.User;
import ubb.graduation24.immopal.security_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.security_service.mapper.UserMapper;
import ubb.graduation24.immopal.security_service.model.UserDto;
import ubb.graduation24.immopal.security_service.service.IUserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final IUserService userService;

    @Autowired
    private final UserMapper userMapper;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        try {
            List<User> users = userService.getUsers();
            List<UserDto> usersDto = users.stream()
                    .map(userMapper::userToUserDto)
                    .toList();
            return ResponseEntity.ok(usersDto);
        } catch (ResourceNotFoundException e) {
            log.info("Users not found");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<UserDto> getUser(@PathVariable String email) {
        try{
            User user = userService.getUserByEmail(email);
            UserDto userDto = userMapper.userToUserDto(user);
            return ResponseEntity.ok(userDto);
        } catch (ResourceNotFoundException e) {
            log.info("User not found");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/id/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(userMapper.userToUserDto(user));
        } catch (ResourceNotFoundException e) {
            log.info("User not found");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/role/{role}")
    public ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable String role) {
        if (role.equalsIgnoreCase("admin")
                || role.equalsIgnoreCase("agent")
                || role.equalsIgnoreCase("customer")) {
            try {
                List<User> users = userService.getUsersByRole(role);
                List<UserDto> usersDto = users.stream()
                        .map(userMapper::userToUserDto)
                        .toList();
                return ResponseEntity.ok(usersDto);
            } catch (ResourceNotFoundException e) {
                log.info("Users not found");
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        try {
            User user = userService.updateUser(id, userDto);
            UserDto userResponseDto = userMapper.userToUserDto(user);
            return ResponseEntity.ok(userResponseDto);
        } catch (ResourceNotFoundException e) {
            log.info("User not found");
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e){
            log.info("User not found");
            return ResponseEntity.notFound().build();
        }
    }
}
