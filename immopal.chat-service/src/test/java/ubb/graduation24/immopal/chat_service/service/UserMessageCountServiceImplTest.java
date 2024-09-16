package ubb.graduation24.immopal.chat_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ubb.graduation24.immopal.chat_service.domain.UserMessageCount;
import ubb.graduation24.immopal.chat_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.chat_service.repository.UserMessageCountRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserMessageCountServiceImplTest {
    private UserMessageCount umc;
    private String chatId;

    @InjectMocks
    private UserMessageCountServiceImpl userMessageCountService;

    @Mock
    private UserMessageCountRepository countRepository;

    @BeforeEach
    void setUp() {
        chatId = "user1@example.com_user2@example.com";
        umc = UserMessageCount.builder()
                .chatId("user1@example.com_user2@example.com")
                .senderId("user1@example.com")
                .unreadCount(5L)
                .build();
    }

    @Test
    void getUserMessageCounts_Success() {
        when(countRepository.findAll()).thenReturn(Collections.singletonList(umc));

        List<UserMessageCount> result = userMessageCountService.getUserMessageCounts();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(umc, result.get(0));
        verify(countRepository, times(1)).findAll();
    }

    @Test
    void getUserMessageCounts_EmptyList() {
        when(countRepository.findAll()).thenReturn(Collections.emptyList());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            userMessageCountService.getUserMessageCounts();
        });

        assertEquals("No UserMessageCount found", thrown.getMessage());
        verify(countRepository, times(1)).findAll();
    }

    @Test
    void increaseUmcCounter_NewEntry() {
        when(countRepository.findAllByChatId(chatId)).thenReturn(Collections.emptyList());

        UserMessageCount newUmc = UserMessageCount.builder()
                .chatId(chatId)
                .senderId("user1@example.com")
                .unreadCount(1L)
                .build();
        when(countRepository.save(any(UserMessageCount.class))).thenReturn(newUmc);

        UserMessageCount result = userMessageCountService.increaseUmcCounter("user1@example.com",
                "user2@example.com");

        assertNotNull(result);
        assertEquals(1L, result.getUnreadCount());
        assertEquals("user1@example.com", result.getSenderId());
        verify(countRepository, times(1)).save(any(UserMessageCount.class));
        verify(countRepository, times(1)).findAllByChatId(chatId);
    }

    @Test
    void increaseUmcCounter_ExistingEntry() {
        when(countRepository.findAllByChatId(chatId)).thenReturn(Collections.singletonList(umc));
        when(countRepository.save(any(UserMessageCount.class))).thenAnswer(invocation -> {
            UserMessageCount arg = invocation.getArgument(0);
            return arg; // Simulate save returning the updated UserMessageCount
        });

        UserMessageCount result = userMessageCountService.increaseUmcCounter("user1@example.com",
                "user2@example.com");

        assertNotNull(result);
        assertEquals(6L, result.getUnreadCount());
        assertEquals("user1@example.com", result.getSenderId());
        verify(countRepository, times(1)).save(any(UserMessageCount.class));
        verify(countRepository, times(1)).findAllByChatId(chatId);
    }

    @Test
    void increaseUmcCounter_SenderNotInList() {
        UserMessageCount anotherUmc = UserMessageCount.builder()
                .chatId(chatId)
                .senderId("user2@example.com")
                .unreadCount(5L)
                .build();
        when(countRepository.findAllByChatId(chatId)).thenReturn(Collections.singletonList(anotherUmc));

        UserMessageCount newUmc = UserMessageCount.builder()
                .chatId(chatId)
                .senderId("user1@example.com")
                .unreadCount(1L)
                .build();
        when(countRepository.save(any(UserMessageCount.class))).thenReturn(newUmc);

        UserMessageCount result = userMessageCountService.increaseUmcCounter("user1@example.com",
                "user2@example.com");

        assertNotNull(result);
        assertEquals(1L, result.getUnreadCount());
        assertEquals("user1@example.com", result.getSenderId());
        verify(countRepository, times(1)).save(any(UserMessageCount.class));
        verify(countRepository, times(1)).findAllByChatId(chatId);
    }


    @Test
    void resetCounter_Success() {
        UserMessageCount existingUmc = UserMessageCount.builder()
                .chatId("user1@example.com_user2@example.com")
                .senderId("user2@example.com")
                .unreadCount(5L)
                .build();
        when(countRepository.findAllByChatId(anyString())).thenReturn(Collections.singletonList(existingUmc));
        when(countRepository.save(any(UserMessageCount.class))).thenAnswer(invocation -> {
            UserMessageCount arg = invocation.getArgument(0);
            return arg;
        });

        UserMessageCount result = userMessageCountService.resetCounter("user1@example.com",
                "user2@example.com");

        assertNotNull(result);
        assertEquals(0L, result.getUnreadCount());
        assertEquals("user2@example.com", result.getSenderId());

        verify(countRepository, times(1)).findAllByChatId(anyString());
        verify(countRepository, times(1)).save(any(UserMessageCount.class));
    }


    @Test
    void resetCounter_NoEntries() {
        when(countRepository.findAllByChatId(anyString())).thenReturn(Collections.emptyList());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            userMessageCountService.resetCounter("user1@example.com", "user2@example.com");
        });

        assertEquals("No UserMessageCount found", thrown.getMessage());
        verify(countRepository, times(1)).findAllByChatId(anyString());
    }

    @Test
    void resetCounter_UserNotFound() {
        when(countRepository.findAllByChatId(anyString())).thenReturn(Collections.emptyList());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            userMessageCountService.resetCounter("user1@example.com", "user2@example.com");
        });

        assertEquals("No UserMessageCount found", thrown.getMessage());
        verify(countRepository, times(1)).findAllByChatId(anyString());
    }
}