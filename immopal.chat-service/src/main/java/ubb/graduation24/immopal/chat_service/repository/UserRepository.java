package ubb.graduation24.immopal.chat_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ubb.graduation24.immopal.chat_service.domain.ChatUser;

public interface UserRepository extends MongoRepository<ChatUser, String> {

    ChatUser findByEmail(String email);

}
