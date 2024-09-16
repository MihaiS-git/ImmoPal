package ubb.graduation24.immopal.appointment_service.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.AmqpTemplate;
import ubb.graduation24.immopal.appointment_service.config.RabbitMqConfig;
import ubb.graduation24.immopal.appointment_service.domain.Appointment;
import ubb.graduation24.immopal.appointment_service.exception.ServiceOperationException;

import java.lang.reflect.Field;

class RabbitMQSenderTest {

    private AmqpTemplate rabbitTemplate;
    private RabbitMqConfig rabbitMqConfig;
    private RabbitMQSender rabbitMQSender;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        rabbitTemplate = Mockito.mock(AmqpTemplate.class);
        rabbitMqConfig = Mockito.mock(RabbitMqConfig.class);

        rabbitMQSender = new RabbitMQSender(rabbitTemplate, rabbitMqConfig);

        // Use reflection to set private fields
        setField(rabbitMQSender, "personNewAppointmentRoutingKey", "person.new.appointment.routingkey");
        setField(rabbitMQSender, "personDeleteAppointmentRoutingKey", "person.delete.appointment.routingkey");
    }

    private void setField(Object target, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void notifyCustomerNewAppointment_Success() {
        // Arrange
        when(rabbitMqConfig.directExchange()).thenReturn(
                new DirectExchange("appointment.direct.exchange", false, false));

        Appointment appointment = new Appointment();
        appointment.setCustomerId(1L);
        appointment.setId(2L);

        // Mock successful send
        doNothing().when(rabbitTemplate).convertAndSend(eq("appointment.direct.exchange"),
                eq("person.new.appointment.routingkey"),
                eq("1:2"));

        // Act
        rabbitMQSender.notifyCustomerNewAppointment(appointment);

        // Assert
        verify(rabbitTemplate).convertAndSend(eq("appointment.direct.exchange"),
                eq("person.new.appointment.routingkey"),
                eq("1:2"));
    }

    @Test
    void notifyCustomerNewAppointment_Failure() {
        // Arrange
        when(rabbitMqConfig.directExchange())
                .thenReturn(new DirectExchange("appointment.direct.exchange", false, false));

        Appointment appointment = new Appointment();
        appointment.setCustomerId(1L);
        appointment.setId(2L);

        // Mock to throw exception
        doThrow(new AmqpException("Failed to send"))
                .when(rabbitTemplate).convertAndSend(eq("appointment.direct.exchange"),
                        eq("person.new.appointment.routingkey"),
                        eq("1:2"));

        // Act & Assert
        assertThrows(ServiceOperationException.class, () -> rabbitMQSender.notifyCustomerNewAppointment(appointment));

        // Verify that convertAndSend was indeed called
        verify(rabbitTemplate).convertAndSend(eq("appointment.direct.exchange"),
                eq("person.new.appointment.routingkey"),
                eq("1:2"));
    }

    @Test
    void notifyCustomerDeleteAppointment_Success() {
        // Arrange
        when(rabbitMqConfig.directExchange())
                .thenReturn(new DirectExchange("appointment.direct.exchange", false, false));

        Appointment appointment = new Appointment();
        appointment.setCustomerId(1L);
        appointment.setId(2L);

        // Mock successful send
        doNothing().when(rabbitTemplate)
                .convertAndSend(
                        eq("appointment.direct.exchange"),
                        eq("person.delete.appointment.routingkey"),
                        eq("1:2"));

        // Act
        rabbitMQSender.notifyCustomerDeleteAppointment(appointment);

        // Assert
        verify(rabbitTemplate)
                .convertAndSend(
                        eq("appointment.direct.exchange"),
                        eq("person.delete.appointment.routingkey"),
                        eq("1:2"));
    }

    @Test
    void notifyCustomerDeleteAppointment_Failure() {
        // Arrange
        when(rabbitMqConfig.directExchange())
                .thenReturn(new DirectExchange("appointment.direct.exchange", false, false));

        Appointment appointment = new Appointment();
        appointment.setCustomerId(1L);
        appointment.setId(2L);

        // Mock to throw exception
        doThrow(new AmqpException("Failed to send"))
                .when(rabbitTemplate).convertAndSend(
                        eq("appointment.direct.exchange"),
                        eq("person.delete.appointment.routingkey"),
                        eq("1:2"));

        // Act & Assert
        assertThrows(ServiceOperationException.class, () -> rabbitMQSender.notifyCustomerDeleteAppointment(appointment));

        // Verify that convertAndSend was indeed called
        verify(rabbitTemplate)
                .convertAndSend(
                        eq("appointment.direct.exchange"),
                        eq("person.delete.appointment.routingkey"),
                        eq("1:2"));
    }

    @Test
    void notifyAgentNewAppointment_Success() {
        // Arrange
        when(rabbitMqConfig.directExchange())
                .thenReturn(new DirectExchange("appointment.direct.exchange", false, false));

        Appointment appointment = new Appointment();
        appointment.setAgentId(1L);
        appointment.setId(2L);

        // Mock successful send
        doNothing().when(rabbitTemplate)
                .convertAndSend(
                        eq("appointment.direct.exchange"),
                        eq("person.new.appointment.routingkey"),
                        eq("1:2"));

        // Act
        rabbitMQSender.notifyAgentNewAppointment(appointment);

        // Assert
        verify(rabbitTemplate)
                .convertAndSend(
                        eq("appointment.direct.exchange"),
                        eq("person.new.appointment.routingkey"),
                        eq("1:2"));
    }

    @Test
    void notifyAgentNewAppointment_Failure() {
        // Arrange
        when(rabbitMqConfig.directExchange())
                .thenReturn(new DirectExchange("appointment.direct.exchange", false, false));

        Appointment appointment = new Appointment();
        appointment.setAgentId(1L);
        appointment.setId(2L);

        // Mock to throw exception
        doThrow(new AmqpException("Failed to send"))
                .when(rabbitTemplate)
                .convertAndSend(
                        eq("appointment.direct.exchange"),
                        eq("person.new.appointment.routingkey"),
                        eq("1:2"));

        // Act & Assert
        assertThrows(ServiceOperationException.class, () -> rabbitMQSender.notifyAgentNewAppointment(appointment));

        // Verify that convertAndSend was indeed called
        verify(rabbitTemplate)
                .convertAndSend(
                        eq("appointment.direct.exchange"),
                        eq("person.new.appointment.routingkey"),
                        eq("1:2"));
    }

    @Test
    void notifyAgentDeleteAppointment_Success() {
        // Arrange
        when(rabbitMqConfig.directExchange())
                .thenReturn(new DirectExchange("appointment.direct.exchange", false, false));

        Appointment appointment = new Appointment();
        appointment.setAgentId(1L);
        appointment.setId(2L);

        // Mock successful send
        doNothing().when(rabbitTemplate)
                .convertAndSend(
                        eq("appointment.direct.exchange"),
                        eq("person.delete.appointment.routingkey"),
                        eq("1:2"));

        // Act
        rabbitMQSender.notifyAgentDeleteAppointment(appointment);

        // Assert
        verify(rabbitTemplate)
                .convertAndSend(
                        eq("appointment.direct.exchange"),
                        eq("person.delete.appointment.routingkey"),
                        eq("1:2"));
    }

    @Test
    void notifyAgentDeleteAppointment_Failure() {
        // Arrange
        when(rabbitMqConfig.directExchange())
                .thenReturn(new DirectExchange("appointment.direct.exchange", false, false));

        Appointment appointment = new Appointment();
        appointment.setAgentId(1L);
        appointment.setId(2L);

        // Mock to throw exception
        doThrow(new AmqpException("Failed to send"))
                .when(rabbitTemplate)
                .convertAndSend(
                        eq("appointment.direct.exchange"),
                        eq("person.delete.appointment.routingkey"),
                        eq("1:2"));

        // Act & Assert
        assertThrows(ServiceOperationException.class, () -> rabbitMQSender.notifyAgentDeleteAppointment(appointment));

        // Verify that convertAndSend was indeed called
        verify(rabbitTemplate)
                .convertAndSend(
                        eq("appointment.direct.exchange"),
                        eq("person.delete.appointment.routingkey"),
                        eq("1:2"));
    }
}