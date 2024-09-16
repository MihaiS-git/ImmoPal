package ubb.graduation24.immopal.auction_service.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import ubb.graduation24.immopal.auction_service.config.RabbitMqConfig;
import ubb.graduation24.immopal.auction_service.domain.BidMessage;
import ubb.graduation24.immopal.auction_service.domain.Participant;
import ubb.graduation24.immopal.auction_service.exception.ServiceOperationException;
import ubb.graduation24.immopal.auction_service.model.EmailMessageRequest;

import java.lang.reflect.Field;

class RabbitMQSenderTest {
    @Mock
    private AmqpTemplate rabbitTemplate;

    @Mock
    private RabbitMqConfig rabbitMqConfig;

    private RabbitMQSender rabbitMQSender;

    private static final String WINNER_ROUTING_KEY = "winner.routingkey";
    private static final String WINNER_EXCHANGE_NAME = "winner.exchange";
    private static final String PERSON_NEW_BID_ROUTING_KEY = "person.bid.routingkey";
    private static final String BID_EXCHANGE_NAME = "bid.exchange";

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        rabbitMQSender = new RabbitMQSender(rabbitTemplate, rabbitMqConfig);

        // Use reflection to set private fields
        setPrivateField(rabbitMQSender, "winnerRoutingKey", WINNER_ROUTING_KEY);
        setPrivateField(rabbitMQSender, "winnerExchangeName", WINNER_EXCHANGE_NAME);
        setPrivateField(rabbitMQSender, "personNewBidRoutingKey", PERSON_NEW_BID_ROUTING_KEY);

        // Mock RabbitMqConfig behavior
        DirectExchange directExchange = mock(DirectExchange.class);
        when(directExchange.getName()).thenReturn(BID_EXCHANGE_NAME);
        when(rabbitMqConfig.bidExchange()).thenReturn(directExchange);
    }


    private void setPrivateField(Object targetObject, String fieldName, Object value) throws Exception {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }

    @Test
    void notifyWinner_ShouldSendEmailMessageRequestSuccessfully() {
        // Arrange
        EmailMessageRequest request = new EmailMessageRequest("Subject", "test@example.com", "Body", false);

        // Act
        rabbitMQSender.notifyWinner(request);

        // Assert
        verify(rabbitTemplate).convertAndSend(WINNER_EXCHANGE_NAME, WINNER_ROUTING_KEY, request);
    }


    @Test
    void notifyWinner_ShouldThrowRuntimeException_WhenAmqpExceptionOccurs() {
        // Arrange
        EmailMessageRequest request = new EmailMessageRequest("Subject", "test@example.com", "Body", false);
        doThrow(new AmqpException("RabbitMQ error")).when(rabbitTemplate)
                .convertAndSend(anyString(), anyString(), any(Object.class));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> rabbitMQSender.notifyWinner(request));

        // Verify the correct method was called
        verify(rabbitTemplate).convertAndSend(anyString(), anyString(), eq(request));
    }

    @Test
    void notifyPersonNewBid_ShouldSendBidMessageSuccessfully() {
        // Arrange
        Participant participant = mock(Participant.class);
        when(participant.getPersonId()).thenReturn(123L);

        BidMessage bidMessage = mock(BidMessage.class);
        when(bidMessage.getSender()).thenReturn(participant);
        when(bidMessage.getId()).thenReturn("bidId");

        String expectedMessage = "123:bidId";

        // Act
        rabbitMQSender.notifyPersonNewBid(bidMessage);

        // Assert
        verify(rabbitTemplate).convertAndSend(
                BID_EXCHANGE_NAME,
                PERSON_NEW_BID_ROUTING_KEY,
                expectedMessage
        );
    }

    @Test
    void notifyPersonNewBid_ShouldThrowServiceOperationException_WhenAmqpExceptionOccurs() {
        // Arrange
        BidMessage bidMessage = mock(BidMessage.class);

        // Mock the Participant object
        Participant participant = mock(Participant.class);
        when(participant.getPersonId()).thenReturn(123L);

        // Mock the BidMessage to return the Participant object
        when(bidMessage.getSender()).thenReturn(participant);
        when(bidMessage.getId()).thenReturn("bidId");

        // Mock `convertAndSend` to throw `AmqpException`
        doThrow(new AmqpException("RabbitMQ error")).when(rabbitTemplate)
                .convertAndSend(eq(BID_EXCHANGE_NAME), eq(PERSON_NEW_BID_ROUTING_KEY), anyString());

        // Act & Assert
        ServiceOperationException exception = assertThrows(ServiceOperationException.class, () ->
                rabbitMQSender.notifyPersonNewBid(bidMessage)
        );

        assertEquals("Failed to send message to RabbitMQ", exception.getMessage());
        verify(rabbitTemplate).convertAndSend(eq(BID_EXCHANGE_NAME), eq(PERSON_NEW_BID_ROUTING_KEY), anyString());
    }
}
