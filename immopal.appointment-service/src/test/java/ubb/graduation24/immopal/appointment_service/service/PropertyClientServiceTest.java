package ubb.graduation24.immopal.appointment_service.service;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ubb.graduation24.immopal.appointment_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.grpc.PropertyOuterClass;
import ubb.graduation24.immopal.grpc.PropertyServiceRPCGrpc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PropertyClientServiceTest {

    @Mock
    private PropertyServiceRPCGrpc.PropertyServiceRPCBlockingStub propertyServiceStub;

    @InjectMocks
    private PropertyClientService propertyClientService;

    @Test
    void getProperty_Success() {
        // Given
        Long propertyId = 1L;
        PropertyOuterClass.Property property = PropertyOuterClass.Property.newBuilder()
                .setId(propertyId)
                .build();
        PropertyOuterClass.GetPropertyResponse response = PropertyOuterClass.GetPropertyResponse.newBuilder()
                .setProperty(property)
                .build();

        when(propertyServiceStub.getPropertyById(any(PropertyOuterClass.GetPropertyRequest.class)))
                .thenReturn(response);

        // When
        PropertyOuterClass.Property result = propertyClientService.getProperty(propertyId);

        // Then
        assertEquals(propertyId, result.getId());
        verify(propertyServiceStub, times(1)).getPropertyById(any(PropertyOuterClass.GetPropertyRequest.class));
    }

    @Test
    void getProperty_Failure_NotFound() {
        // Given
        Long propertyId = 1L;
        when(propertyServiceStub.getPropertyById(any(PropertyOuterClass.GetPropertyRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.NOT_FOUND));

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            propertyClientService.getProperty(propertyId);
        });

        assertEquals("Failed to get Property with Id " + propertyId, exception.getMessage());
        verify(propertyServiceStub, times(1)).getPropertyById(any(PropertyOuterClass.GetPropertyRequest.class));
    }

    @Test
    void getProperty_Failure_OtherStatus() {
        // Given
        Long propertyId = 1L;

        // Simulate UNAVAILABLE
        when(propertyServiceStub.getPropertyById(any(PropertyOuterClass.GetPropertyRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.UNAVAILABLE));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            propertyClientService.getProperty(propertyId);
        });

        assertEquals("Unhandled gRPC exception: UNAVAILABLE", exception.getMessage());
        verify(propertyServiceStub, times(1)).getPropertyById(any(PropertyOuterClass.GetPropertyRequest.class));
    }
}