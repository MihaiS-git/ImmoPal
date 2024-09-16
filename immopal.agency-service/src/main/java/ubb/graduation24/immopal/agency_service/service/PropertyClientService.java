package ubb.graduation24.immopal.agency_service.service;

import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.agency_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.grpc.PropertyOuterClass;
import ubb.graduation24.immopal.grpc.PropertyServiceRPCGrpc;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PropertyClientService {

    private final PropertyServiceRPCGrpc.PropertyServiceRPCBlockingStub propertyServiceStub;

    @Retryable(
            value = { StatusRuntimeException.class },
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000)
    )
    public PropertyOuterClass.Property getPropertyById(Long propertyId) {
        PropertyOuterClass.GetPropertyRequest request = PropertyOuterClass.GetPropertyRequest.newBuilder()
                .setPropertyId(propertyId)
                .build();
        try {
            log.info("gRPC Requesting Property with Id={}", propertyId);
            PropertyOuterClass.GetPropertyResponse response = propertyServiceStub.getPropertyById(request);
            return response.getProperty();
        } catch (StatusRuntimeException e) {
            log.error("Failed to get Property with Id {} : {}", propertyId, e.getMessage());
            if (e.getStatus().getCode() == io.grpc.Status.Code.NOT_FOUND) {
                throw new ResourceNotFoundException("Failed to get Property with Id " + propertyId, e);
            }
            throw new RuntimeException("Unhandled gRPC exception: " + e.getStatus().getCode(), e);
        }
    }
}
