package ubb.graduation24.immopal.chat_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ubb.graduation24.immopal.chat_service.domain.UserMessageCount;

import java.util.List;

public interface UserMessageCountRepository extends MongoRepository<UserMessageCount, String> {

    List<UserMessageCount> findAllByChatId(String chatId);
}
