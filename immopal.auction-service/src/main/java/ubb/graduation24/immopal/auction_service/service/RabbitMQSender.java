package ubb.graduation24.immopal.auction_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.auction_service.config.RabbitMqConfig;
import ubb.graduation24.immopal.auction_service.domain.BidMessage;
import ubb.graduation24.immopal.auction_service.exception.ServiceOperationException;
import ubb.graduation24.immopal.auction_service.model.EmailMessageRequest;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitMQSender {

    @Value("${rabbitmq.person.bid.routingkey}")
    private String personNewBidRoutingKey;

    @Value("${rabbitmq.person.winner.routingkey}")
    private String winnerRoutingKey;

    @Value("${rabbitmq.winner.exchange}")
    private String winnerExchangeName;

    private final AmqpTemplate rabbitTemplate;
    private final RabbitMqConfig rabbitMqConfig;

    public void notifyWinner(EmailMessageRequest request) {
        try {
            log.info("notifyWinner() --- RabbitMQ method entered");
            rabbitTemplate.convertAndSend(winnerExchangeName, winnerRoutingKey, request);
            log.info("notifyWinner() sent the winner messsage to EMAIL-SERVICE");
        } catch (AmqpException e) {
            log.error("Error sending message to RabbitMQ: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send message to RabbitMQ", e);
        }
    }

    public void notifyPersonNewBid(BidMessage bidMessage) {
        try {
            log.info("notifyPersonNewBid() --- RabbitMQ method entered");
            String message = bidMessage.getSender().getPersonId() + ":" + bidMessage.getId();
            rabbitTemplate.convertAndSend(rabbitMqConfig.bidExchange().getName(), personNewBidRoutingKey, message);
            log.info("New bid notification sent");
        } catch (AmqpException e) {
            log.error("Error sending message to RabbitMQ: {}", e.getMessage(), e);
            throw new ServiceOperationException("Failed to send message to RabbitMQ");
        }
    }
}
