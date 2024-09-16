package ubb.graduation24.immopal.chat_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import ubb.graduation24.immopal.chat_service.domain.*;
import ubb.graduation24.immopal.chat_service.model.ConnectUserRequest;
import ubb.graduation24.immopal.chat_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.chat_service.service.IChatMessageService;
import ubb.graduation24.immopal.chat_service.service.IChatRoomService;
import ubb.graduation24.immopal.chat_service.service.IUserService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    private final IUserService userService;
    private final IChatMessageService chatMessageService;
    private final IChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/chatUsers/connectUser")
    public ResponseEntity<ChatUser> connectUser(@RequestBody ConnectUserRequest request) {
        log.debug("Request to connectUser received: {}", request);
        ChatUser user = userService.connectUser(request);
        log.debug("User connected and message sent to topic.");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/chatUsers/disconnectUser")
    public ResponseEntity<ChatUser> disconnectUser(@RequestBody String email) {
        log.debug("Request to disconnectUser");
        userService.disconnect(email);
        ChatUser disconnectedUser = userService.findByEmail(email);
        if (disconnectedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        messagingTemplate.convertAndSend("/topic/user/updates", disconnectedUser);
        return ResponseEntity.ok(disconnectedUser);
    }

    @GetMapping("/chatUsers/users")
    public ResponseEntity<List<ChatUser>> getAllUsers() {
        log.debug("Request to findConnectedUsers");
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/chatUsers/{email}")
    public ResponseEntity<ChatUser> getUserByEmail(@PathVariable String email) {
        log.debug("Request to getUserByEmail");
        ChatUser user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/chatMessages/{chatId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(@PathVariable String chatId) {
        List<ChatMessage> messages = chatMessageService.findChatMessages(chatId);
        messages.forEach(message -> {
            if (message.getTimestamp() == null) {
                message.setTimestamp(LocalDateTime.now(ZoneId.of("Europe/Bucharest")));
            }
        });
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/chatRoom/{senderId}/{recipientId}")
    public ResponseEntity<ChatRoom> enterChatRoom(@PathVariable String senderId, @PathVariable String recipientId) {
        try {
            List<String> emails = Arrays.asList(senderId, recipientId);
            Collections.sort(emails);
            String chatId = String.join("_", emails);

            ChatUser user1 = userService.findByEmail(senderId);
            ChatUser user2 = userService.findByEmail(recipientId);

            if(user1 == null || user2 == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            log.debug("Request to create or retrieve chat room between {} and {}", senderId, recipientId);

            Optional<ChatRoom> chatRoomOptional = chatRoomService.findByChatId(chatId);
            if (chatRoomOptional.isEmpty()) {
                ChatRoom newChatRoom = chatRoomService.createChatRoom(user1, user2);
                return ResponseEntity.ok(newChatRoom);
            } else {
                return ResponseEntity.ok(chatRoomOptional.get());
            }
        } catch (ResourceNotFoundException e) {
            log.error("Error create or retrieve chat room:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
