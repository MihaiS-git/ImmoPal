package ubb.graduation24.immopal.agency_service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ubb.graduation24.immopal.agency_service.domain.AgencyProperty;
import ubb.graduation24.immopal.agency_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.agency_service.repository.AgencyPropertyRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgencyPropertyServiceImplTest {
    @Mock
    private AgencyPropertyRepository agencyPropertyRepository;

    @InjectMocks
    private AgencyPropertyServiceImpl agencyPropertyService;

    @Test
    void testGetAgencyProperty() {
        // Arrange
        AgencyProperty property1 = new AgencyProperty();
        AgencyProperty property2 = new AgencyProperty();
        when(agencyPropertyRepository.findAll()).thenReturn(Arrays.asList(property1, property2));

        // Act
        List<AgencyProperty> properties = agencyPropertyService.getAgencyProperty();

        // Assert
        assertEquals(2, properties.size());
        verify(agencyPropertyRepository, times(1)).findAll();
    }

    @Test
    void testGetAgencyPropertyThrowsExceptionWhenEmpty() {
        // Arrange
        when(agencyPropertyRepository.findAll()).thenReturn(Arrays.asList());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> agencyPropertyService.getAgencyProperty());
        assertEquals("No agency property found", exception.getMessage());
    }

    @Test
    void testGetAgencyPropertyById() {
        // Arrange
        AgencyProperty property = new AgencyProperty();
        when(agencyPropertyRepository.findById(anyLong())).thenReturn(Optional.of(property));

        // Act
        AgencyProperty result = agencyPropertyService.getAgencyPropertyById(1L);

        // Assert
        assertEquals(property, result);
        verify(agencyPropertyRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAgencyPropertyByIdThrowsExceptionWhenNotFound() {
        // Arrange
        when(agencyPropertyRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> agencyPropertyService.getAgencyPropertyById(1L));
        assertEquals("No agency property found", exception.getMessage());
    }

    @Test
    void testGetPropertiesByAgencyId() {
        // Arrange
        AgencyProperty property1 = new AgencyProperty();
        AgencyProperty property2 = new AgencyProperty();
        when(agencyPropertyRepository.getAllByAgencyId(anyLong())).thenReturn(Arrays.asList(property1, property2));

        // Act
        List<AgencyProperty> properties = agencyPropertyService.getPropertiesByAgencyId(1L);

        // Assert
        assertEquals(2, properties.size());
        verify(agencyPropertyRepository, times(1)).getAllByAgencyId(1L);
    }

    @Test
    void testGetPropertiesByAgencyIdThrowsExceptionWhenEmpty() {
        // Arrange
        when(agencyPropertyRepository.getAllByAgencyId(anyLong())).thenReturn(Arrays.asList());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> agencyPropertyService.getPropertiesByAgencyId(1L));
        assertEquals("No property found", exception.getMessage());
    }

    @Test
    void testCreateAgencyProperty() {
        // Arrange
        AgencyProperty property = new AgencyProperty();
        when(agencyPropertyRepository.save(any(AgencyProperty.class))).thenReturn(property);

        // Act
        AgencyProperty result = agencyPropertyService.createAgencyProperty(property);

        // Assert
        assertEquals(property, result);
        verify(agencyPropertyRepository, times(1)).save(property);
    }

    @Test
    void testUpdateAgencyProperty() {
        // Arrange
        AgencyProperty existingProperty = new AgencyProperty();
        AgencyProperty updatedProperty = new AgencyProperty();
        when(agencyPropertyRepository.findById(anyLong())).thenReturn(Optional.of(existingProperty));
        when(agencyPropertyRepository.save(any(AgencyProperty.class))).thenReturn(updatedProperty);

        // Act
        AgencyProperty result = agencyPropertyService.updateAgencyProperty(1L, updatedProperty);

        // Assert
        assertEquals(updatedProperty, result);
        verify(agencyPropertyRepository, times(1)).findById(1L);
        verify(agencyPropertyRepository, times(1)).save(updatedProperty);
    }

    @Test
    void testUpdateAgencyPropertyThrowsExceptionWhenNotFound() {
        // Arrange
        when(agencyPropertyRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> agencyPropertyService.updateAgencyProperty(1L, new AgencyProperty()));
        assertEquals("No agency property found", exception.getMessage());
    }

    @Test
    void testDeleteAgencyPropertyById() {
        // Arrange
        AgencyProperty property = new AgencyProperty();
        when(agencyPropertyRepository.findById(anyLong())).thenReturn(Optional.of(property));

        // Act
        agencyPropertyService.deleteAgencyPropertyById(1L);

        // Assert
        verify(agencyPropertyRepository, times(1)).findById(1L);
        verify(agencyPropertyRepository, times(1)).delete(property);
    }

    @Test
    void testDeleteAgencyPropertyByIdThrowsExceptionWhenNotFound() {
        // Arrange
        when(agencyPropertyRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> agencyPropertyService.deleteAgencyPropertyById(1L));
        assertEquals("No agency property found", exception.getMessage());
    }
}