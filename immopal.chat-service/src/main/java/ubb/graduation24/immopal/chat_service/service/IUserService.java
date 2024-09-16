package ubb.graduation24.immopal.chat_service.service;

import ubb.graduation24.immopal.chat_service.domain.ChatUser;
import ubb.graduation24.immopal.chat_service.model.ConnectUserRequest;

import java.util.List;

public interface IUserService {

    ChatUser saveUser(ChatUser user);
    ChatUser disconnect(String email);
    List<ChatUser> findAllUsers();
    ChatUser findById(String id);
//    ChatUser updateUserStatus(ConnectUserRequest request);
    ChatUser findByEmail(String email);
    ChatUser connectUser(ConnectUserRequest request);
}

