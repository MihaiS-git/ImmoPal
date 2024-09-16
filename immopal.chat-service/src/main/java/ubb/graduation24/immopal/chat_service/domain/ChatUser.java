package ubb.graduation24.immopal.chat_service.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class ChatUser {
    @Id
    private String email;
    private String fullName;
    private Status status;
    private String pictureUrl;
}
