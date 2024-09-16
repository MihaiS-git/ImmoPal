package ubb.graduation24.immopal.person_service.config;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ubb.graduation24.immopal.person_service.service.PersonServiceRPCImpl;

import java.io.IOException;

@Configuration
public class GrpcServerConfiguration {

    private static final int PERSON_GRPCSERVER_PORT = 9051;

    @Bean
    public Server grpcPersonServer(PersonServiceRPCImpl personServiceRPC){
        Server server = ServerBuilder.forPort(PERSON_GRPCSERVER_PORT)
                .addService(personServiceRPC)
                .build();

        try {
            server.start();
            System.out.println("gRPC server started, listening on port " + PERSON_GRPCSERVER_PORT);
        } catch (IOException e) {
            System.err.println("Failed to start gRPC server: " + e.getMessage());
        }

        return server;
    }
}
