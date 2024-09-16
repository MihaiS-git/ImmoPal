package ubb.graduation24.immopal.security_service.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.grpc.PersonOuterClass;
import ubb.graduation24.immopal.grpc.PersonServiceRPCGrpc;
import ubb.graduation24.immopal.security_service.domain.*;
import ubb.graduation24.immopal.security_service.exception.InvalidAuthException;
import ubb.graduation24.immopal.security_service.exception.InvalidRegistrationException;
import ubb.graduation24.immopal.security_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.security_service.model.AuthenticationRequest;
import ubb.graduation24.immopal.security_service.model.AuthenticationResponse;
import ubb.graduation24.immopal.security_service.model.RegisterRequest;
import ubb.graduation24.immopal.security_service.repository.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PersonServiceRPCGrpc.PersonServiceRPCBlockingStub personServiceStub;


    public AuthenticationResponse register(RegisterRequest request) {
        log.info("Register request: {}", request);

        String dateOfBirthString = request.getDateOfBirth() != null ? request.getDateOfBirth().toString() : null;

        PersonOuterClass.Person.Builder personBuilder = PersonOuterClass.Person.newBuilder();
        personBuilder.setFirstName(request.getFirstName());
        personBuilder.setLastName(request.getLastName());
        personBuilder.setPhoneNumber(request.getPhoneNumber());
        personBuilder.setDateOfBirth(dateOfBirthString);
        personBuilder.setAddress(request.getAddress());
        if(request.getPictureUrl() != null){
            personBuilder.setPictureUrl(request.getPictureUrl());
        }
        PersonOuterClass.Person savePersonProto = personBuilder.build();

        PersonOuterClass.AddPersonRequest grpcRequest = PersonOuterClass.AddPersonRequest.newBuilder()
                .setPerson(savePersonProto)
                .build();

        PersonOuterClass.AddPersonResponse grpcResponse = personServiceStub.addPerson(grpcRequest);

        if (grpcResponse.hasPerson()) {
            PersonOuterClass.Person fetchedPerson = grpcResponse.getPerson();
            // add personId on the saved User
            log.info("Person added: {}", fetchedPerson);
            Long personId = fetchedPerson.getId();
            log.info("Person saved with Id: {}", personId);

            // save User
            var user = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.CUSTOMER)
                    .personId(personId)
                    .build();
            User savedUser = repository.save(user);

            // add userId on the saved Person
            Long userId = savedUser.getUserId();
            log.info("User saved with Id: {}", userId);
            var updatePersonProto = fetchedPerson.toBuilder().setId(userId).build();

            PersonOuterClass.UpdatePersonRequest grpcUpdateRequest = PersonOuterClass.UpdatePersonRequest.newBuilder()
                    .setPerson(updatePersonProto)
                    .build();
            PersonOuterClass.UpdatePersonResponse grpcUpdateResponse = personServiceStub.updatePerson(grpcUpdateRequest);
            if(grpcUpdateResponse.hasPerson()) {
                log.info("Person updated: {}", grpcUpdateResponse.getPerson());
            } else {
                log.info("Person Id not updated");
                throw new ResourceNotFoundException("Person Id not updated");
            }

            // build and return the token
            var jwtToken = jwtService.generateToken(user);
            log.info("JWT token: {}", jwtToken);
            return AuthenticationResponse
                    .builder()
                    .token(jwtToken)
                    .build();
        } else {
            throw new InvalidRegistrationException("Could not save Person.");
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("Authenticate request: {}", request);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new InvalidAuthException();
        }
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        log.info("JWT token: {}", jwtToken);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}
