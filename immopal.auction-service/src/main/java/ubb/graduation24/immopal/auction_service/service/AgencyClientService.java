package ubb.graduation24.immopal.auction_service.service;

import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.auction_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.grpc.AgencyOuterClass;
import ubb.graduation24.immopal.grpc.AgencyServiceRPCGrpc;

@Slf4j
@Service
public class AgencyClientService {
    @Autowired
    private AgencyServiceRPCGrpc.AgencyServiceRPCBlockingStub agencyServiceStub;

    public AgencyOuterClass.Agency getAgencyByAgentId(Long agentId) {
        AgencyOuterClass.GetAgencyByAgentIdRequest request = AgencyOuterClass.GetAgencyByAgentIdRequest.newBuilder()
                .setAgentId(agentId)
                .build();

        try{
            log.info("Get AgencyByAgentId:{}", request);
            AgencyOuterClass.GetAgencyByAgentIdResponse response = agencyServiceStub.getAgencyByAgentId(request);
            log.info("Get AgencyByAgentId:{}", response);
            return response.getAgency();
        } catch (StatusRuntimeException e) {
            log.error("Failed to get Agency by Agent Id {} : {}", agentId, e.getMessage());
            throw new ResourceNotFoundException("Failed to get Agency by Agent Id " + agentId);
        }
    }
}
