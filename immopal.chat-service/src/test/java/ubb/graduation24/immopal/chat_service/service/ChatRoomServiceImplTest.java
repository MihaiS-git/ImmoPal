package ubb.graduation24.immopal.chat_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import ubb.graduation24.immopal.chat_service.domain.ChatRoom;
import ubb.graduation24.immopal.chat_service.domain.ChatUser;
import ubb.graduation24.immopal.chat_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.chat_service.repository.ChatRoomRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatRoomServiceImplTest {

    @InjectMocks
    private ChatRoomServiceImpl chatRoomService;

    @Mock
    private ChatRoomRepository chatRoomRepository;

    private ChatUser user1;
    private ChatUser user2;
    private ChatRoom chatRoom;

    @BeforeEach
    void setUp() {
        user1 = ChatUser.builder().email("user1@example.com").build();
        user2 = ChatUser.builder().email("user2@example.com").build();
        chatRoom = ChatRoom.builder()
                .chatId("user1@example.com_user2@example.com")
                .user1(user1)
                .user2(user2)
                .build();
    }

    @Test
    void createChatRoom_Success() {
        when(chatRoomRepository.save(any(ChatRoom.class))).thenReturn(chatRoom);

        ChatRoom result = chatRoomService.createChatRoom(user1, user2);

        assertNotNull(result);
        assertEquals("user1@example.com_user2@example.com", result.getChatId());
        assertEquals(user1, result.getUser1());
        assertEquals(user2, result.getUser2());
        verify(chatRoomRepository, times(1)).save(any(ChatRoom.class));
    }

    @Test
    void createChatRoom_Exception() {
        when(chatRoomRepository.save(any(ChatRoom.class))).thenThrow(new DataAccessException("Error") {});

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            chatRoomService.createChatRoom(user1, user2);
        });

        assertTrue(thrown.getMessage().contains("Could not create chat room"));
        verify(chatRoomRepository, times(1)).save(any(ChatRoom.class));
    }

    @Test
    void findByChatId_Found() {
        when(chatRoomRepository.findByChatId(anyString())).thenReturn(Optional.of(chatRoom));

        Optional<ChatRoom> result = chatRoomService.findByChatId("user1@example.com_user2@example.com");

        assertTrue(result.isPresent());
        assertEquals(chatRoom, result.get());
        verify(chatRoomRepository, times(1)).findByChatId(anyString());
    }

    @Test
    void findByChatId_NotFound() {
        when(chatRoomRepository.findByChatId(anyString())).thenReturn(Optional.empty());

        Optional<ChatRoom> result = chatRoomService.findByChatId("user1@example.com_user2@example.com");

        assertFalse(result.isPresent());
        verify(chatRoomRepository, times(1)).findByChatId(anyString());
    }

    @Test
    void findByChatId_Exception() {
        when(chatRoomRepository.findByChatId(anyString())).thenThrow(new DataAccessException("Error") {});

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            chatRoomService.findByChatId("user1@example.com_user2@example.com");
        });

        assertTrue(thrown.getMessage().contains("Could not retrieve chat room"));
        verify(chatRoomRepository, times(1)).findByChatId(anyString());
    }
}
