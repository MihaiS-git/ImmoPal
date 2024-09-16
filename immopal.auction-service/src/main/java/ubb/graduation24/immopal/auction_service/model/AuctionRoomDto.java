package ubb.graduation24.immopal.auction_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ubb.graduation24.immopal.auction_service.domain.AuctionRoomStatus;
import ubb.graduation24.immopal.auction_service.domain.BidMessage;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuctionRoomDto {

    private String id;
    private PropertyDto property;
    private PersonDto agent;
    private AgencyDto agency;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime lastModifiedDate;
    private AuctionRoomStatus auctionStatus;
    private double maxBidAmount;
    private double startBidAmount;
    private List<ParticipantDto> participants;
    private ParticipantDto winner;
    private BidMessage winningBid;
    private List<BidMessage> bids;

}
