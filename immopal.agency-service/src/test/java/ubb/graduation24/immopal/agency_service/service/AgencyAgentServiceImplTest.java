package ubb.graduation24.immopal.agency_service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ubb.graduation24.immopal.agency_service.domain.AgencyAgent;
import ubb.graduation24.immopal.agency_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.agency_service.repository.AgencyAgentRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgencyAgentServiceImplTest {

    @InjectMocks
    private AgencyAgentServiceImpl agencyAgentService;

    @Mock
    private AgencyAgentRepository agencyAgentRepository;

    @Test
    void testGetAgencyAgents() {
        // Arrange
        AgencyAgent agent1 = new AgencyAgent();
        AgencyAgent agent2 = new AgencyAgent();
        when(agencyAgentRepository.findAll()).thenReturn(Arrays.asList(agent1, agent2));

        // Act
        List<AgencyAgent> agents = agencyAgentService.getAgencyAgents();

        // Assert
        assertEquals(2, agents.size(), "Expected 2 agents but got a different number");
        verify(agencyAgentRepository, times(1)).findAll();
    }

    @Test
    void testGetAgencyAgentsThrowsExceptionWhenEmpty() {
        // Arrange
        when(agencyAgentRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> agencyAgentService.getAgencyAgents(),
                "Expected getAgencyAgents() to throw ResourceNotFoundException, but it didn't.");
    }

    @Test
    void testGetAgencyAgentById() {
        // Arrange
        Long id = 1L;
        AgencyAgent agent = new AgencyAgent();
        when(agencyAgentRepository.findById(id)).thenReturn(Optional.of(agent));

        // Act
        AgencyAgent result = agencyAgentService.getAgencyAgentById(id);

        // Assert
        assertEquals(agent, result, "Expected the returned agent to match the one retrieved by ID");
        verify(agencyAgentRepository, times(1)).findById(id);
    }

    @Test
    void testGetAgencyAgentByIdThrowsExceptionWhenNotFound() {
        // Arrange
        Long id = 1L;
        when(agencyAgentRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> agencyAgentService.getAgencyAgentById(id),
                "Expected getAgencyAgentById() to throw ResourceNotFoundException, but it didn't.");
    }

    @Test
    void testGetAgencyAgentsByAgencyId() {
        // Arrange
        Long agencyId = 1L;
        AgencyAgent agent1 = new AgencyAgent();
        AgencyAgent agent2 = new AgencyAgent();
        when(agencyAgentRepository.getAllByAgencyId(agencyId)).thenReturn(Arrays.asList(agent1, agent2));

        // Act
        List<AgencyAgent> agents = agencyAgentService.getAgencyAgentsByAgencyId(agencyId);

        // Assert
        assertEquals(2, agents.size(), "Expected 2 agents but got a different number");
        verify(agencyAgentRepository, times(1)).getAllByAgencyId(agencyId);
    }

    @Test
    void testGetAgencyAgentsByAgencyIdThrowsExceptionWhenEmpty() {
        // Arrange
        Long agencyId = 1L;
        when(agencyAgentRepository.getAllByAgencyId(agencyId)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> agencyAgentService.getAgencyAgentsByAgencyId(agencyId),
                "Expected getAgencyAgentsByAgencyId() to throw ResourceNotFoundException, but it didn't.");
    }

    @Test
    void testCreateAgencyAgent() {
        // Arrange
        AgencyAgent agent = new AgencyAgent();
        when(agencyAgentRepository.save(agent)).thenReturn(agent);

        // Act
        AgencyAgent result = agencyAgentService.createAgencyAgent(agent);

        // Assert
        assertEquals(agent, result, "Expected the returned agent to match the created one");
        verify(agencyAgentRepository, times(1)).save(agent);
    }

    @Test
    void testUpdateAgencyAgent() {
        // Arrange
        Long id = 1L;
        AgencyAgent existingAgent = new AgencyAgent();
        AgencyAgent updatedAgent = new AgencyAgent();
        when(agencyAgentRepository.findById(id)).thenReturn(Optional.of(existingAgent));
        when(agencyAgentRepository.save(existingAgent)).thenReturn(existingAgent);

        // Act
        AgencyAgent result = agencyAgentService.updateAgencyAgent(id, updatedAgent);

        // Assert
        assertEquals(existingAgent, result, "Expected the returned agent to match the updated one");
        verify(agencyAgentRepository, times(1)).findById(id);
        verify(agencyAgentRepository, times(1)).save(existingAgent);
    }

    @Test
    void testUpdateAgencyAgentThrowsExceptionWhenNotFound() {
        // Arrange
        Long id = 1L;
        AgencyAgent updatedAgent = new AgencyAgent();
        when(agencyAgentRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> agencyAgentService.updateAgencyAgent(id, updatedAgent),
                "Expected updateAgencyAgent() to throw ResourceNotFoundException, but it didn't.");
    }

    @Test
    void testDeleteAgencyAgent() {
        // Arrange
        Long id = 1L;
        AgencyAgent agent = new AgencyAgent();
        when(agencyAgentRepository.findById(id)).thenReturn(Optional.of(agent));

        // Act
        agencyAgentService.deleteAgencyAgent(id);

        // Assert
        verify(agencyAgentRepository, times(1)).findById(id);
        verify(agencyAgentRepository, times(1)).delete(agent);
    }

    @Test
    void testDeleteAgencyAgentThrowsExceptionWhenNotFound() {
        // Arrange
        Long id = 1L;
        when(agencyAgentRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> agencyAgentService.deleteAgencyAgent(id),
                "Expected deleteAgencyAgent() to throw ResourceNotFoundException, but it didn't.");
    }
}
