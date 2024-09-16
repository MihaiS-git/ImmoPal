package ubb.graduation24.immopal.auction_service.service;

import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.auction_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.grpc.PropertyOuterClass;
import ubb.graduation24.immopal.grpc.PropertyServiceRPCGrpc;

@Slf4j
@Service
public class PropertyClientService {
    @Autowired
    private PropertyServiceRPCGrpc.PropertyServiceRPCBlockingStub propertyServiceStub;

    public PropertyOuterClass.Property getProperty(Long propertyId) {
        PropertyOuterClass.GetPropertyRequest propertyRequest = PropertyOuterClass.GetPropertyRequest.newBuilder()
                .setPropertyId(propertyId)
                .build();
        try{
            log.debug("gRPC Requesting Property with Id={}", propertyId);
            PropertyOuterClass.GetPropertyResponse response = propertyServiceStub.getPropertyById(propertyRequest);
            log.info("gRPC Response: {}", response);
            return response.getProperty();
        } catch (StatusRuntimeException e) {
            log.error("Failed to get Property with Id {} : {}", propertyId, e.getMessage());
            throw new ResourceNotFoundException("Failed to get Property with id=" + propertyId);
        }
    }
}
