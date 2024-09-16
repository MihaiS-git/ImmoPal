package ubb.graduation24.immopal.appointment_service.service;

import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.appointment_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.grpc.PersonOuterClass;
import ubb.graduation24.immopal.grpc.PersonServiceRPCGrpc;

@Slf4j
@RequiredArgsConstructor
@Service
public class PersonClientService {

    private final PersonServiceRPCGrpc.PersonServiceRPCBlockingStub personServiceStub;

    @Retryable(
            value = { StatusRuntimeException.class },
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000)
    )
    public PersonOuterClass.Person getPerson(Long personId) {
        PersonOuterClass.GetPersonRequest request = PersonOuterClass.GetPersonRequest.newBuilder()
                .setPersonId(personId)
                .build();
        try {
            log.info("gRPC Requesting Person with Id={}", personId);
            PersonOuterClass.GetPersonResponse response = personServiceStub.getPerson(request);
            return response.getPerson();
        } catch (StatusRuntimeException e) {
            log.error("Failed to get Person with Id {} : {}", personId, e.getMessage());
            if (e.getStatus().getCode() == io.grpc.Status.Code.NOT_FOUND) {
                throw new ResourceNotFoundException("Failed to get Person with Id " + personId, e);
            }
            throw new RuntimeException("Unhandled gRPC exception: " + e.getStatus().getCode(), e);
        }
    }
}
