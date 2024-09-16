package ubb.graduation24.immopal.auction_service.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection="bid_messages")
public class BidMessage {

    @Id
    private String id;
    private String auctionRoomId;
    private MessageType type;
    private Participant sender;
    private Double amount;
    private LocalDateTime timestamp;

}
