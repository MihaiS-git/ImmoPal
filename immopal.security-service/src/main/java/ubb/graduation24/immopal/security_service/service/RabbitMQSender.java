package ubb.graduation24.immopal.security_service.service;

import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.security_service.model.EmailMessageRequest;

@Slf4j
@Service
public class RabbitMQSender {

    @Value("${rabbitmq.email.routingkey}")
    private String emailRoutingKey;

    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmail(EmailMessageRequest emailRequest) {
        log.info("RabbitMQSender sendEmail() --- method entered");
        try {
            rabbitTemplate.convertAndSend(exchangeName, emailRoutingKey, emailRequest);
        } catch (AmqpException e) {
            log.error("Error sending message to RabbitMQ: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send message to RabbitMQ", e);
        }
    }

}
