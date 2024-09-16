package ubb.graduation24.immopal.appointment_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.appointment_service.config.RabbitMqConfig;
import ubb.graduation24.immopal.appointment_service.domain.Appointment;
import ubb.graduation24.immopal.appointment_service.exception.ServiceOperationException;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitMQSender {

    @Value("${rabbitmq.person.new.appointment.routingkey}")
    private String personNewAppointmentRoutingKey;

    @Value("${rabbitmq.person.delete.appointment.routingkey}")
    private String personDeleteAppointmentRoutingKey;

    private final AmqpTemplate rabbitTemplate;
    private final RabbitMqConfig rabbitMqConfig;


    public void notifyCustomerNewAppointment(Appointment appointment) {
        log.info("notifyCustomerNewAppointment() --- RabbitMQ method entered");
        try {
            String message = appointment.getCustomerId() + ":" + appointment.getId();
            rabbitTemplate.convertAndSend(rabbitMqConfig.directExchange().getName(),
                    personNewAppointmentRoutingKey, message);
            log.info("New appointment notification sent");
        } catch (AmqpException e) {
            log.error("Error sending message to RabbitMQ: {}", e.getMessage(), e);
            throw new ServiceOperationException("Failed to send message to RabbitMQ");
        }
    }

    public void notifyCustomerDeleteAppointment(Appointment appointment) {
        log.info("notifyCustomerNewAppointment() --- RabbitMQ method entered");
        try {
            String message = appointment.getCustomerId() + ":" + appointment.getId();
            rabbitTemplate.convertAndSend(rabbitMqConfig.directExchange().getName(), personDeleteAppointmentRoutingKey, message);
            log.info("Delete appointment notification sent");
        } catch (AmqpException e) {
            log.error("Error sending message to RabbitMQ: {}", e.getMessage(), e);
            throw new ServiceOperationException("Failed to send message to RabbitMQ");
        }
    }

    public void notifyAgentNewAppointment(Appointment appointment) {
        log.info("notifyAgentNewAppointment() --- RabbitMQ method entered");
        try {
            String message = appointment.getAgentId() + ":" + appointment.getId();
            rabbitTemplate.convertAndSend(rabbitMqConfig.directExchange().getName(), personNewAppointmentRoutingKey, message);

            log.info("New appointment notification sent");
        } catch (AmqpException e) {
            log.error("Error sending message to RabbitMQ: {}", e.getMessage(), e);
            throw new ServiceOperationException("Failed to send message to RabbitMQ");
        }
    }

    public void notifyAgentDeleteAppointment(Appointment appointment) {
        log.info("notifyAgentNewAppointment() --- RabbitMQ method entered");
        try {
            String message = appointment.getAgentId() + ":" + appointment.getId();
            rabbitTemplate.convertAndSend(rabbitMqConfig.directExchange().getName(), personDeleteAppointmentRoutingKey, message);

            log.info("New appointment notification sent");
        } catch (AmqpException e) {
            log.error("Error sending message to RabbitMQ: {}", e.getMessage(), e);
            throw new ServiceOperationException("Failed to send message to RabbitMQ");
        }
    }
}
