package ubb.graduation24.immopal.chat_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.graduation24.immopal.chat_service.domain.UserMessageCount;
import ubb.graduation24.immopal.chat_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.chat_service.service.IUserMessageCountService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class UserMessageCountController {

    private final IUserMessageCountService umcService;

    @GetMapping("/unreadCounts")
    public ResponseEntity<List<UserMessageCount>> getUnreadCounts() {
        try {
            List<UserMessageCount> umcList = umcService.getUserMessageCounts();
            return ResponseEntity.ok(umcList);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/unreadCounts/{senderId}/{recipientId}/reset")
    public ResponseEntity<UserMessageCount> resetCounter(@PathVariable String senderId, @PathVariable String recipientId) {
        try {
            UserMessageCount umc = umcService.resetCounter(senderId, recipientId);
            return ResponseEntity.ok(umc);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
