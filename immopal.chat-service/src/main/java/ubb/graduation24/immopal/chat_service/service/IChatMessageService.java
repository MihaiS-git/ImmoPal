package ubb.graduation24.immopal.chat_service.service;

import ubb.graduation24.immopal.chat_service.domain.ChatMessage;

import java.util.List;

public interface IChatMessageService {

    ChatMessage sendMessage(String chatId, String senderEmail, String content);
    List<ChatMessage> findChatMessages(String chatId);

}
