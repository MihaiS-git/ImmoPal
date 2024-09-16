package ubb.graduation24.immopal.chat_service.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import ubb.graduation24.immopal.chat_service.domain.ChatMessage;
import ubb.graduation24.immopal.chat_service.domain.ChatUser;
import ubb.graduation24.immopal.chat_service.repository.UserRepository;

import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;
    private final UserRepository userRepository;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String email = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("email");
        if (email != null) {
            log.info("User disconnected: {}", email);
            ChatUser user = userRepository.findByEmail(email);
            if (user != null) {
                ChatMessage message = ChatMessage.builder()
                        .senderId(email)
                        .content("User has disconnected")
                        .build();
                messagingTemplate.convertAndSend("/topic/user/updates", message);
            }
        }
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String email = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("email");
        if (email != null) {
            log.info("User connected: {}", email);
            ChatUser user = userRepository.findByEmail(email);
            if (user != null) {
                ChatMessage message = ChatMessage.builder()
                        .senderId(email)
                        .content("User has connected")
                        .build();
                messagingTemplate.convertAndSend("/topic/user/updates", message);
            }
        }
    }
}


