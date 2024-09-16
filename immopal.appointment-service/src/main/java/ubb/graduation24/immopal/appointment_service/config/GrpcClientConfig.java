package ubb.graduation24.immopal.appointment_service.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ubb.graduation24.immopal.grpc.AgencyServiceRPCGrpc;
import ubb.graduation24.immopal.grpc.PersonServiceRPCGrpc;
import ubb.graduation24.immopal.grpc.PropertyServiceRPCGrpc;

@Slf4j
@Configuration
public class GrpcClientConfig {

    private final String PERSON_SERVICE_HOST = "project-person-service-1";
    private final int PERSON_SERVICE_PORT = 9051;
    private final String PROPERTY_SERVICE_HOST = "project-property-service-1";
    private final int PROPERTY_SERVICE_PORT = 9052;
    private final String AGENCY_SERVICE_HOST = "project-agency-service-1";
    private final int AGENCY_SERVICE_PORT = 9053;

    @Bean
    public ManagedChannel personManagedChannel() {
        log.info("Creating ManagedChannel for Person Service at {}:{}", PERSON_SERVICE_HOST, PERSON_SERVICE_PORT);
        return ManagedChannelBuilder.forAddress(PERSON_SERVICE_HOST, PERSON_SERVICE_PORT)
                .usePlaintext()
                .build();
    }

    @Bean
    public PersonServiceRPCGrpc.PersonServiceRPCBlockingStub personServiceStub(ManagedChannel personManagedChannel) {
        log.info("Creating PersonServiceRPCBlockingStub");
        return PersonServiceRPCGrpc.newBlockingStub(personManagedChannel);
    }

    @Bean
    public ManagedChannel propertyManagedChannel() {
        log.info("Creating ManagedChannel for Property Service at {}:{}", PROPERTY_SERVICE_HOST, PROPERTY_SERVICE_PORT);
        return ManagedChannelBuilder.forAddress(PROPERTY_SERVICE_HOST, PROPERTY_SERVICE_PORT)
                .usePlaintext()
                .build();
    }

    @Bean
    public PropertyServiceRPCGrpc.PropertyServiceRPCBlockingStub propertyServiceStub(ManagedChannel propertyManagedChannel) {
        log.info("Creating PropertyServiceRPCBlockingStub");
        return PropertyServiceRPCGrpc.newBlockingStub(propertyManagedChannel);
    }

    @Bean
    public ManagedChannel agencyManagedChannel() {
        log.info("Creating ManagedChannel for Agency Service at {}:{}", AGENCY_SERVICE_HOST, AGENCY_SERVICE_PORT);
        return ManagedChannelBuilder.forAddress(AGENCY_SERVICE_HOST, AGENCY_SERVICE_PORT)
                .usePlaintext()
                .build();
    }

    @Bean
    public AgencyServiceRPCGrpc.AgencyServiceRPCBlockingStub agencyServiceStub(ManagedChannel agencyManagedChannel) {
        log.info("Creating AgencyServiceRPCBlockingStub");
        return AgencyServiceRPCGrpc.newBlockingStub(agencyManagedChannel);
    }
}