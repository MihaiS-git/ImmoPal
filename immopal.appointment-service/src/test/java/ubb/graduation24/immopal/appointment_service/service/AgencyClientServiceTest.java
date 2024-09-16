package ubb.graduation24.immopal.appointment_service.service;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ubb.graduation24.immopal.appointment_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.grpc.AgencyOuterClass;
import ubb.graduation24.immopal.grpc.AgencyServiceRPCGrpc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AgencyClientServiceTest {

    @InjectMocks
    private AgencyClientService agencyClientService;

    @Mock
    private AgencyServiceRPCGrpc.AgencyServiceRPCBlockingStub agencyServiceStub;

    @Captor
    private ArgumentCaptor<AgencyOuterClass.GetAgencyByAgentIdRequest> requestCaptor;

    @Test
    void testGetAgencyByAgentId_Success() {
        Long agentId = 1L;
        AgencyOuterClass.Agency expectedAgency = AgencyOuterClass.Agency.newBuilder()
                .setId(2L)
                .setName("Test Agency")
                .build();
        AgencyOuterClass.GetAgencyByAgentIdResponse response = AgencyOuterClass.GetAgencyByAgentIdResponse.newBuilder()
                .setAgency(expectedAgency)
                .build();

        when(agencyServiceStub.getAgencyByAgentId(any(AgencyOuterClass.GetAgencyByAgentIdRequest.class)))
                .thenReturn(response);

        AgencyOuterClass.Agency actualAgency = agencyClientService.getAgencyByAgentId(agentId);

        verify(agencyServiceStub).getAgencyByAgentId(requestCaptor.capture());
        assertEquals(agentId, requestCaptor.getValue().getAgentId());
        assertEquals(expectedAgency, actualAgency);
    }

    @Test
    void testGetAgencyByAgentId_Failure_NotFound() {
        Long agentId = 1L;
        when(agencyServiceStub.getAgencyByAgentId(any(AgencyOuterClass.GetAgencyByAgentIdRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.NOT_FOUND.withDescription("Agency not found")));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            agencyClientService.getAgencyByAgentId(agentId);
        });

        verify(agencyServiceStub).getAgencyByAgentId(requestCaptor.capture());
        assertEquals(agentId, requestCaptor.getValue().getAgentId());
        assertEquals("Failed to get Agency by AgentId " + agentId, exception.getMessage());
    }

    @Test
    void testGetAgencyByAgentId_Failure_OtherStatus() {
        Long agentId = 1L;
        when(agencyServiceStub.getAgencyByAgentId(any(AgencyOuterClass.GetAgencyByAgentIdRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.UNAVAILABLE.withDescription("Service unavailable")));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            agencyClientService.getAgencyByAgentId(agentId);
        });

        assertEquals("Unhandled gRPC exception: UNAVAILABLE", exception.getMessage());
        verify(agencyServiceStub).getAgencyByAgentId(requestCaptor.capture());
        assertEquals(agentId, requestCaptor.getValue().getAgentId());
    }
}