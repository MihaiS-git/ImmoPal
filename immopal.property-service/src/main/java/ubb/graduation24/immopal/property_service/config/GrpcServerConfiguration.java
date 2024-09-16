package ubb.graduation24.immopal.property_service.config;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ubb.graduation24.immopal.property_service.service.PropertyServiceRPCImpl;

import java.io.IOException;

@Configuration
public class GrpcServerConfiguration {

    private static final int PROPERTY_SERVER_PORT = 9052;

    @Bean
    public Server grpcPropertyServer(PropertyServiceRPCImpl propertyServiceRPC){
        Server server = ServerBuilder.forPort(PROPERTY_SERVER_PORT)
                .addService(propertyServiceRPC)
                .build();

        try {
            server.start();
            System.out.println("gRPC server started, listening on port " + PROPERTY_SERVER_PORT);
        } catch (IOException e) {
            System.err.println("Failed to start gRPC server: " + e.getMessage());
        }

        return server;
    }

}
