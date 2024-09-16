package ubb.graduation24.immopal.agency_service.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ubb.graduation24.immopal.grpc.PersonServiceRPCGrpc;
import ubb.graduation24.immopal.grpc.PropertyServiceRPCGrpc;

@Slf4j
@Configuration
public class GrpcClientConfiguration {

    private final String PERSON_SERVICE_HOST = "person-service";
    private final int PERSON_SERVICE_PORT = 9051;
    private final String PROPERTY_SERVICE_HOST = "property-service";
    private final int PROPERTY_SERVICE_PORT = 9052;

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
}
