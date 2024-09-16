package ubb.graduation24.immopal.agency_service.service;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ubb.graduation24.immopal.agency_service.domain.Address;
import ubb.graduation24.immopal.agency_service.domain.Agency;
import ubb.graduation24.immopal.agency_service.domain.AgencyAgent;
import ubb.graduation24.immopal.agency_service.domain.AgencyProperty;
import ubb.graduation24.immopal.agency_service.repository.AgencyAgentRepository;
import ubb.graduation24.immopal.agency_service.repository.AgencyPropertyRepository;
import ubb.graduation24.immopal.agency_service.repository.AgencyRepository;
import ubb.graduation24.immopal.grpc.AgencyOuterClass;
import java.util.Collections;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgencyServiceRPCImplTest {

    @Mock
    private AgencyRepository agencyRepository;

    @Mock
    private AgencyAgentRepository agencyAgentRepository;

    @Mock
    private AgencyPropertyRepository agencyPropertyRepository;

    @InjectMocks
    private AgencyServiceRPCImpl agencyServiceRPC;

    @Mock
    private StreamObserver<AgencyOuterClass.GetAgenciesResponse> getAgenciesResponseObserver;

    @Mock
    private StreamObserver<AgencyOuterClass.GetAgencyResponse> getAgencyResponseObserver;

    @Mock
    private StreamObserver<AgencyOuterClass.GetAgencyByAgentIdResponse> getAgencyByAgentIdResponseObserver;

    @Mock
    private StreamObserver<AgencyOuterClass.GetAgencyByPropertyIdResponse> getAgencyByPropertyIdResponseObserver;

    @Test
    void testGetAllAgencies_Success() {
        // Create address objects
        Address address1 = Address.builder()
                .id(1L)
                .country("US")
                .state("NY")
                .city("NY")
                .neighborhood("N Test 1")
                .street("Test Street 1")
                .streetNo(1L)
                .latitude(1.1)
                .longitude(2.1)
                .build();

        Address address2 = Address.builder()
                .id(2L)
                .country("US")
                .state("NY")
                .city("NY")
                .neighborhood("N Test 2")
                .street("Test Street 2")
                .streetNo(2L)
                .latitude(1.2)
                .longitude(2.2)
                .build();

        // Create agency objects
        Agency agency1 = Agency.builder()
                .id(1L)
                .name("Agency 1")
                .address(address1)
                .phone("1234567890")
                .email("agency1@test.com")
                .description("Description 1")
                .logoUrl("logo1.png")
                .build();

        Agency agency2 = Agency.builder()
                .id(2L)
                .name("Agency 2")
                .address(address2)
                .phone("0987654321")
                .email("agency2@test.com")
                .description("Description 2")
                .logoUrl("logo2.png")
                .build();

        // Mock repository response
        when(agencyRepository.findAll()).thenReturn(Arrays.asList(agency1, agency2));

        // Call the method
        agencyServiceRPC.getAllAgencies(
                AgencyOuterClass.GetAgenciesRequest.getDefaultInstance(),
                getAgenciesResponseObserver
        );

        // Create gRPC agency objects
        AgencyOuterClass.Agency grpcAgency1 = AgencyOuterClass.Agency.newBuilder()
                .setId(1L)
                .setName("Agency 1")
                .setAddress(AgencyOuterClass.Address.newBuilder()
                        .setId(1L)
                        .setCountry("US")
                        .setState("NY")
                        .setCity("NY")
                        .setNeighborhood("N Test 1")
                        .setStreet("Test Street 1")
                        .setStreetNo(1L)
                        .setLatitude(1.1)
                        .setLongitude(2.1)
                        .build())
                .setPhone("1234567890")
                .setEmail("agency1@test.com")
                .setDescription("Description 1")
                .setLogoUrl("logo1.png")
                .build();

        AgencyOuterClass.Agency grpcAgency2 = AgencyOuterClass.Agency.newBuilder()
                .setId(2L)
                .setName("Agency 2")
                .setAddress(AgencyOuterClass.Address.newBuilder()
                        .setId(2L)
                        .setCountry("US")
                        .setState("NY")
                        .setCity("NY")
                        .setNeighborhood("N Test 2")
                        .setStreet("Test Street 2")
                        .setStreetNo(2L)
                        .setLatitude(1.2)
                        .setLongitude(2.2)
                        .build())
                .setPhone("0987654321")
                .setEmail("agency2@test.com")
                .setDescription("Description 2")
                .setLogoUrl("logo2.png")
                .build();

        // Create expected gRPC response
        AgencyOuterClass.GetAgenciesResponse expectedResponse = AgencyOuterClass.GetAgenciesResponse.newBuilder()
                .addAllAgency(Arrays.asList(grpcAgency1, grpcAgency2))
                .build();

        // Verify interactions
        verify(getAgenciesResponseObserver).onNext(expectedResponse);
        verify(getAgenciesResponseObserver).onCompleted();
    }

    @Test
    void testGetAllAgencies_NoAgencies() {
        when(agencyRepository.findAll()).thenReturn(Collections.emptyList());

        agencyServiceRPC.getAllAgencies(
                AgencyOuterClass.GetAgenciesRequest.getDefaultInstance(),
                getAgenciesResponseObserver
        );

        verify(getAgenciesResponseObserver).onError(argThat(exception ->
                exception instanceof io.grpc.StatusRuntimeException &&
                        ((io.grpc.StatusRuntimeException) exception).getStatus().getCode() == Status.Code.NOT_FOUND &&
                        ((io.grpc.StatusRuntimeException) exception).getStatus().getDescription().equals("No agencies found")
        ));
    }

    @Test
    void testGetAgencyById_Success() {
        // Create address object
        Address address = Address.builder()
                .id(1L)
                .country("US")
                .state("NY")
                .city("NY")
                .neighborhood("N Test")
                .street("Test Street")
                .streetNo(2L)
                .latitude(1.0)
                .longitude(2.0)
                .build();

        // Create agency object
        Agency agency = Agency.builder()
                .id(1L)
                .name("Agency 1")
                .address(address)
                .phone("1234567890")
                .email("test@test.com")
                .description("Test description")
                .logoUrl("logo.png")
                .build();

        // Mock repository response
        when(agencyRepository.findById(1L)).thenReturn(Optional.of(agency));

        // Call the method
        agencyServiceRPC.getAgencyById(
                AgencyOuterClass.GetAgencyRequest.newBuilder().setAgencyId(1L).build(),
                getAgencyResponseObserver
        );

        // Create gRPC agency object
        AgencyOuterClass.Agency grpcAgency = AgencyOuterClass.Agency.newBuilder()
                .setId(1L)
                .setName("Agency 1")
                .setAddress(AgencyOuterClass.Address.newBuilder()
                        .setId(1L)
                        .setCountry("US")
                        .setState("NY")
                        .setCity("NY")
                        .setNeighborhood("N Test")
                        .setStreet("Test Street")
                        .setStreetNo(2L)
                        .setLatitude(1.0)
                        .setLongitude(2.0)
                        .build())
                .setPhone("1234567890")
                .setEmail("test@test.com")
                .setDescription("Test description")
                .setLogoUrl("logo.png")
                .build();

        // Create expected gRPC response
        AgencyOuterClass.GetAgencyResponse expectedResponse = AgencyOuterClass.GetAgencyResponse.newBuilder()
                .setAgency(grpcAgency)
                .build();

        // Verify interactions
        verify(getAgencyResponseObserver).onNext(expectedResponse);
        verify(getAgencyResponseObserver).onCompleted();
    }

    @Test
    void testGetAgencyById_AgencyNotFound() {
        // Arrange
        when(agencyRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        agencyServiceRPC.getAgencyById(
                AgencyOuterClass.GetAgencyRequest.newBuilder().setAgencyId(1L).build(),
                getAgencyResponseObserver
        );

        // Assert
        verify(getAgencyResponseObserver).onError(argThat(exception ->
                exception instanceof StatusRuntimeException &&
                        ((StatusRuntimeException) exception).getStatus().getCode() == Status.Code.NOT_FOUND &&
                        ((StatusRuntimeException) exception).getStatus().getDescription().equals("No agency found with id: 1")
        ));
    }

    @Test
    void testGetAgencyByAgentId_Success() {
        // Arrange
        Address address = Address.builder()
                .id(1L)
                .country("US")
                .state("NY")
                .city("NY")
                .neighborhood("N Test")
                .street("Test Street")
                .streetNo(2L)
                .latitude(1.0)
                .longitude(2.0)
                .build();

        AgencyAgent agencyAgent = AgencyAgent.builder()
                .id(1L)
                .agentId(1L)
                .build();

        AgencyProperty agencyProperty = AgencyProperty.builder()
                .id(1L)
                .propertyId(1L)
                .build();

        Agency agency = Agency.builder()
                .id(1L)
                .name("Agency 1")
                .address(address)
                .phone("1234567890")
                .email("test@test.com")
                .description("Test")
                .logoUrl("sdaad")
                .agents(new HashSet<>(Arrays.asList(agencyAgent)))
                .properties(new HashSet<>(Arrays.asList(agencyProperty)))
                .build();

        agencyAgent.setAgency(agency);
        agencyProperty.setAgency(agency);

        when(agencyAgentRepository.findByAgentId(agencyAgent.getAgentId())).thenReturn(Optional.of(agencyAgent));
        when(agencyRepository.findById(agency.getId())).thenReturn(Optional.of(agency));

        // Act
        AgencyOuterClass.GetAgencyByAgentIdRequest request = AgencyOuterClass.GetAgencyByAgentIdRequest.newBuilder()
                .setAgentId(agencyAgent.getAgentId())
                .build();

        AgencyOuterClass.GetAgencyByAgentIdResponse expectedResponse = AgencyOuterClass.GetAgencyByAgentIdResponse.newBuilder()
                .setAgency(AgencyOuterClass.Agency.newBuilder()
                        .setId(1)
                        .setName("Agency 1")
                        .setAddress(AgencyOuterClass.Address.newBuilder()
                                .setId(1)
                                .setCountry("US")
                                .setState("NY")
                                .setCity("NY")
                                .setNeighborhood("N Test")
                                .setStreet("Test Street")
                                .setStreetNo(2)
                                .setLatitude(1.0)
                                .setLongitude(2.0)
                                .build())
                        .setPhone("1234567890")
                        .setEmail("test@test.com")
                        .setDescription("Test")
                        .setLogoUrl("sdaad")
                        .build())
                .build();

        // Call the method
        agencyServiceRPC.getAgencyByAgentId(request, getAgencyByAgentIdResponseObserver);

        // Verify interactions
        verify(getAgencyByAgentIdResponseObserver).onNext(expectedResponse);
        verify(getAgencyByAgentIdResponseObserver).onCompleted();
    }

    @Test
    void testGetAgencyByAgentId_AgentNotFound() {
        Long agentId = 1L;

        // Mock repository response
        when(agencyAgentRepository.findByAgentId(agentId)).thenReturn(Optional.empty());

        // Call the method under test
        agencyServiceRPC.getAgencyByAgentId(
                AgencyOuterClass.GetAgencyByAgentIdRequest.newBuilder().setAgentId(agentId).build(),
                getAgencyByAgentIdResponseObserver
        );

        // Verify interactions
        verify(getAgencyByAgentIdResponseObserver).onError(argThat(throwable ->
                throwable instanceof StatusRuntimeException &&
                        ((StatusRuntimeException) throwable).getStatus().getCode() == Status.Code.NOT_FOUND
        ));
        verifyNoMoreInteractions(getAgencyByAgentIdResponseObserver);
    }

    @Test
    void testGetAgencyByAgentId_AgencyNotFound() {
        Long agentId = 1L;
        AgencyAgent agencyAgent = new AgencyAgent();
        agencyAgent.setAgency(new Agency());
        Agency agency = agencyAgent.getAgency();
        agency.setId(agentId);

        // Mock repository responses
        when(agencyAgentRepository.findByAgentId(agentId)).thenReturn(Optional.of(agencyAgent));
        when(agencyRepository.findById(agentId)).thenReturn(Optional.empty());

        // Call the method under test
        agencyServiceRPC.getAgencyByAgentId(
                AgencyOuterClass.GetAgencyByAgentIdRequest.newBuilder().setAgentId(agentId).build(),
                getAgencyByAgentIdResponseObserver
        );

        // Verify interactions
        verify(getAgencyByAgentIdResponseObserver).onError(argThat(throwable ->
                throwable instanceof StatusRuntimeException &&
                        ((StatusRuntimeException) throwable).getStatus().getCode() == Status.Code.NOT_FOUND
        ));
        verifyNoMoreInteractions(getAgencyByAgentIdResponseObserver);
    }

    @Test
    void testGetAgencyByAgentId_InternalError() {
        Long agentId = 1L;

        // Mock repository to throw exception
        when(agencyAgentRepository.findByAgentId(agentId)).thenThrow(new RuntimeException("Database error"));

        // Call the method under test
        agencyServiceRPC.getAgencyByAgentId(
                AgencyOuterClass.GetAgencyByAgentIdRequest.newBuilder().setAgentId(agentId).build(),
                getAgencyByAgentIdResponseObserver
        );

        // Verify interactions
        verify(getAgencyByAgentIdResponseObserver).onError(argThat(throwable ->
                throwable instanceof StatusRuntimeException &&
                        ((StatusRuntimeException) throwable).getStatus().getCode() == Status.Code.INTERNAL
        ));
        verifyNoMoreInteractions(getAgencyByAgentIdResponseObserver);
    }

    @Test
    void testGetAgencyByPropertyId_Success() {
        // Arrange
        Address address = Address.builder()
                .id(1L)
                .country("US")
                .state("NY")
                .city("NY")
                .neighborhood("N Test")
                .street("Test Street")
                .streetNo(2L)
                .latitude(1.0)
                .longitude(2.0)
                .build();

        AgencyAgent agencyAgent = AgencyAgent.builder()
                .id(1L)
                .agentId(1L)
                .build();

        AgencyProperty agencyProperty = AgencyProperty.builder()
                .id(1L)
                .propertyId(1L)
                .build();

        Agency agency = Agency.builder()
                .id(2L)
                .name("Agency 2")
                .address(address)
                .phone("1234567890")
                .email("test@test.com")
                .description("Test Description")
                .logoUrl("http://logo.url")
                .agents(new HashSet<>(Arrays.asList(agencyAgent)))
                .properties(new HashSet<>(Arrays.asList(agencyProperty)))
                .build();

        agencyAgent.setAgency(agency);
        agencyProperty.setAgency(agency);

        when(agencyPropertyRepository.findByPropertyId(1L)).thenReturn(Optional.of(agencyProperty));
        when(agencyRepository.findById(2L)).thenReturn(Optional.of(agency));

        // Act
        agencyServiceRPC.getAgencyByPropertyId(
                AgencyOuterClass.GetAgencyByPropertyIdRequest.newBuilder().setPropertyId(1L).build(),
                getAgencyByPropertyIdResponseObserver
        );

        // Expected gRPC Agency object
        AgencyOuterClass.Agency grpcAgency = AgencyOuterClass.Agency.newBuilder()
                .setId(2L)
                .setName("Agency 2")
                .setAddress(AgencyOuterClass.Address.newBuilder()
                        .setId(1L)
                        .setCountry("US")
                        .setState("NY")
                        .setCity("NY")
                        .setNeighborhood("N Test")
                        .setStreet("Test Street")
                        .setStreetNo(2L)
                        .setLatitude(1.0)
                        .setLongitude(2.0)
                        .build())
                .setPhone("1234567890")
                .setEmail("test@test.com")
                .setDescription("Test Description")
                .setLogoUrl("http://logo.url")
                .build();

        // Expected response
        AgencyOuterClass.GetAgencyByPropertyIdResponse expectedResponse = AgencyOuterClass.GetAgencyByPropertyIdResponse.newBuilder()
                .setAgency(grpcAgency)
                .build();

        // Assert
        verify(getAgencyByPropertyIdResponseObserver).onNext(expectedResponse);
        verify(getAgencyByPropertyIdResponseObserver).onCompleted();
    }

    @Test
    void testGetAgencyByPropertyId_PropertyNotFound() {
        // Arrange: Mock the repository to return an empty optional
        when(agencyPropertyRepository.findByPropertyId(1L)).thenReturn(Optional.empty());

        // Call the method under test
        agencyServiceRPC.getAgencyByPropertyId(
                AgencyOuterClass.GetAgencyByPropertyIdRequest.newBuilder().setPropertyId(1L).build(),
                getAgencyByPropertyIdResponseObserver
        );

        // Assert: Verify that the responseObserver.onError is called with the correct exception
        verify(getAgencyByPropertyIdResponseObserver).onError(argThat(throwable ->
                throwable instanceof StatusRuntimeException &&
                        ((StatusRuntimeException) throwable).getStatus().getCode() == Status.Code.NOT_FOUND &&
                        ((StatusRuntimeException) throwable).getStatus().getDescription().equals("No agency found by propertyId: 1")
        ));
    }
}