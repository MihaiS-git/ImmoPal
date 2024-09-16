package ubb.graduation24.immopal.chat_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ubb.graduation24.immopal.chat_service.domain.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByChatId(String id);
}
