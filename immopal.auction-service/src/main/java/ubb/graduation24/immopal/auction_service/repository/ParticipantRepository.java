package ubb.graduation24.immopal.auction_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ubb.graduation24.immopal.auction_service.domain.Participant;

@Repository
public interface ParticipantRepository extends MongoRepository<Participant, String> {

    Participant findByEmail(String email);
    Participant findByPersonId(Long personid);

    @Query("{ 'personId': ?0, 'auctions': ?1 }")
    Participant findByPersonIdAndAuctionRoomId(Long personId, String auctionRoomId);

}
