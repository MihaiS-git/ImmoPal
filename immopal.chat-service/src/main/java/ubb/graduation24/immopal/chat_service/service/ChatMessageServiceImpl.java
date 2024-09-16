package ubb.graduation24.immopal.chat_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.chat_service.domain.ChatMessage;
import ubb.graduation24.immopal.chat_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.chat_service.repository.ChatMessageRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatMessageServiceImpl implements IChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    @Override
    public ChatMessage sendMessage(String chatId, String senderEmail, String content) {
        log.info("Sending message to chatId: {}, senderEmail: {}, content: {}", chatId, senderEmail, content);

        try {
            ChatMessage chatMessage = ChatMessage.builder()
                    .chatId(chatId)
                    .senderId(senderEmail)
                    .content(content)
                    .timestamp(LocalDateTime.now(ZoneId.of("Europe/Bucharest")))
                    .build();

            ChatMessage savedChatMessage = chatMessageRepository.save(chatMessage);

            log.info("Saved Message: {}", savedChatMessage);
            return savedChatMessage;
        } catch (Exception e) {
            log.error("Error sending message: {}", e.getMessage());
            throw new RuntimeException("Could not send message", e);
        }
    }

    @Override
    public List<ChatMessage> findChatMessages(String chatId) {
        log.info("findChatMessages() --- method entered for chatId: {}", chatId);
        try {
            List<ChatMessage> messages = chatMessageRepository.findByChatId(chatId);
            if (messages.isEmpty()) {
                log.warn("No messages found for chatId: {}", chatId);
                throw new ResourceNotFoundException("No messages found for chat ID: " + chatId);
            }
            log.info("Found {} messages for chatId: {}", messages.size(), chatId);
            return messages;
        } catch (DataAccessException e) {
            log.error("Error retrieving messages for chatId: {}. Error: {}", chatId, e.getMessage());
            throw new RuntimeException("Could not retrieve messages", e);
        }
    }

}
