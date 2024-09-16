package ubb.graduation24.immopal.chat_service.service;

import org.springframework.data.mongodb.repository.Query;
import ubb.graduation24.immopal.chat_service.domain.ChatRoom;
import ubb.graduation24.immopal.chat_service.domain.ChatUser;

import java.util.Optional;


public interface IChatRoomService {

    ChatRoom createChatRoom(ChatUser user1, ChatUser user2);

    @Query("{ 'chatId': ?0 }")
    Optional<ChatRoom> findByChatId(String chatId);

}
