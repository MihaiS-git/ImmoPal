package ubb.graduation24.immopal.security_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) ->
                        auth
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/persons/**").permitAll()
                                .requestMatchers("/api/properties/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/api/agencies/**").permitAll()
                                .requestMatchers("/api/appointments/**").hasAnyRole("ADMIN", "AGENT", "CUSTOMER")
                                .requestMatchers("/api/auctionRooms/**").hasAnyRole("ADMIN", "AGENT", "CUSTOMER")
                                .requestMatchers("/api/chat/**").hasAnyRole("ADMIN", "AGENT", "CUSTOMER")
                                .requestMatchers("/api/users/{email}").hasAnyRole("ADMIN", "AGENT", "CUSTOMER")
                                .requestMatchers("/api/users").hasAnyRole("ADMIN", "AGENT", "CUSTOMER")
                                .requestMatchers("/api/auctionRooms").hasAnyRole("ADMIN", "AGENT")
                                .requestMatchers("/api/users/{id}").hasRole("ADMIN")
                                .anyRequest()
                                .authenticated())
                .httpBasic(httpBasic -> httpBasic
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
