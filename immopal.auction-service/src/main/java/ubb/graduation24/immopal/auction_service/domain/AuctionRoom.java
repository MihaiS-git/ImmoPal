package ubb.graduation24.immopal.auction_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "auction_rooms")
public class AuctionRoom {

    @Id
    private String id;
    private Long propertyId;
    private Long agencyId;
    private Long agentId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime lastModifiedDate;
    private AuctionRoomStatus auctionStatus;
    private Double maxBidAmount;
    private Double startBidAmount;

    @Builder.Default
    private List<String> participantIds = new ArrayList<>();

    private String winnerId;
    private BidMessage winningBid;

    @Builder.Default
    private List<BidMessage> bids = new ArrayList<>();
}
