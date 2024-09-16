package ubb.graduation24.immopal.chat_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ubb.graduation24.immopal.chat_service.domain.*;
import ubb.graduation24.immopal.chat_service.model.SendMessageRequest;
import ubb.graduation24.immopal.chat_service.service.IChatMessageService;
import ubb.graduation24.immopal.chat_service.service.IChatRoomService;
import ubb.graduation24.immopal.chat_service.service.IUserMessageCountService;
import ubb.graduation24.immopal.chat_service.service.IUserService;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatWsController {

    private final SimpMessagingTemplate messagingTemplate;
    private final IChatMessageService chatMessageService;
    private final IChatRoomService chatRoomService;
    private final IUserMessageCountService umcService;
    private final IUserService userService;

    @MessageMapping("/sendMessage")
    public void sendMessage(SendMessageRequest request) {
        log.debug("Received sendMessage request: {}", request);
        ChatMessage message = chatMessageService.sendMessage(request.getChatId(), request.getSenderId(), request.getContent());
        ChatNotification notification;
        Optional<ChatRoom> chatRoomOptional = chatRoomService.findByChatId(request.getChatId());
        if (chatRoomOptional.isPresent()) {
            ChatRoom chatRoom = chatRoomOptional.get();
            ChatUser sender = userService.findByEmail(request.getSenderId());
            if (sender != null) {
                ChatUser recipient;
                if (chatRoom.getUser1().equals(sender)) {
                    recipient = chatRoom.getUser2();
                    sender = chatRoom.getUser1();
                    notification = ChatNotification.builder()
                            .senderId(request.getSenderId())
                            .recipientId(recipient.getEmail())
                            .content(request.getContent())
                            .build();
                } else {
                    recipient = chatRoom.getUser1();
                    sender = chatRoom.getUser2();
                    notification = ChatNotification.builder()
                            .senderId(request.getSenderId())
                            .recipientId(recipient.getEmail())
                            .content(request.getContent())
                            .build();
                }

                UserMessageCount umc = umcService.increaseUmcCounter(sender.getEmail(), recipient.getEmail());
                log.debug("Increased umc counter: {}", umc);

                messagingTemplate.convertAndSend("/topic/chat/" + recipient.getEmail() + "/queue/messages", message);
                messagingTemplate.convertAndSend("/topic/chat/" + recipient.getEmail() + "/queue/notifications", notification);

                messagingTemplate.convertAndSend("/topic/chat/" + sender.getEmail() + "/queue/messages", message);
                messagingTemplate.convertAndSend("/topic/chat/" + sender.getEmail() + "/queue/notifications", notification);
                log.debug("Message sent: {}", message);
            }
        } else {
            log.error("ChatRoom with chatId {} not found", request.getChatId());
        }
    }
}