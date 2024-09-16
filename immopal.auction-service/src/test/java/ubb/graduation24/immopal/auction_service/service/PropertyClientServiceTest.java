package ubb.graduation24.immopal.auction_service.service;

import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ubb.graduation24.immopal.auction_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.grpc.PropertyOuterClass;
import ubb.graduation24.immopal.grpc.PropertyServiceRPCGrpc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PropertyClientServiceTest {

    @Mock
    private PropertyServiceRPCGrpc.PropertyServiceRPCBlockingStub propertyServiceStub;

    @InjectMocks
    private PropertyClientService propertyClientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProperty_ShouldReturnProperty_WhenSuccess() {
        // Arrange
        PropertyOuterClass.Address addres = PropertyOuterClass.Address.newBuilder()
                .setId(1L)
                .setCountry("country")
                .setState("state")
                .setCity("city")
                .setNeighborhood("neighborhood")
                .setStreet("street")
                .setStreetNo("1A")
                .setLatitude(1.00)
                .setLongitude(2.00)
                .build();

        Long propertyId = 1L;
        PropertyOuterClass.Property expectedProperty = PropertyOuterClass.Property.newBuilder()
                .setId(propertyId)
                .setAddress(addres)
                .build();
        PropertyOuterClass.GetPropertyResponse response = PropertyOuterClass.GetPropertyResponse.newBuilder()
                .setProperty(expectedProperty)
                .build();
        PropertyOuterClass.GetPropertyRequest request = PropertyOuterClass.GetPropertyRequest.newBuilder()
                .setPropertyId(propertyId)
                .build();

        when(propertyServiceStub.getPropertyById(request)).thenReturn(response);

        // Act
        PropertyOuterClass.Property property = propertyClientService.getProperty(propertyId);

        // Assert
        assertEquals(expectedProperty, property);
        verify(propertyServiceStub, times(1)).getPropertyById(request);
    }

    @Test
    void getProperty_ShouldThrowException_WhenGrpcCallFails() {
        // Arrange
        Long propertyId = 1L;
        PropertyOuterClass.GetPropertyRequest request = PropertyOuterClass.GetPropertyRequest.newBuilder()
                .setPropertyId(propertyId)
                .build();

        when(propertyServiceStub.getPropertyById(request)).thenThrow(new StatusRuntimeException(io.grpc.Status.NOT_FOUND));

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            propertyClientService.getProperty(propertyId);
        });
        assertEquals("Failed to get Property with id=1", exception.getMessage());
    }
}
