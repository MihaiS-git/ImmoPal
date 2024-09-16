package ubb.graduation24.immopal.chat_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.chat_service.domain.UserMessageCount;
import ubb.graduation24.immopal.chat_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.chat_service.repository.UserMessageCountRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserMessageCountServiceImpl implements IUserMessageCountService {

    private final UserMessageCountRepository countRepository;

    @Override
    public List<UserMessageCount> getUserMessageCounts() {
        List<UserMessageCount> umcList = countRepository.findAll();
        if (umcList.isEmpty()) {
            throw new ResourceNotFoundException("No UserMessageCount found");
        }
        return umcList;
    }

    @Override
    public UserMessageCount increaseUmcCounter(String senderId, String recipientId) {
        String chatId = getChatId(senderId, recipientId);
        List<UserMessageCount> umcList = countRepository.findAllByChatId(chatId);
        UserMessageCount umc = umcList.stream().filter(u -> u.getSenderId().equals(senderId)).findFirst().orElse(null);
        if (umc == null) {
            UserMessageCount newUmc = UserMessageCount.builder()
                    .chatId(chatId)
                    .senderId(senderId)
                    .unreadCount(1L)
                    .build();
            return countRepository.save(newUmc);
        } else {
            umc.setUnreadCount(umc.getUnreadCount() + 1);
            return countRepository.save(umc);
        }
    }

    @Override
    public UserMessageCount resetCounter(String senderId, String recipientId) {
        String chatId = getChatId(senderId, recipientId);

        List<UserMessageCount> umcList = countRepository.findAllByChatId(chatId);
        if (umcList.isEmpty()) {
            throw new ResourceNotFoundException("No UserMessageCount found");
        }

        UserMessageCount umc = umcList.stream().filter(u -> u.getSenderId().equals(recipientId)).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No UserMessageCount found"));

        umc.setUnreadCount(0L);
        return countRepository.save(umc);
    }

    private static String getChatId(String senderId, String recipientId) {
        List<String> emails = Arrays.asList(senderId, recipientId);
        Collections.sort(emails);
        return String.join("_", emails);
    }
}

