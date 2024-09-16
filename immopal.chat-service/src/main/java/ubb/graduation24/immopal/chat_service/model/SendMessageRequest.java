package ubb.graduation24.immopal.chat_service.model;

import lombok.Data;

@Data
public class SendMessageRequest {
    String chatId;
    String senderId;
    String content;
}
