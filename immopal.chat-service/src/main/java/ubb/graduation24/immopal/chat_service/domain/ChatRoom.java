package ubb.graduation24.immopal.chat_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class ChatRoom {

    @Id
    private String id;
    private String chatId;
    @DBRef
    private ChatUser user1;
    @DBRef
    private ChatUser user2;

}