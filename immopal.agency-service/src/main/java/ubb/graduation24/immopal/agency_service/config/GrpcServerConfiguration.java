package ubb.graduation24.immopal.agency_service.config;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ubb.graduation24.immopal.agency_service.service.AgencyServiceRPCImpl;

import java.io.IOException;

@Configuration
public class GrpcServerConfiguration {

    private static final int AGENCY_SERVER_PORT = 9053;

    @Bean
    public Server grpcAgencyServer(AgencyServiceRPCImpl agencyServiceRPC){
        Server server = ServerBuilder.forPort(AGENCY_SERVER_PORT)
                .addService(agencyServiceRPC)
                .build();

        try{
            server.start();
            System.out.println("gRPC server started, listening on port " + AGENCY_SERVER_PORT);
        } catch (IOException e) {
            System.err.println("Failed to start gRPC server: " + e.getMessage());
        }

        return server;
    }
}
