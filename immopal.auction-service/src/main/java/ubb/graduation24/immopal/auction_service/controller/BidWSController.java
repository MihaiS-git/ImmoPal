package ubb.graduation24.immopal.auction_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import ubb.graduation24.immopal.auction_service.domain.BidMessage;
import ubb.graduation24.immopal.auction_service.service.IBidMessageService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BidWSController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final IBidMessageService bidMessageService;

    @MessageMapping("/auction/{auctionRoomId}/bid")
    public void placeBid(@Payload BidMessage bidMessage,
                         @DestinationVariable String auctionRoomId,
                         SimpMessageHeaderAccessor headerAccessor) {

        BidMessage savedBidMessage = bidMessageService.placeBidMessage(auctionRoomId, bidMessage);
        messagingTemplate.convertAndSend("/topic/auction/" + auctionRoomId + "/bids", savedBidMessage);
    }

}
