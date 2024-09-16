package ubb.graduation24.immopal.auction_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.auction_service.domain.AuctionRoom;
import ubb.graduation24.immopal.auction_service.domain.BidMessage;
import ubb.graduation24.immopal.auction_service.domain.MessageType;
import ubb.graduation24.immopal.auction_service.domain.Participant;
import ubb.graduation24.immopal.auction_service.exception.BidRequestException;
import ubb.graduation24.immopal.auction_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.auction_service.exception.ServiceOperationException;
import ubb.graduation24.immopal.auction_service.model.EmailMessageRequest;
import ubb.graduation24.immopal.auction_service.repository.AuctionRoomRepository;
import ubb.graduation24.immopal.auction_service.repository.BidMessageRepository;
import ubb.graduation24.immopal.auction_service.repository.ParticipantRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BidMessageServiceImpl implements IBidMessageService {
    private  static final String AUCTION_LINK = "http://localhost:4200/auctions/rooms-list/";

    private final BidMessageRepository bidMessageRepository;
    private final AuctionRoomRepository auctionRoomRepository;
    private final ParticipantRepository participantRepository;
    private final RabbitMQSender rabbitMQSender;

    @Override
    public BidMessage placeBidMessage(String auctionRoomId, BidMessage bidMessage) {
        if (bidMessage.getType() != MessageType.BID) {
            log.debug("Auction message type is not BID");
            throw new BidRequestException("Auction message type is not BID");
        }
        log.info("Place bid message for auction room with id {}", auctionRoomId);
        Optional<AuctionRoom> auctionRoomOptional = auctionRoomRepository.findById(auctionRoomId);
        if (auctionRoomOptional.isEmpty()) {
            log.debug("Auction room not found");
            throw new ResourceNotFoundException(auctionRoomId);
        }
        AuctionRoom auctionRoom = auctionRoomOptional.get();
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/Bucharest"));
        if (auctionRoom.getStartDate() == null || auctionRoom.getEndDate() == null) {
            throw new BidRequestException("Auction room has invalid date range");
        }
        if(now.isBefore(auctionRoom.getStartDate()) || now.isAfter(auctionRoom.getEndDate())) {
            throw new BidRequestException("Current date and time is not in the official bidding interval");
        }

        if (bidMessage.getAmount() <= auctionRoom.getMaxBidAmount()) {
            log.debug("Auction message amount is smaller than last bid amount");
            throw new BidRequestException("Auction message amount is smaller than last bid amount");
        }

        String senderEmail = bidMessage.getSender().getEmail();
        Participant participant = participantRepository.findByEmail(senderEmail);
        if (participant == null) {
            log.debug("Participant not found");
            participant = bidMessage.getSender();
            participant.getAuctions().add(bidMessage.getAuctionRoomId());
            participant = participantRepository.save(participant);
            bidMessage.setSender(participant);
        } else {
            log.debug("Participant already exists");
            if(!participant.getAuctions().contains(bidMessage.getAuctionRoomId())) {
                participant.getAuctions().add(bidMessage.getAuctionRoomId());
                participant = participantRepository.save(participant);
                bidMessage.setSender(participant);
            } else {
                bidMessage.setSender(participant);
            }
        }
        bidMessage = bidMessageRepository.save(bidMessage);

        auctionRoom.setLastModifiedDate(LocalDateTime.now(ZoneId.of("Europe/Bucharest")));
        auctionRoom.setMaxBidAmount(bidMessage.getAmount());
        if (!auctionRoom.getParticipantIds().contains(participant.getId())) {
            auctionRoom.getParticipantIds().add(participant.getId());
        }
        auctionRoom.setWinnerId(participant.getId());
        auctionRoom.setWinningBid(bidMessage);
        auctionRoom.getBids().add(bidMessage);
        auctionRoomRepository.save(auctionRoom);

        try{
            rabbitMQSender.notifyPersonNewBid(bidMessage);
        } catch (ServiceOperationException e){
            log.error("Error while sending new bid notification: {}", e.getMessage());
        }

        return bidMessage;
    }

    @Override
    public List<BidMessage> findBidMessagesByAuctionRoomId(String auctionRoomId) {
        log.info("findBidMessages --- method entered");
        List<BidMessage> bidMessages = bidMessageRepository.findByAuctionRoomId(auctionRoomId);
        if (bidMessages.isEmpty()) {
            log.info("findBidMessages --- not found");
            throw new ResourceNotFoundException("Auction room not found with id: " + auctionRoomId);
        }
        return bidMessages;
    }

    @Override
    public Participant getTheWinner(String auctionRoomId) {
        log.info("getWinner --- method entered");
        List<BidMessage> bidMessages = bidMessageRepository.findByAuctionRoomId(auctionRoomId);
        if (bidMessages.isEmpty()) {
            log.info("getWinner --- not found");
            throw new ResourceNotFoundException("Auction room not found with id: " + auctionRoomId);
        }
        Optional<BidMessage> winnerBidMessageOptional = bidMessages.stream()
                .max(Comparator.comparingDouble(BidMessage::getAmount)
                        .thenComparing(BidMessage::getTimestamp));
        if (winnerBidMessageOptional.isPresent()) {
            BidMessage winnerMessage = winnerBidMessageOptional.get();
            Participant winnerParticipant = winnerMessage.getSender();
            log.info("winner : {}", winnerParticipant);

            String auctionLink = AUCTION_LINK + auctionRoomId;
            EmailMessageRequest request = EmailMessageRequest.builder()
                    .subject("Congratulations! You won the auction!")
                    .to(winnerParticipant.getEmail())
                    .body(auctionLink)
                    .isHtml(false)
                    .build();
            rabbitMQSender.notifyWinner(request);

            return winnerParticipant;
        } else {
            log.info("Winner not found");
            throw new ResourceNotFoundException("Winner not found");
        }
    }
}
