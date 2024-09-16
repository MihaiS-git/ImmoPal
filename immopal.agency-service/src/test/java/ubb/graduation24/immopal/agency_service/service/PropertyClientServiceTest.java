package ubb.graduation24.immopal.agency_service.service;

import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ubb.graduation24.immopal.grpc.PropertyOuterClass;
import ubb.graduation24.immopal.grpc.PropertyServiceRPCGrpc;
import ubb.graduation24.immopal.agency_service.exception.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PropertyClientServiceTest {

    @Mock
    private PropertyServiceRPCGrpc.PropertyServiceRPCBlockingStub propertyServiceStub;

    @InjectMocks
    private PropertyClientService propertyClientService;

    @Test
    void testGetPropertyById_Success() {
        Long propertyId = 1L;
        PropertyOuterClass.Property expectedProperty = PropertyOuterClass.Property.newBuilder()
                .setId(propertyId)
                .setPrice(700.00)
                .build();

        PropertyOuterClass.GetPropertyResponse response = PropertyOuterClass.GetPropertyResponse.newBuilder()
                .setProperty(expectedProperty)
                .build();

        when(propertyServiceStub.getPropertyById(any(PropertyOuterClass.GetPropertyRequest.class)))
                .thenReturn(response);

        PropertyOuterClass.Property resultProperty = propertyClientService.getPropertyById(propertyId);

        assertNotNull(resultProperty);
        assertEquals(propertyId, resultProperty.getId());
        assertEquals(700.00, resultProperty.getPrice());
    }

    @Test
    void testGetPropertyById_StatusRuntimeException_NotFound() {
        Long propertyId = 1L;

        // Simulate a NOT_FOUND exception
        when(propertyServiceStub.getPropertyById(any(PropertyOuterClass.GetPropertyRequest.class)))
                .thenThrow(new StatusRuntimeException(io.grpc.Status.NOT_FOUND));

        ResourceNotFoundException thrownException = assertThrows(
                ResourceNotFoundException.class,
                () -> propertyClientService.getPropertyById(propertyId),
                "Expected getPropertyById() to throw ResourceNotFoundException"
        );

        assertTrue(thrownException.getMessage().contains("Failed to get Property with Id " + propertyId));
    }

    @Test
    void testGetPropertyById_StatusRuntimeException_OtherStatus() {
        Long propertyId = 1L;

        // Simulate an UNAVAILABLE exception
        when(propertyServiceStub.getPropertyById(any(PropertyOuterClass.GetPropertyRequest.class)))
                .thenThrow(new StatusRuntimeException(io.grpc.Status.UNAVAILABLE));

        RuntimeException thrownException = assertThrows(
                RuntimeException.class,
                () -> propertyClientService.getPropertyById(propertyId),
                "Expected getPropertyById() to throw RuntimeException"
        );

        assertTrue(thrownException.getMessage().contains("Unhandled gRPC exception: UNAVAILABLE"));
    }
}