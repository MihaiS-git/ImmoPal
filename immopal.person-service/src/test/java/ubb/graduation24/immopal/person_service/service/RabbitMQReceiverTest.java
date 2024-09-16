package ubb.graduation24.immopal.person_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ubb.graduation24.immopal.person_service.domain.Person;
import ubb.graduation24.immopal.person_service.repository.PersonRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RabbitMQReceiverTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private RabbitMQReceiver rabbitMQReceiver;

    private Person person;

    @BeforeEach
    void setUp() {
        person = Person.builder()
                .id(1L)
                .appointmentIds(new ArrayList<>())
                .build();
    }

    @Test
    void testReceivePersonNewAppointmentNotification_Success() {
        // Arrange
        String message = "1:1001";
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        // Act
        rabbitMQReceiver.receivePersonNewAppointmentNotification(message);

        // Assert
        verify(personRepository).save(person);
        assertTrue(person.getAppointmentIds().contains(1001L));
    }

    @Test
    void testReceivePersonNewAppointmentNotification_PersonNotFound() {
        // Arrange
        String message = "1:1001";
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        rabbitMQReceiver.receivePersonNewAppointmentNotification(message);

        // Assert
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    void testReceivePersonDeleteAppointmentNotification_Success() {
        // Arrange
        person.getAppointmentIds().add(1001L);
        String message = "1:1001";
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        // Act
        rabbitMQReceiver.receivePersonDeleteAppointmentNotification(message);

        // Assert
        verify(personRepository).save(person);
        assertFalse(person.getAppointmentIds().contains(1001L));
    }

    @Test
    void testReceivePersonDeleteAppointmentNotification_PersonNotFound() {
        // Arrange
        String message = "1:1001";
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        rabbitMQReceiver.receivePersonDeleteAppointmentNotification(message);

        // Assert
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    void testReceiveNewBidNotification_Success() {
        // Arrange
        String message = "1:3";
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        // Act
        rabbitMQReceiver.receiveNewBidNotification(message);

        // Assert
        verify(personRepository).save(person);
        assertTrue(person.getBidIds().contains("3"));
    }

    @Test
    void testReceiveNewBidNotification_PersonNotFound() {
        // Arrange
        String message = "1:3";
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        rabbitMQReceiver.receiveNewBidNotification(message);

        // Assert
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    void testReceiveNewBidNotification_InvalidMessageFormat() {
        // Arrange
        String message = "invalidMessageFormat";

        // Act
        try {
            rabbitMQReceiver.receiveNewBidNotification(message);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        // Assert
        // Verify that personRepository.save was never called
        verify(personRepository, never()).save(any(Person.class));
    }
}
