package ubb.graduation24.immopal.chat_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.chat_service.domain.ChatRoom;
import ubb.graduation24.immopal.chat_service.domain.ChatUser;
import ubb.graduation24.immopal.chat_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.chat_service.repository.ChatRoomRepository;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements IChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public ChatRoom createChatRoom(ChatUser user1, ChatUser user2) {
        String chatId = createChatId(user1.getEmail(), user2.getEmail());
        ChatRoom chatRoom = ChatRoom.builder()
                .chatId(chatId)
                .user1(user1)
                .user2(user2)
                .build();
        try {
            ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
            log.debug("Created ChatRoom: {}", savedChatRoom);
            return savedChatRoom;
        } catch (DataAccessException e) {
            log.error("Error creating chat room: {}", e.getMessage());
            throw new RuntimeException("Could not create chat room", e);
        }
    }

    @Override
    public Optional<ChatRoom> findByChatId(String chatId) {
        log.debug("Searching for ChatRoom with chatId: {}", chatId);
        try {
            return chatRoomRepository.findByChatId(chatId);
        } catch (DataAccessException e) {
            log.error("Error finding chat room by chatId: {}", e.getMessage());
            throw new ResourceNotFoundException("Could not retrieve chat room", e);
        }
    }

    private String createChatId(String email1, String email2) {
        String[] emails = {email1, email2};
        Arrays.sort(emails);
        return String.join("_", emails);
    }
}
