package ubb.graduation24.immopal.agency_service.service;

import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ubb.graduation24.immopal.agency_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.grpc.PersonOuterClass;
import ubb.graduation24.immopal.grpc.PersonServiceRPCGrpc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonClientServiceTest {

    @Mock
    private PersonServiceRPCGrpc.PersonServiceRPCBlockingStub personServiceStub;

    @InjectMocks
    private PersonClientService personClientService;

    @Test
    void testGetPerson_Success() {
        Long personId = 1L;
        PersonOuterClass.Person expectedPerson = PersonOuterClass.Person.newBuilder()
                .setId(personId)
                .setFirstName("John")
                .setLastName("Doe")
                .build();

        PersonOuterClass.GetPersonResponse response = PersonOuterClass.GetPersonResponse.newBuilder()
                .setPerson(expectedPerson)
                .build();

        when(personServiceStub.getPerson(any(PersonOuterClass.GetPersonRequest.class)))
                .thenReturn(response);

        PersonOuterClass.Person resultPerson = personClientService.getPerson(personId);

        assertNotNull(resultPerson);
        assertEquals(personId, resultPerson.getId());
        assertEquals("John", resultPerson.getFirstName());
        assertEquals("Doe", resultPerson.getLastName());
    }

    @Test
    void testGetPerson_ResourceNotFoundException() {
        Long personId = 1L;

        // Simulate a NOT_FOUND exception
        when(personServiceStub.getPerson(any(PersonOuterClass.GetPersonRequest.class)))
                .thenThrow(new StatusRuntimeException(io.grpc.Status.NOT_FOUND));

        ResourceNotFoundException thrownException = assertThrows(
                ResourceNotFoundException.class,
                () -> personClientService.getPerson(personId),
                "Expected getPerson() to throw ResourceNotFoundException"
        );

        assertTrue(thrownException.getMessage().contains("Failed to get Person with Id " + personId));
    }

    @Test
    void testGetPerson_RuntimeException() {
        Long personId = 1L;

        // Simulate an UNAVAILABLE exception
        when(personServiceStub.getPerson(any(PersonOuterClass.GetPersonRequest.class)))
                .thenThrow(new StatusRuntimeException(io.grpc.Status.UNAVAILABLE));

        RuntimeException thrownException = assertThrows(
                RuntimeException.class,
                () -> personClientService.getPerson(personId),
                "Expected getPerson() to throw RuntimeException"
        );

        assertTrue(thrownException.getMessage().contains("Unhandled gRPC exception: UNAVAILABLE"));
    }
}