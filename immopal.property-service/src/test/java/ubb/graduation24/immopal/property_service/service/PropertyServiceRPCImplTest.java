package ubb.graduation24.immopal.property_service.service;

import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ubb.graduation24.immopal.grpc.PropertyOuterClass;
import ubb.graduation24.immopal.property_service.domain.*;
import ubb.graduation24.immopal.property_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.property_service.repository.PropertyRepository;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropertyServiceRPCImplTest {

    @InjectMocks
    private PropertyServiceRPCImpl propertyServiceRPC;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private StreamObserver<PropertyOuterClass.GetPropertiesResponse> getPropertiesResponseObserver;

    @Mock
    private StreamObserver<PropertyOuterClass.GetPropertiesByAgentIdResponse> getPropertiesByAgentIdResponseObserver;

    @Mock
    private StreamObserver<PropertyOuterClass.GetPropertyResponse> getPropertyResponseObserver;

    private Property property;
    private PropertyOuterClass.Property grpcProperty;

    @BeforeEach
    void setUp() {
        Address address = Address.builder()
                .id(1L)
                .country("Country")
                .state("State")
                .city("City")
                .neighborhood("Neighborhood")
                .street("Street")
                .streetNo("123")
                .latitude(12.34)
                .longitude(56.78)
                .build();

        PropertyDetails propertyDetails = PropertyDetails.builder()
                .id(1L)
                .description("Description")
                .carpetArea(100.0)
                .builtUpArea(120.0)
                .comfortType(ComfortType.LUXURIOUS)
                .floor("1")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2020)
                .bathNo(2)
                .kitchenNo(1)
                .bedroomNo(3)
                .parkingNo(1)
                .balcony(true)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.A)
                .build();

        PropertyImages propertyImage = PropertyImages.builder()
                .id(1L)
                .imageUrl("http://image.url")
                .build();

        property = Property.builder()
                .id(1L)
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(propertyDetails)
                .address(address)
                .price(1000.0)
                .agentId(1L)
                .propertyImages(Collections.singletonList(propertyImage))
                .build();

        grpcProperty = PropertyOuterClass.Property.newBuilder()
                .setId(1L)
                .setTransactionType(PropertyOuterClass.TransactionType.RENT)
                .setPropertyCategory(PropertyOuterClass.PropertyCategory.APARTMENT)
                .setAddress(PropertyOuterClass.Address.newBuilder()
                        .setId(1L)
                        .setCountry("Country")
                        .setState("State")
                        .setCity("City")
                        .setNeighborhood("Neighborhood")
                        .setStreet("Street")
                        .setStreetNo("123")
                        .setLatitude(12.34)
                        .setLongitude(56.78)
                        .build())
                .setPropertyDetails(PropertyOuterClass.PropertyDetails.newBuilder()
                        .setId(1L)
                        .setDescription("Description")
                        .setCarpetArea(100.0)
                        .setBuiltUpArea(120.0)
                        .setComfortType(PropertyOuterClass.ComfortType.LUXURIOUS)
                        .setFloor("1")
                        .setStructureType(PropertyOuterClass.StructureType.REINFORCED_CONCRETE)
                        .setYearOfConstruction(2020)
                        .setBathNo(2)
                        .setKitchenNo(1)
                        .setBedroomNo(3)
                        .setParkingNo(1)
                        .setBalcony(true)
                        .setTerrace(false)
                        .setSwimmingPool(false)
                        .setEnergeticCertificate(PropertyOuterClass.EnergeticCertificate.A)
                        .build())
                .setPrice(1000.0)
                .setAgentId(1L)
                .addPropertyImages(PropertyOuterClass.PropertyImage.newBuilder()
                        .setId(1L)
                        .setImageUrl("http://image.url")
                        .build())
                .build();
    }

    @Test
    void testGetAllPropertiesSuccess() {
        when(propertyRepository.findAll()).thenReturn(Collections.singletonList(property));

        propertyServiceRPC.getAllProperties(PropertyOuterClass.GetPropertiesRequest.newBuilder().build(), getPropertiesResponseObserver);

        verify(getPropertiesResponseObserver).onNext(argThat(response ->
                response.getPropertyCount() == 1 &&
                        response.getProperty(0).equals(grpcProperty)
        ));
        verify(getPropertiesResponseObserver).onCompleted();
    }

    @Test
    void testGetAllPropertiesFailure() {
        when(propertyRepository.findAll()).thenReturn(Collections.emptyList());

        propertyServiceRPC.getAllProperties(PropertyOuterClass.GetPropertiesRequest.newBuilder().build(), getPropertiesResponseObserver);

        verify(getPropertiesResponseObserver).onError(any(ResourceNotFoundException.class));
    }

    @Test
    void testGetAllPropertiesByAgentIdSuccess() {
        when(propertyRepository.findAll()).thenReturn(Collections.singletonList(property));

        propertyServiceRPC.getAllPropertiesByAgentId(PropertyOuterClass.GetPropertiesByAgentIdRequest.newBuilder().setAgentId(1L).build(), getPropertiesByAgentIdResponseObserver);

        verify(getPropertiesByAgentIdResponseObserver).onNext(argThat(response ->
                response.getPropertyCount() == 1 &&
                        response.getProperty(0).equals(grpcProperty)
        ));
        verify(getPropertiesByAgentIdResponseObserver).onCompleted();
    }

    @Test
    void testGetAllPropertiesByAgentIdFailure() {
        when(propertyRepository.findAll()).thenReturn(Collections.emptyList());

        propertyServiceRPC.getAllPropertiesByAgentId(PropertyOuterClass.GetPropertiesByAgentIdRequest.newBuilder().setAgentId(1L).build(), getPropertiesByAgentIdResponseObserver);

        verify(getPropertiesByAgentIdResponseObserver).onError(any(ResourceNotFoundException.class));
    }

    @Test
    void testGetPropertyByIdSuccess() {
        when(propertyRepository.findByIdWithImages(1L)).thenReturn(Optional.of(property));

        propertyServiceRPC.getPropertyById(PropertyOuterClass.GetPropertyRequest.newBuilder().setPropertyId(1L).build(), getPropertyResponseObserver);

        verify(getPropertyResponseObserver).onNext(argThat(response ->
                response.getProperty().equals(grpcProperty)
        ));
        verify(getPropertyResponseObserver).onCompleted();
    }

    @Test
    void testGetPropertyByIdNotFound() {
        when(propertyRepository.findByIdWithImages(1L)).thenReturn(Optional.empty());

        propertyServiceRPC.getPropertyById(PropertyOuterClass.GetPropertyRequest.newBuilder().setPropertyId(1L).build(), getPropertyResponseObserver);

        verify(getPropertyResponseObserver).onError(any(StatusRuntimeException.class));
    }

    @Test
    void testGetPropertyByIdException() {
        when(propertyRepository.findByIdWithImages(1L)).thenThrow(new RuntimeException("Database error"));

        propertyServiceRPC.getPropertyById(PropertyOuterClass.GetPropertyRequest.newBuilder().setPropertyId(1L).build(), getPropertyResponseObserver);

        verify(getPropertyResponseObserver).onError(argThat(exception ->
                ((StatusRuntimeException) exception).getStatus().getCode() == io.grpc.Status.Code.INTERNAL &&
                        ((StatusRuntimeException) exception).getStatus().getDescription().equals("Failed to process request")
        ));
    }
}

