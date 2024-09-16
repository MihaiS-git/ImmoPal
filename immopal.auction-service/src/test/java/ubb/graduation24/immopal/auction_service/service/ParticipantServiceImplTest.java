package ubb.graduation24.immopal.auction_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ubb.graduation24.immopal.auction_service.domain.Participant;
import ubb.graduation24.immopal.auction_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.auction_service.repository.ParticipantRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ParticipantServiceImplTest {

    @Mock
    private ParticipantRepository participantRepository;

    @InjectMocks
    private ParticipantServiceImpl participantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getParticipantByEmail_ShouldReturnParticipant() {
        // Arrange
        String email = "participant@example.com";
        Participant participant = new Participant();
        participant.setEmail(email);
        when(participantRepository.findByEmail(email)).thenReturn(participant);

        // Act
        Participant result = participantService.getParticipantByEmail(email);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(participantRepository).findByEmail(email);
    }

    @Test
    void getParticipantByEmail_ShouldThrowException_WhenNotFound() {
        // Arrange
        String email = "participant@example.com";
        when(participantRepository.findByEmail(email)).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            participantService.getParticipantByEmail(email);
        });
        assertEquals("Participant not found", exception.getMessage());
        verify(participantRepository).findByEmail(email);
    }

    @Test
    void getParticipantById_ShouldReturnParticipant() {
        // Arrange
        String participantId = "participant-id";
        Participant participant = new Participant();
        participant.setId(participantId);
        when(participantRepository.findById(participantId)).thenReturn(Optional.of(participant));

        // Act
        Participant result = participantService.getParticipantById(participantId);

        // Assert
        assertNotNull(result);
        assertEquals(participantId, result.getId());
        verify(participantRepository).findById(participantId);
    }

    @Test
    void getParticipantById_ShouldThrowException_WhenNotFound() {
        // Arrange
        String participantId = "participant-id";
        when(participantRepository.findById(participantId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            participantService.getParticipantById(participantId);
        });
        assertEquals("Participant not found", exception.getMessage());
        verify(participantRepository).findById(participantId);
    }
}
