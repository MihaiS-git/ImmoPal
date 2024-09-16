package ubb.graduation24.immopal.auction_service.service;

import ubb.graduation24.immopal.auction_service.domain.AuctionRoom;
import ubb.graduation24.immopal.auction_service.model.CreateAuctionRoomRequest;

import java.util.List;

public interface IAuctionRoomService {

    List<AuctionRoom> getAllAuctionRooms();

    AuctionRoom getAuctionRoomById(String id);

    AuctionRoom createAuctionRoom(CreateAuctionRoomRequest requestBody);

}
