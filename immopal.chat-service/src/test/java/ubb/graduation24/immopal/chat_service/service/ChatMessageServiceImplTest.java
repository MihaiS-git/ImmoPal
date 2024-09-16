package ubb.graduation24.immopal.chat_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import ubb.graduation24.immopal.chat_service.domain.ChatMessage;
import ubb.graduation24.immopal.chat_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.chat_service.repository.ChatMessageRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatMessageServiceImplTest {

    @InjectMocks
    private ChatMessageServiceImpl chatMessageService;

    @Mock
    private ChatMessageRepository chatMessageRepository;

    private ChatMessage chatMessage;

    @BeforeEach
    void setUp() {
        chatMessage = ChatMessage.builder()
                .chatId("chat1")
                .senderId("sender@example.com")
                .content("Hello, World!")
                .timestamp(LocalDateTime.now(ZoneId.of("Europe/Bucharest")))
                .build();
    }

    @Test
    void sendMessage_Success() {
        when(chatMessageRepository.save(any(ChatMessage.class))).thenReturn(chatMessage);

        ChatMessage result = chatMessageService
                .sendMessage("chat1", "sender@example.com", "Hello, World!");

        assertNotNull(result);
        assertEquals("chat1", result.getChatId());
        assertEquals("sender@example.com", result.getSenderId());
        assertEquals("Hello, World!", result.getContent());
        verify(chatMessageRepository, times(1)).save(any(ChatMessage.class)); // Update to expect one call
    }

    @Test
    void sendMessage_SaveException() {
        when(chatMessageRepository.save(any(ChatMessage.class))).thenThrow(new DataAccessException("Error") {});

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            chatMessageService.sendMessage("chat1", "sender@example.com", "Hello, World!");
        });

        assertTrue(thrown.getMessage().contains("Could not send message"));
        verify(chatMessageRepository, times(1)).save(any(ChatMessage.class));
    }

    @Test
    void findChatMessages_Success() {
        when(chatMessageRepository.findByChatId("chat1")).thenReturn(List.of(chatMessage));

        List<ChatMessage> result = chatMessageService.findChatMessages("chat1");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(chatMessage, result.get(0));
        verify(chatMessageRepository, times(1)).findByChatId("chat1");
    }

    @Test
    void findChatMessages_NoMessages() {
        when(chatMessageRepository.findByChatId("chat1")).thenReturn(Collections.emptyList());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            chatMessageService.findChatMessages("chat1");
        });

        assertTrue(thrown.getMessage().contains("No messages found for chat ID: chat1"));
        verify(chatMessageRepository, times(1)).findByChatId("chat1");
    }

    @Test
    void findChatMessages_RepositoryException() {
        // Arrange
        String chatId = "chat1";
        when(chatMessageRepository.findByChatId(chatId)).thenThrow(new DataAccessException("Error") {});

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            chatMessageService.findChatMessages(chatId);
        });

        // Validate exception message
        assertTrue(thrown.getMessage().contains("Could not retrieve messages"));
        verify(chatMessageRepository, times(1)).findByChatId(chatId);
    }


}
