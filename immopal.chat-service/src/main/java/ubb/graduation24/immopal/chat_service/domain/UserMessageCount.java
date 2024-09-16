package ubb.graduation24.immopal.chat_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class UserMessageCount {

    @Id
    private String id;
    private String chatId;
    private String senderId;
    private Long unreadCount;

}
