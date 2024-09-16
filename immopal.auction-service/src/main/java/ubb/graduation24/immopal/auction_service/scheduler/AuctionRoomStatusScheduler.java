package ubb.graduation24.immopal.auction_service.scheduler;

import com.mongodb.MongoQueryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ubb.graduation24.immopal.auction_service.domain.AuctionRoom;
import ubb.graduation24.immopal.auction_service.domain.AuctionRoomStatus;
import ubb.graduation24.immopal.auction_service.domain.Participant;
import ubb.graduation24.immopal.auction_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.auction_service.repository.AuctionRoomRepository;
import ubb.graduation24.immopal.auction_service.service.IBidMessageService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuctionRoomStatusScheduler {

    private final AuctionRoomRepository auctionRoomRepository;
    private final IBidMessageService bidMessageService;

    @Scheduled(fixedRate = 60000) // set to 60 sec
    public void updateAuctionRoomStatuses() {
        log.info("SCHEDULER RUNNING...{}", LocalDateTime.now(ZoneId.of("Europe/Bucharest")));
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/Bucharest"));
        try {
            List<AuctionRoom> auctionRooms = auctionRoomRepository.findAll();
            if(auctionRooms.isEmpty()) {
                log.info("updateAuctionRoomStatuses() --- no auction rooms found");
                return;
            }
            for (AuctionRoom room : auctionRooms) {
                try {
                    updateRoomStatus(room, now);
                } catch (ResourceNotFoundException e) {
                    log.error("AuctionRoom with Id={} not found", room.getId());
                } catch (Exception e) {
                    log.error("Error processing AuctionRoom with Id={}: {}", room.getId(), e.getMessage());
                }
            }
            log.info("updateAuctionRoomStatuses() --- method exit");
        } catch (UncategorizedMongoDbException | MongoQueryException e) {
            log.error("Database error in updateAuctionRoomStatuses(): {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error in updateAuctionRoomStatuses(): {}", e.getMessage());
        }
    }

    private void updateRoomStatus(AuctionRoom room, LocalDateTime now) {
        log.info("UPDATING AuctionRoom with Id={} at {}", room.getId(), now);
        if (room.getAuctionStatus() == AuctionRoomStatus.INACTIVE && now.isAfter(room.getStartDate())) {
            room.setAuctionStatus(AuctionRoomStatus.ACTIVE);
            room.setLastModifiedDate(now);
            auctionRoomRepository.save(room);
            log.info("AuctionRoom with Id={} set to ACTIVE", room.getId());
        } else if (room.getAuctionStatus() == AuctionRoomStatus.ACTIVE && now.isAfter(room.getEndDate())) {
            room.setAuctionStatus(AuctionRoomStatus.CLOSED);
            room.setLastModifiedDate(now);
            Participant winner = bidMessageService.getTheWinner(room.getId());
            room.setWinnerId(winner.getId());
            auctionRoomRepository.save(room);
            log.info("AuctionRoom with Id={} set to CLOSED", room.getId());
            log.info("AuctionRoom with Id={} winnerId is {}", room.getId(), winner.getId());
        }
    }
}
