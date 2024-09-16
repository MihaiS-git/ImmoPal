package ubb.graduation24.immopal.auction_service.service;

import ubb.graduation24.immopal.auction_service.domain.BidMessage;
import ubb.graduation24.immopal.auction_service.domain.Participant;

import java.util.List;

public interface IBidMessageService {

    BidMessage placeBidMessage(String auctionRoomId, BidMessage bidMessage);

    List<BidMessage> findBidMessagesByAuctionRoomId(String auctionRoomId);

    Participant getTheWinner(String auctionRoomId);

}
