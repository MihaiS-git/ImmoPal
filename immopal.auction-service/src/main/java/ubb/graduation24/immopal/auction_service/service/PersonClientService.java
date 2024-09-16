package ubb.graduation24.immopal.auction_service.service;

import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.auction_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.grpc.PersonOuterClass;
import ubb.graduation24.immopal.grpc.PersonServiceRPCGrpc;

@Slf4j
@Service
public class PersonClientService {

    @Autowired
    private PersonServiceRPCGrpc.PersonServiceRPCBlockingStub personServiceStub;

    public PersonOuterClass.Person getPerson(Long personId) {
        PersonOuterClass.GetPersonRequest person = PersonOuterClass.GetPersonRequest.newBuilder()
                .setPersonId(personId)
                .build();
        try {
            log.info("gRPC Requesting Person with Id={}", personId);
            PersonOuterClass.GetPersonResponse response = personServiceStub.getPerson(person);
            log.info("gRPC Response: {}", response);
            return response.getPerson();
        } catch (StatusRuntimeException e) {
            log.error("Failed to get Person with Id {} : {}", personId, e.getMessage());
            throw new ResourceNotFoundException("Failed to get Person with Id " + personId);
        }
    }
}
