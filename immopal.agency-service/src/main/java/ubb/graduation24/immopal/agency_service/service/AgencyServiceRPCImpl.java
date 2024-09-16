package ubb.graduation24.immopal.agency_service.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import ubb.graduation24.immopal.agency_service.domain.Address;
import ubb.graduation24.immopal.agency_service.domain.AgencyAgent;
import ubb.graduation24.immopal.agency_service.domain.AgencyProperty;
import ubb.graduation24.immopal.agency_service.repository.AgencyAgentRepository;
import ubb.graduation24.immopal.agency_service.repository.AgencyPropertyRepository;
import ubb.graduation24.immopal.grpc.AgencyOuterClass;
import ubb.graduation24.immopal.grpc.AgencyServiceRPCGrpc;
import ubb.graduation24.immopal.agency_service.domain.Agency;
import ubb.graduation24.immopal.agency_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.agency_service.repository.AgencyRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@GRpcService
public class AgencyServiceRPCImpl extends AgencyServiceRPCGrpc.AgencyServiceRPCImplBase {

    private final AgencyRepository agencyRepository;
    private final AgencyAgentRepository agencyAgentRepository;
    private final AgencyPropertyRepository agencyPropertyRepository;

    @Override
    public void getAllAgencies(
            AgencyOuterClass.GetAgenciesRequest request,
            StreamObserver<AgencyOuterClass.GetAgenciesResponse> responseObserver
    ) {
        List<Agency> agencies = agencyRepository.findAll();
        if (agencies.isEmpty()) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("No agencies found")
                    .asRuntimeException());
            return;
        }

        List<AgencyOuterClass.Agency> grpcAgencies = agencies.stream()
                .map(this::convertToGrpcAgency)
                .toList();

        AgencyOuterClass.GetAgenciesResponse response = AgencyOuterClass.GetAgenciesResponse.newBuilder()
                .addAllAgency(grpcAgencies)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }



    private AgencyOuterClass.Agency convertToGrpcAgency(Agency agency) {
        if (agency == null) {
            throw new IllegalArgumentException("Agency must not be null");
        }
        return AgencyOuterClass.Agency.newBuilder()
                .setId(agency.getId())
                .setName(agency.getName())
                .setAddress(convertToGrpcAddress(agency.getAddress()))
                .setPhone(agency.getPhone())
                .setEmail(agency.getEmail())
                .setDescription(agency.getDescription())
                .setLogoUrl(agency.getLogoUrl())
                .build();
    }

    private AgencyOuterClass.Address convertToGrpcAddress(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Address must not be null");
        }
        return AgencyOuterClass.Address.newBuilder()
                .setId(address.getId())
                .setCountry(address.getCountry())
                .setState(address.getState())
                .setCity(address.getCity())
                .setNeighborhood(address.getNeighborhood())
                .setStreet(address.getStreet())
                .setStreetNo(address.getStreetNo())
                .setLatitude(address.getLatitude())
                .setLongitude(address.getLongitude())
                .build();
    }

    @Override
    public void getAgencyById(
            AgencyOuterClass.GetAgencyRequest request,
            StreamObserver<AgencyOuterClass.GetAgencyResponse> responseObserver
    ) {
        Long agencyId = request.getAgencyId();
        Optional<Agency> optionalAgency = agencyRepository.findById(agencyId);
        if (optionalAgency.isEmpty()) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("No agency found with id: " + agencyId)
                    .asRuntimeException());
            return;
        }

        Agency agency = optionalAgency.get();
        AgencyOuterClass.Agency grpcAgency = convertToGrpcAgency(agency);

        AgencyOuterClass.GetAgencyResponse response = AgencyOuterClass.GetAgencyResponse.newBuilder()
                .setAgency(grpcAgency)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    @Override
    public void getAgencyByAgentId(
            AgencyOuterClass.GetAgencyByAgentIdRequest request,
            StreamObserver<AgencyOuterClass.GetAgencyByAgentIdResponse> responseObserver
    ) {
        Long agentId = request.getAgentId();
        try {
            log.debug("Fetching agency agent by agentId: {}", agentId);
            Optional<AgencyAgent> optionalAgencyAgent = agencyAgentRepository.findByAgentId(agentId);
            if (optionalAgencyAgent.isEmpty()) {
                log.error("No agency agent found for agentId: {}", agentId);
                responseObserver.onError(Status.NOT_FOUND
                        .withDescription("No agency found for agentId: " + agentId)
                        .asRuntimeException());
                return;
            }
            AgencyAgent agencyAgent = optionalAgencyAgent.get();
            Agency agency = agencyAgent.getAgency();
            log.debug("AgencyAgent found: {}", agencyAgent);

            log.debug("Fetching agency by agencyId: {}", agency.getId());
            Optional<Agency> optionalAgency = agencyRepository.findById(agency.getId());
            if (optionalAgency.isEmpty()) {
                log.error("No agency found with id: {}", agency.getId());
                responseObserver.onError(Status.NOT_FOUND
                        .withDescription("No agency found with id: " + agency.getId())
                        .asRuntimeException());
                return;
            }

            Agency agencyResult = optionalAgency.get();
            log.debug("Agency found: {}", agencyResult);
            AgencyOuterClass.Agency grpcAgency = convertToGrpcAgency(agencyResult);

            log.debug("Converted gRPC Agency: {}", grpcAgency);
            AgencyOuterClass.GetAgencyByAgentIdResponse response = AgencyOuterClass.GetAgencyByAgentIdResponse.newBuilder()
                    .setAgency(grpcAgency)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error fetching agency by agentId: {}", agentId, e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Internal server error")
                    .withCause(e)
                    .asRuntimeException());
        }
    }

    @Override
    public void getAgencyByPropertyId(
            AgencyOuterClass.GetAgencyByPropertyIdRequest request,
            StreamObserver<AgencyOuterClass.GetAgencyByPropertyIdResponse> responseObserver
    ) {
        Long propertyId = request.getPropertyId();
        Optional<AgencyProperty> optionalAgencyProperty = agencyPropertyRepository.findByPropertyId(propertyId);
        if (optionalAgencyProperty.isEmpty()) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("No agency found by propertyId: " + propertyId)
                    .asRuntimeException());
            return;
        }

        AgencyProperty agencyProperty = optionalAgencyProperty.get();
        Agency agency = agencyProperty.getAgency();
        Optional<Agency> optionalAgency = agencyRepository.findById(agency.getId());
        if (optionalAgency.isEmpty()) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("No agency found with id: " + agency.getId())
                    .asRuntimeException());
            return;
        }

        Agency agencyResult = optionalAgency.get();
        AgencyOuterClass.Agency grpcAgency = convertToGrpcAgency(agencyResult);

        AgencyOuterClass.GetAgencyByPropertyIdResponse response = AgencyOuterClass.GetAgencyByPropertyIdResponse.newBuilder()
                .setAgency(grpcAgency)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
