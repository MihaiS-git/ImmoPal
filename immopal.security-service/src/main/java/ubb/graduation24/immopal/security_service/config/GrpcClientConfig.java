package ubb.graduation24.immopal.security_service.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import ubb.graduation24.immopal.grpc.PersonServiceRPCGrpc;

@Configuration
public class GrpcClientConfig {
    private static final Logger log = LoggerFactory.getLogger(GrpcClientConfig.class);

    @Value("${person.service.host}")
    private String personServiceHost;
    @Value("${person.service.port}")
    private int personServicePort;

    @Bean
    ManagedChannel managedChannel() {
        log.info("Creating ManagedChannel for {}:{}", personServiceHost, personServicePort);
        String grpcServerAddress = personServiceHost + ":" + personServicePort;
        log.info("grpcServerAddress: {}", grpcServerAddress);
        return ManagedChannelBuilder
                .forTarget(grpcServerAddress)
                .usePlaintext()
                .build();
    }

    @Bean
    public PersonServiceRPCGrpc.PersonServiceRPCBlockingStub personServiceStub(ManagedChannel channel) {
        log.info("Creating PersonServiceRPCBlockingStub");
        return PersonServiceRPCGrpc.newBlockingStub(channel);
    }

    @Retryable(
            value = {StatusRuntimeException.class},
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000)
    )
    public PersonServiceRPCGrpc.PersonServiceRPCBlockingStub getPersonServiceStub() {
        return personServiceStub(managedChannel());
    }
}
