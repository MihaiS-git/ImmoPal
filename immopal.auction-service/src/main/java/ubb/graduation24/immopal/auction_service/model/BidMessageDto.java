package ubb.graduation24.immopal.auction_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ubb.graduation24.immopal.auction_service.domain.MessageType;
import ubb.graduation24.immopal.auction_service.domain.Participant;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidMessageDto {

    private String id;
    private String auctionRoomId;
    private MessageType type;
    private Participant sender;
    private double amount;
    private LocalDateTime timestamp;

}
