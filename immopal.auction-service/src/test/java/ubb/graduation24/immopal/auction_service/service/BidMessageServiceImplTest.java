package ubb.graduation24.immopal.auction_service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BidMessageServiceImplTest {
    private static final String AUCTION_ROOM_ID = "1";
    private static final String PARTICIPANT_EMAIL = "participant@example.com";
    private static final double BID_AMOUNT = 100.0;

    @Mock
    private BidMessageRepository bidMessageRepository;

    @Mock
    private AuctionRoomRepository auctionRoomRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private RabbitMQSender rabbitMQSender;

    @InjectMocks
    private BidMessageServiceImpl bidMessageService;

    @Test
    void placeBidMessage_ShouldThrowException_WhenMessageTypeIsNotBid() {
        BidMessage bidMessage = new BidMessage();
        bidMessage.setType(MessageType.JOIN); // Not BID

        BidRequestException exception = assertThrows(BidRequestException.class, () ->
                bidMessageService.placeBidMessage(AUCTION_ROOM_ID, bidMessage)
        );
        assertEquals("Auction message type is not BID", exception.getMessage());
    }

    @Test
    void placeBidMessage_ShouldThrowException_WhenAuctionRoomNotFound() {
        BidMessage bidMessage = new BidMessage();
        bidMessage.setType(MessageType.BID);
        when(auctionRoomRepository.findById(AUCTION_ROOM_ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                bidMessageService.placeBidMessage(AUCTION_ROOM_ID, bidMessage)
        );
        assertEquals(AUCTION_ROOM_ID, exception.getMessage());
    }

    @Test
    void placeBidMessage_ShouldThrowException_WhenNotInBidInterval() {
        BidMessage bidMessage = new BidMessage();
        bidMessage.setType(MessageType.BID);
        var auctionRoom = new AuctionRoom();
        auctionRoom.setStartDate(LocalDateTime.now(ZoneId.of("Europe/Bucharest")).plusDays(1));
        auctionRoom.setEndDate(LocalDateTime.now(ZoneId.of("Europe/Bucharest")).plusDays(2));
        when(auctionRoomRepository.findById(AUCTION_ROOM_ID)).thenReturn(Optional.of(auctionRoom));

        BidRequestException exception = assertThrows(BidRequestException.class, () ->
                bidMessageService.placeBidMessage(AUCTION_ROOM_ID, bidMessage)
        );
        assertEquals("Current date and time is not in the official bidding interval", exception.getMessage());
    }

    @Test
    void placeBidMessage_ShouldThrowException_WhenBidAmountIsTooLow() {
        BidMessage bidMessage = new BidMessage();
        bidMessage.setType(MessageType.BID);
        bidMessage.setAmount(BID_AMOUNT - 10);
        bidMessage.setTimestamp(LocalDateTime.now(ZoneId.of("Europe/Bucharest")));
        AuctionRoom auctionRoom = new AuctionRoom();
        auctionRoom.setStartDate(LocalDateTime.now(ZoneId.of("Europe/Bucharest")).minusDays(1));
        auctionRoom.setEndDate(LocalDateTime.now(ZoneId.of("Europe/Bucharest")).plusDays(29));
        auctionRoom.setMaxBidAmount(BID_AMOUNT);
        when(auctionRoomRepository.findById(AUCTION_ROOM_ID)).thenReturn(Optional.of(auctionRoom));

        BidRequestException exception = assertThrows(BidRequestException.class, () ->
                bidMessageService.placeBidMessage(AUCTION_ROOM_ID, bidMessage)
        );
        assertEquals("Auction message amount is smaller than last bid amount", exception.getMessage());
    }

    @Test
    void placeBidMessage_ShouldSuccessfullyUpdateAuctionRoom() {
        BidMessage bidMessage = new BidMessage();
        bidMessage.setType(MessageType.BID);
        bidMessage.setAmount(150.0);
        bidMessage.setSender(new Participant());
        bidMessage.getSender().setEmail("participant@example.com");

        AuctionRoom auctionRoom = new AuctionRoom();
        auctionRoom.setId("1");
        auctionRoom.setMaxBidAmount(100.0);
        auctionRoom.setStartDate(LocalDateTime.now(ZoneId.of("Europe/Bucharest")).minusDays(1));
        auctionRoom.setEndDate(LocalDateTime.now(ZoneId.of("Europe/Bucharest")).plusDays(29));
        when(auctionRoomRepository.findById("1")).thenReturn(Optional.of(auctionRoom));

        Participant participant = new Participant();
        participant.setId("participant-id");
        participant.setEmail("participant@example.com");
        when(participantRepository.findByEmail("participant@example.com")).thenReturn(participant);
        when(participantRepository.save(any(Participant.class))).thenReturn(participant);
        when(bidMessageRepository.save(any(BidMessage.class))).thenReturn(bidMessage);

        BidMessage result = bidMessageService.placeBidMessage("1", bidMessage);

        assertNotNull(result);
        assertEquals(150.0, result.getAmount());

        ArgumentCaptor<AuctionRoom> auctionRoomCaptor = ArgumentCaptor.forClass(AuctionRoom.class);
        verify(auctionRoomRepository).save(auctionRoomCaptor.capture());

        AuctionRoom savedAuctionRoom = auctionRoomCaptor.getValue();
        assertEquals(150.0, savedAuctionRoom.getMaxBidAmount());
        assertEquals("participant-id", savedAuctionRoom.getWinnerId());
        assertTrue(savedAuctionRoom.getBids().contains(bidMessage));
    }

    @Test
    void findBidMessagesByAuctionRoomId_ShouldThrowException_WhenNoMessagesFound() {
        when(bidMessageRepository.findByAuctionRoomId(AUCTION_ROOM_ID)).thenReturn(Collections.emptyList());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                bidMessageService.findBidMessagesByAuctionRoomId(AUCTION_ROOM_ID)
        );
        assertEquals("Auction room not found with id: " + AUCTION_ROOM_ID, exception.getMessage());
    }

    @Test
    void placeBidMessage_ShouldThrowException_WhenAuctionRoomDateRangeIsInvalid() {
        BidMessage bidMessage = new BidMessage();
        bidMessage.setType(MessageType.BID);

        AuctionRoom auctionRoom = new AuctionRoom();
        auctionRoom.setStartDate(null);
        auctionRoom.setEndDate(null);
        when(auctionRoomRepository.findById(AUCTION_ROOM_ID)).thenReturn(Optional.of(auctionRoom));

        BidRequestException exception = assertThrows(BidRequestException.class, () ->
                bidMessageService.placeBidMessage(AUCTION_ROOM_ID, bidMessage)
        );
        assertEquals("Auction room has invalid date range", exception.getMessage());
    }

    @Test
    void placeBidMessage_ShouldCreateParticipant_WhenNotFound() {
        BidMessage bidMessage = new BidMessage();
        bidMessage.setType(MessageType.BID);
        bidMessage.setAmount(150.0);
        bidMessage.setTimestamp(LocalDateTime.now(ZoneId.of("Europe/Bucharest")));
        bidMessage.setSender(new Participant());
        bidMessage.getSender().setEmail(PARTICIPANT_EMAIL);

        AuctionRoom auctionRoom = new AuctionRoom();
        auctionRoom.setId(AUCTION_ROOM_ID);
        auctionRoom.setMaxBidAmount(BID_AMOUNT - 10);
        auctionRoom.setStartDate(LocalDateTime.now(ZoneId.of("Europe/Bucharest")).minusDays(1));
        auctionRoom.setEndDate(LocalDateTime.now(ZoneId.of("Europe/Bucharest")).plusDays(29));
        when(auctionRoomRepository.findById(AUCTION_ROOM_ID)).thenReturn(Optional.of(auctionRoom));

        Participant participant = new Participant();
        participant.setId("participant-id");
        participant.setEmail(PARTICIPANT_EMAIL);
        when(participantRepository.findByEmail(PARTICIPANT_EMAIL)).thenReturn(null); // Simulate participant not found
        when(participantRepository.save(any(Participant.class))).thenReturn(participant);
        when(bidMessageRepository.save(any(BidMessage.class))).thenReturn(bidMessage);

        // Act
        BidMessage result = bidMessageService.placeBidMessage(AUCTION_ROOM_ID, bidMessage);

        // Assert
        assertNotNull(result);
        assertEquals(150.0, result.getAmount());
    }


    @Test
    void placeBidMessage_ShouldUpdateExistingParticipant() {
        BidMessage bidMessage = new BidMessage();
        bidMessage.setType(MessageType.BID);
        bidMessage.setAmount(250.0);
        Participant sender = new Participant();
        sender.setEmail(PARTICIPANT_EMAIL);
        bidMessage.setSender(sender);

        Participant existingParticipant = new Participant();
        existingParticipant.setId("existing-participant-id");
        existingParticipant.setEmail(PARTICIPANT_EMAIL);

        AuctionRoom auctionRoom = new AuctionRoom();
        auctionRoom.setId(AUCTION_ROOM_ID);
        auctionRoom.setMaxBidAmount(100.0);
        auctionRoom.setStartDate(LocalDateTime.now(ZoneId.of("Europe/Bucharest")).minusDays(1));
        auctionRoom.setEndDate(LocalDateTime.now(ZoneId.of("Europe/Bucharest")).plusDays(29));

        when(auctionRoomRepository.findById(AUCTION_ROOM_ID)).thenReturn(Optional.of(auctionRoom));
        when(participantRepository.findByEmail(PARTICIPANT_EMAIL)).thenReturn(existingParticipant);
        when(participantRepository.save(any(Participant.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        when(bidMessageRepository.save(any(BidMessage.class))).thenReturn(bidMessage);

        BidMessage result = bidMessageService.placeBidMessage(AUCTION_ROOM_ID, bidMessage);

        assertNotNull(result);
        assertEquals(existingParticipant, result.getSender());
        assertEquals(250.0, result.getAmount());
        verify(participantRepository).save(existingParticipant);
    }


    @Test
    void placeBidMessage_ShouldHandleServiceOperationException_WhenSendingNotificationFails() {
        BidMessage bidMessage = new BidMessage();
        bidMessage.setType(MessageType.BID);
        bidMessage.setAmount(150.0);
        bidMessage.setSender(new Participant());
        bidMessage.getSender().setEmail(PARTICIPANT_EMAIL);

        AuctionRoom auctionRoom = new AuctionRoom();
        auctionRoom.setId(AUCTION_ROOM_ID);
        auctionRoom.setMaxBidAmount(100.0);
        auctionRoom.setStartDate(LocalDateTime.now(ZoneId.of("Europe/Bucharest")).minusDays(1));
        auctionRoom.setEndDate(LocalDateTime.now(ZoneId.of("Europe/Bucharest")).plusDays(29));

        when(auctionRoomRepository.findById(AUCTION_ROOM_ID)).thenReturn(Optional.of(auctionRoom));
        when(participantRepository.findByEmail(PARTICIPANT_EMAIL)).thenReturn(new Participant());
        when(participantRepository.save(any(Participant.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        when(bidMessageRepository.save(any(BidMessage.class))).thenReturn(bidMessage);
        doThrow(new ServiceOperationException("RabbitMQ error")).when(rabbitMQSender)
                .notifyPersonNewBid(any(BidMessage.class));

        BidMessage result = bidMessageService.placeBidMessage(AUCTION_ROOM_ID, bidMessage);

        assertNotNull(result);
        assertEquals(150.0, result.getAmount());
        verify(rabbitMQSender).notifyPersonNewBid(bidMessage);
    }

    @Test
    void findBidMessagesByAuctionRoomId_ShouldReturnEmptyList_WhenNoBidsFound() {
        when(bidMessageRepository.findByAuctionRoomId(AUCTION_ROOM_ID)).thenReturn(Collections.emptyList());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                bidMessageService.findBidMessagesByAuctionRoomId(AUCTION_ROOM_ID)
        );
        assertEquals("Auction room not found with id: " + AUCTION_ROOM_ID, exception.getMessage());
    }

    @Test
    void placeBidMessage_ShouldHandleRabbitMQException() {
        BidMessage bidMessage = new BidMessage();
        bidMessage.setType(MessageType.BID);
        bidMessage.setAmount(150.0);
        bidMessage.setSender(new Participant());
        bidMessage.getSender().setEmail(PARTICIPANT_EMAIL);

        AuctionRoom auctionRoom = new AuctionRoom();
        auctionRoom.setId(AUCTION_ROOM_ID);
        auctionRoom.setMaxBidAmount(BID_AMOUNT - 10);
        auctionRoom.setStartDate(LocalDateTime.now(ZoneId.of("Europe/Bucharest")).minusDays(1));
        auctionRoom.setEndDate(LocalDateTime.now(ZoneId.of("Europe/Bucharest")).plusDays(29));
        when(auctionRoomRepository.findById(AUCTION_ROOM_ID)).thenReturn(Optional.of(auctionRoom));

        Participant participant = new Participant();
        participant.setId("participant-id");
        participant.setEmail(PARTICIPANT_EMAIL);
        when(participantRepository.findByEmail(PARTICIPANT_EMAIL)).thenReturn(participant);
        when(participantRepository.save(any(Participant.class))).thenReturn(participant);
        when(bidMessageRepository.save(any(BidMessage.class))).thenReturn(bidMessage);

        // Simulate exception in RabbitMQ sender
        doThrow(new ServiceOperationException("RabbitMQ error")).when(rabbitMQSender)
                .notifyPersonNewBid(any(BidMessage.class));

        // Act
        BidMessage result = bidMessageService.placeBidMessage(AUCTION_ROOM_ID, bidMessage);

        // Assert
        assertNotNull(result);
        assertEquals(150.0, result.getAmount());
    }



    @Test
    void getTheWinner_ShouldReturnWinner_WhenBidMessagesExist() {
        // Arrange
        Participant participant = new Participant();
        participant.setId("participant-id");
        participant.setEmail(PARTICIPANT_EMAIL);

        BidMessage bidMessage = new BidMessage();
        bidMessage.setAmount(BID_AMOUNT);
        bidMessage.setSender(participant);
        bidMessage.setTimestamp(LocalDateTime.now());

        when(bidMessageRepository.findByAuctionRoomId(AUCTION_ROOM_ID)).thenReturn(Collections.singletonList(bidMessage));

        // Act
        Participant winner = bidMessageService.getTheWinner(AUCTION_ROOM_ID);

        // Assert
        assertEquals(participant, winner);
        ArgumentCaptor<EmailMessageRequest> emailCaptor = ArgumentCaptor.forClass(EmailMessageRequest.class);
        verify(rabbitMQSender).notifyWinner(emailCaptor.capture());

        EmailMessageRequest capturedRequest = emailCaptor.getValue();
        assertEquals("Congratulations! You won the auction!", capturedRequest.getSubject());
        assertEquals(PARTICIPANT_EMAIL, capturedRequest.getTo());
        assertTrue(capturedRequest.getBody().contains(AUCTION_ROOM_ID));
    }

    @Test
    void getTheWinner_ShouldThrowException_WhenNoBidMessagesFound() {
        // Arrange
        when(bidMessageRepository.findByAuctionRoomId(AUCTION_ROOM_ID)).thenReturn(Collections.emptyList());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                bidMessageService.getTheWinner(AUCTION_ROOM_ID)
        );
        assertEquals("Auction room not found with id: " + AUCTION_ROOM_ID, exception.getMessage());
    }


}
