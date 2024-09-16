package ubb.graduation24.immopal.auction_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ubb.graduation24.immopal.auction_service.domain.BidMessage;

import java.util.List;

@Repository
public interface BidMessageRepository extends MongoRepository<BidMessage, String> {

    List<BidMessage> findByAuctionRoomId(String auctionRoomId);

}
