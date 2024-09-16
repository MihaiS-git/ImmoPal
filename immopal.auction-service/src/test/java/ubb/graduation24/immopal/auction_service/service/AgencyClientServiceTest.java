package ubb.graduation24.immopal.auction_service.service;

import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ubb.graduation24.immopal.auction_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.grpc.AgencyOuterClass;
import ubb.graduation24.immopal.grpc.AgencyServiceRPCGrpc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgencyClientServiceTest {

    @Mock
    private AgencyServiceRPCGrpc.AgencyServiceRPCBlockingStub agencyServiceStub;

    @InjectMocks
    private AgencyClientService agencyClientService;

    private AgencyOuterClass.GetAgencyByAgentIdRequest request;
    private AgencyOuterClass.GetAgencyByAgentIdResponse response;
    private AgencyOuterClass.Agency agency;

    @BeforeEach
    void setUp() {
        request = AgencyOuterClass.GetAgencyByAgentIdRequest.newBuilder().setAgentId(1L).build();
        agency = AgencyOuterClass.Agency.newBuilder().setId(1L).setName("Test Agency").build();
        response = AgencyOuterClass.GetAgencyByAgentIdResponse.newBuilder().setAgency(agency).build();
    }

    @Test
    void testGetAgencyByAgentId_Success() {
        when(agencyServiceStub.getAgencyByAgentId(any(AgencyOuterClass.GetAgencyByAgentIdRequest.class)))
                .thenReturn(response);

        AgencyOuterClass.Agency result = agencyClientService.getAgencyByAgentId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Agency", result.getName());

        verify(agencyServiceStub, times(1))
                .getAgencyByAgentId(any(AgencyOuterClass.GetAgencyByAgentIdRequest.class));
    }

    @Test
    void testGetAgencyByAgentId_NotFound() {
        when(agencyServiceStub.getAgencyByAgentId(any(AgencyOuterClass.GetAgencyByAgentIdRequest.class)))
                .thenThrow(new StatusRuntimeException(io.grpc.Status.NOT_FOUND));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            agencyClientService.getAgencyByAgentId(1L);
        });

        assertEquals("Failed to get Agency by Agent Id 1", exception.getMessage());

        verify(agencyServiceStub, times(1))
                .getAgencyByAgentId(any(AgencyOuterClass.GetAgencyByAgentIdRequest.class));
    }
}
