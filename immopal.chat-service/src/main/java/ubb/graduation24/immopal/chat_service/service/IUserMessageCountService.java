package ubb.graduation24.immopal.chat_service.service;

import ubb.graduation24.immopal.chat_service.domain.UserMessageCount;

import java.util.List;

public interface IUserMessageCountService {

    List<UserMessageCount> getUserMessageCounts();
    UserMessageCount resetCounter(String chatId, String senderId);
    UserMessageCount increaseUmcCounter(String senderId, String recipientId);

}

