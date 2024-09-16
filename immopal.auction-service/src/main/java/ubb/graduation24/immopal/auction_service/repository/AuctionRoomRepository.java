package ubb.graduation24.immopal.auction_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ubb.graduation24.immopal.auction_service.domain.AuctionRoom;

import java.util.Optional;

@Repository
public interface AuctionRoomRepository extends MongoRepository<AuctionRoom, String> {

    Optional<AuctionRoom> findByPropertyId(long propertytId);

}
