package ubb.graduation24.immopal.security_service.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import ubb.graduation24.immopal.security_service.model.EmailMessageRequest;

@ExtendWith(MockitoExtension.class)
class RabbitMQSenderTest {

    @Value("${rabbitmq.email.routingkey}")
    private String emailRoutingKey;

    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private RabbitMQSender rabbitMQSender;

    @Test
    void testSendEmail_Success() {
        EmailMessageRequest emailRequest = new EmailMessageRequest();
        emailRequest.setTo("test@example.com");
        emailRequest.setSubject("Test Subject");
        emailRequest.setBody("Test Body");

        // Mock RabbitTemplate's convertAndSend method
        doNothing().when(rabbitTemplate).convertAndSend(eq(exchangeName), eq(emailRoutingKey),
                any(EmailMessageRequest.class));

        // Call the method under test
        rabbitMQSender.sendEmail(emailRequest);

        // Verify that convertAndSend was called with the expected parameters
        verify(rabbitTemplate, times(1)).convertAndSend(eq(exchangeName),
                eq(emailRoutingKey), eq(emailRequest));
    }

}