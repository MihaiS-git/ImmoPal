package ubb.graduation24.immopal.email.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.email.model.EmailMessageRequest;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitMQReceiver {

    private final EmailService emailService;

    @RabbitListener(queues = "${rabbitmq.email.queue}")
    public void receiveEmailRequest(EmailMessageRequest emailRequest) {
        log.info("RabbitMQReceiver received email message: {}", emailRequest);
        try {
            emailService.sendEmail(emailRequest.getSubject(), emailRequest.getTo(),
                    emailRequest.getBody(), emailRequest.isHtml());
            log.info("RabbitMQReceiver sent email message: {}", emailRequest);
        } catch (MessagingException e) {
            log.error("Failed to send email: {}", e.getMessage());
        }
    }

    @RabbitListener(queues = "${rabbitmq.person.winner.queue}")
    public void receiveWinnerNotification(EmailMessageRequest emailRequest) {
        log.info("Received winner notification: {}", emailRequest);
        try {
            emailService.sendEmail(emailRequest.getSubject(), emailRequest.getTo(),
                    emailRequest.getBody(), emailRequest.isHtml());
            log.info("RabbitMQReceiver sent winner email message: {}", emailRequest);
        } catch (MessagingException e) {
            log.error("Failed to send winner email: {}", e.getMessage());
        }
    }
}
