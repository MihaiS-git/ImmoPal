package ubb.graduation24.immopal.agency_service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ubb.graduation24.immopal.agency_service.domain.Agency;
import ubb.graduation24.immopal.agency_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.agency_service.exception.ServiceOperationException;
import ubb.graduation24.immopal.agency_service.repository.AgencyRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AgencyServiceImplTest {

    @InjectMocks
    private AgencyServiceImpl agencyService;

    @Mock
    private AgencyRepository agencyRepository;

    @Test
    public void testGetAgencies(){
        Agency agency1 = new Agency();
        Agency agency2 = new Agency();
        when(agencyRepository.findAll()).thenReturn(Arrays.asList(agency1, agency2));

        assertEquals(2, agencyService.getAgencies().size());
        verify(agencyRepository, times(1)).findAll();

    }

    @Test
    void testGetAgencies_NoAgencies() {
        when(agencyRepository.findAll()).thenReturn(Arrays.asList());

        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> agencyService.getAgencies());
        assertEquals("Agencies not found", exception.getMessage());
    }

    @Test
    void testGetAgencyById() {
        Long id = 1L;
        Agency agency = new Agency();
        when(agencyRepository.findById(id)).thenReturn(Optional.of(agency));

        assertEquals(agency, agencyService.getAgencyById(id));
        verify(agencyRepository, times(1)).findById(id);
    }

    @Test
    void testGetAgencyById_NotFound() {
        Long id = 1L;
        when(agencyRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> agencyService.getAgencyById(id));
        assertEquals("Agency not found", exception.getMessage());
    }

    @Test
    void testCreateAgency() {
        Agency agency = new Agency();
        when(agencyRepository.save(agency)).thenReturn(agency);

        assertEquals(agency, agencyService.createAgency(agency));
        verify(agencyRepository, times(1)).save(agency);
    }

    @Test
    void testCreateAgency_Exception() {
        Agency agency = new Agency();
        when(agencyRepository.save(agency)).thenThrow(new RuntimeException());

        Exception exception = assertThrows(ServiceOperationException.class,
                () -> agencyService.createAgency(agency));
        assertEquals("Agency creation failed", exception.getMessage());
    }

    @Test
    void testUpdateAgency() {
        Long id = 1L;
        Agency existingAgency = new Agency();
        Agency updatedAgency = new Agency();
        when(agencyRepository.findById(id)).thenReturn(Optional.of(existingAgency));
        when(agencyRepository.save(existingAgency)).thenReturn(updatedAgency);

        assertEquals(updatedAgency, agencyService.updateAgency(id, existingAgency));
        verify(agencyRepository, times(1)).findById(id);
        verify(agencyRepository, times(1)).save(existingAgency);
    }

    @Test
    void testUpdateAgency_NotFound() {
        Long id = 1L;
        Agency agency = new Agency();
        when(agencyRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> agencyService.updateAgency(id, agency));
        assertEquals("Agency not found", exception.getMessage());
    }

    @Test
    void testDeleteAgency() {
        Long id = 1L;
        when(agencyRepository.findById(id)).thenReturn(Optional.of(new Agency()));

        agencyService.deleteAgency(id);
        verify(agencyRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteAgency_NotFound() {
        Long id = 1L;
        when(agencyRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> agencyService.deleteAgency(id));
        assertEquals("Agency not found", exception.getMessage());
    }
}
