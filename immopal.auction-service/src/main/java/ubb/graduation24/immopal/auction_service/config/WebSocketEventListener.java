package ubb.graduation24.immopal.auction_service.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import ubb.graduation24.immopal.auction_service.domain.BidMessage;
import ubb.graduation24.immopal.auction_service.domain.MessageType;
import ubb.graduation24.immopal.auction_service.repository.ParticipantRepository;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ParticipantRepository participantRepository;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String email = (String) headerAccessor.getSessionAttributes().get("email");
        if (email != null) {
            log.info("Participant disconnected: {}", email);
            BidMessage bidMessage = BidMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(participantRepository.findByEmail(email))
                    .build();
            messagingTemplate.convertAndSend("/topic/auction/public", bidMessage);
        }
    }
}
