package ubb.graduation24.immopal.chat_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ubb.graduation24.immopal.chat_service.domain.ChatRoom;

import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

    Optional<ChatRoom> findByChatId(String chatId);

}
