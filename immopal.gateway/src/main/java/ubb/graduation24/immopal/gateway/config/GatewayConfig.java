package ubb.graduation24.immopal.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("security-service", r ->
                        r.path("/api/auth/**", "/api/users/**")
                                .uri("http://security-service:9000"))
                .route("person-service", r ->
                        r.path("/api/persons/**")
                                .uri("http://person-service:9001"))
                .route("property-service", r ->
                        r.path("/api/properties/**")
                                .uri("http://property-service:9002"))
                .route("agency-service", r ->
                        r.path("/api/agencies/**")
                                .uri("http://agency-service:9003"))
                .route("appointment-service", r ->
                        r.path("/api/appointments/**")
                                .uri("http://appointment-service:9004"))
                .route("chat-service", r ->
                        r.path("/api/chat/**")
                                .uri("http://chat-service:9005"))
                .route("auction-service", r ->
                        r.path("/api/auctionRooms/**")
                                .uri("http://auction-service:9006"))
                .route("chat-websocket", r ->
                        r.path("/api/chat/**")
                                .uri("http://chat-service:9005"))
                .route("auction-websocket", r ->
                        r.path("/api/auction/**")
                                .uri("http://auction-service:9006"))
                .build();
    }
}
