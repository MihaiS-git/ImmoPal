package ubb.graduation24.immopal.auction_service.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import ubb.graduation24.immopal.grpc.AgencyServiceRPCGrpc;
import ubb.graduation24.immopal.grpc.PersonServiceRPCGrpc;
import ubb.graduation24.immopal.grpc.PropertyServiceRPCGrpc;

@Slf4j
@Configuration
public class GrpcClientConfig {

    private final String PERSON_SERVICE_HOST = "person-service";
    private final int PERSON_SERVICE_PORT = 9051;
    private final String PROPERTY_SERVICE_HOST = "property-service";
    private final int PROPERTY_SERVICE_PORT = 9052;
    private final String AGENCY_SERVICE_HOST = "agency-service";
    private final int AGENCY_SERVICE_PORT = 9053;

    @Bean
    public ManagedChannel personManagedChannel() {
        log.info("Creating ManagedChannel for {}:{}", PERSON_SERVICE_HOST, PERSON_SERVICE_PORT);
        return ManagedChannelBuilder.forAddress(PERSON_SERVICE_HOST, PERSON_SERVICE_PORT)
                .usePlaintext()
                .build();
    }

    @Bean
    public PersonServiceRPCGrpc.PersonServiceRPCBlockingStub personServiceStub(ManagedChannel personManagedChannel) {
        log.info("Creating PersonServiceRPCBlockingStub");
        return PersonServiceRPCGrpc.newBlockingStub(personManagedChannel);
    }

    @Retryable(
            value = { StatusRuntimeException.class },
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000)
    )
    public PersonServiceRPCGrpc.PersonServiceRPCBlockingStub getPersonServiceStub() {
        return personServiceStub(personManagedChannel());
    }

    @Bean
    public ManagedChannel propertyManagedChannel() {
        log.info("Creating ManagedChannel for {}:{}", PROPERTY_SERVICE_HOST, PROPERTY_SERVICE_PORT);
        return ManagedChannelBuilder.forAddress(PROPERTY_SERVICE_HOST, PROPERTY_SERVICE_PORT)
                .usePlaintext()
                .build();
    }

    @Bean
    public PropertyServiceRPCGrpc.PropertyServiceRPCBlockingStub propertyServiceStub(ManagedChannel propertyManagedChannel) {
        log.info("Creating PropertyServiceRPCBlockingStub");
        return PropertyServiceRPCGrpc.newBlockingStub(propertyManagedChannel);
    }

    @Retryable(
            value = { StatusRuntimeException.class },
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000)
    )
    public PropertyServiceRPCGrpc.PropertyServiceRPCBlockingStub getPropertyServiceStub() {
        return propertyServiceStub(propertyManagedChannel());
    }

    @Bean
    public ManagedChannel agencyManagedChannel() {
        log.info("Creating ManagedChannel for {}:{}", AGENCY_SERVICE_HOST, AGENCY_SERVICE_PORT);
        return ManagedChannelBuilder.forAddress(AGENCY_SERVICE_HOST, AGENCY_SERVICE_PORT)
                .usePlaintext()
                .build();
    }

    @Bean
    public AgencyServiceRPCGrpc.AgencyServiceRPCBlockingStub agencyServiceStub(ManagedChannel agencyManagedChannel) {
        log.info("Creating PropertyServiceRPCBlockingStub");
        return AgencyServiceRPCGrpc.newBlockingStub(agencyManagedChannel);
    }

    @Retryable(
            value = { StatusRuntimeException.class },
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000)
    )
    public AgencyServiceRPCGrpc.AgencyServiceRPCBlockingStub getAgencyServiceStub() {
        return agencyServiceStub(agencyManagedChannel());
    }

}
