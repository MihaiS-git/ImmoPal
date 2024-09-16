package ubb.graduation24.immopal.appointment_service.service;

import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.appointment_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.grpc.AgencyOuterClass;
import ubb.graduation24.immopal.grpc.AgencyServiceRPCGrpc;


@Slf4j
@RequiredArgsConstructor
@Service
public class AgencyClientService {

    private final AgencyServiceRPCGrpc.AgencyServiceRPCBlockingStub agencyServiceStub;

    @Retryable(
            value = { StatusRuntimeException.class },
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000)
    )
    public AgencyOuterClass.Agency getAgencyByAgentId(Long agentId) {
        AgencyOuterClass.GetAgencyByAgentIdRequest request = AgencyOuterClass.GetAgencyByAgentIdRequest.newBuilder()
                .setAgentId(agentId)
                .build();
        try {
            log.info("gRPC Requesting Agency by AgentId={}", agentId);
            AgencyOuterClass.GetAgencyByAgentIdResponse response = agencyServiceStub.getAgencyByAgentId(request);
            return response.getAgency();
        } catch (StatusRuntimeException e) {
            log.error("Failed to get Agency by AgentId {} : {}", agentId, e.getMessage());
            if (e.getStatus().getCode() == io.grpc.Status.Code.NOT_FOUND) {
                throw new ResourceNotFoundException("Failed to get Agency by AgentId " + agentId, e);
            }
            throw new RuntimeException("Unhandled gRPC exception: " + e.getStatus().getCode(), e);
        }
    }
}