package ubb.graduation24.immopal.appointment_service.service;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ubb.graduation24.immopal.appointment_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.grpc.PersonOuterClass;
import ubb.graduation24.immopal.grpc.PersonServiceRPCGrpc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonClientServiceTest {

    @Mock
    private PersonServiceRPCGrpc.PersonServiceRPCBlockingStub personServiceStub;

    @InjectMocks
    private PersonClientService personClientService;

    @Test
    void getPerson_Success() {
        // Given
        Long personId = 1L;
        PersonOuterClass.Person person = PersonOuterClass.Person.newBuilder().setId(personId).build();
        PersonOuterClass.GetPersonResponse response = PersonOuterClass.GetPersonResponse.newBuilder()
                .setPerson(person)
                .build();

        when(personServiceStub.getPerson(any(PersonOuterClass.GetPersonRequest.class))).thenReturn(response);

        // When
        PersonOuterClass.Person result = personClientService.getPerson(personId);

        // Then
        assertEquals(personId, result.getId());
        verify(personServiceStub, times(1)).getPerson(any(PersonOuterClass.GetPersonRequest.class));
    }

    @Test
    void getPerson_Failure() {
        // Given
        Long personId = 1L;
        when(personServiceStub.getPerson(any(PersonOuterClass.GetPersonRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.NOT_FOUND));

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            personClientService.getPerson(personId);
        });

        assertEquals("Failed to get Person with Id " + personId, exception.getMessage());
        verify(personServiceStub, times(1)).getPerson(any(PersonOuterClass.GetPersonRequest.class));
    }

    @Test
    void getPerson_OtherStatusRuntimeException() {
        // Given
        Long personId = 1L;

        // Simulate  an UNAVAILABLE exception
        when(personServiceStub.getPerson(any(PersonOuterClass.GetPersonRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.UNAVAILABLE));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            personClientService.getPerson(personId);
        });

        assertEquals("Unhandled gRPC exception: UNAVAILABLE", exception.getMessage());
        verify(personServiceStub, times(1)).getPerson(any(PersonOuterClass.GetPersonRequest.class));
    }

//    @Test
//    void updatePerson_Success() {
//        // Given
//        Long personId = 1L;
//        PersonOuterClass.Person person = PersonOuterClass.Person.newBuilder().setId(personId).build();
//        PersonOuterClass.UpdatePersonResponse response = PersonOuterClass.UpdatePersonResponse.newBuilder()
//                .setPerson(person)
//                .build();
//
//        when(personServiceStub.updatePerson(any(PersonOuterClass.UpdatePersonRequest.class))).thenReturn(response);
//
//        // When
//        PersonOuterClass.Person result = personClientService.updatePerson(personId, person);
//
//        // Then
//        assertEquals(personId, result.getId());
//        verify(personServiceStub, times(1)).updatePerson(any(PersonOuterClass.UpdatePersonRequest.class));
//    }

//    @Test
//    void updatePerson_Failure() {
//        // Given
//        Long personId = 1L;
//        PersonOuterClass.Person person = PersonOuterClass.Person.newBuilder().setId(personId).build();
//        when(personServiceStub.updatePerson(any(PersonOuterClass.UpdatePersonRequest.class)))
//                .thenThrow(new StatusRuntimeException(Status.NOT_FOUND));
//
//        // When & Then
//        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
//            personClientService.updatePerson(personId, person);
//        });
//
//        assertEquals("Failed to update Person with Id " + personId, exception.getMessage());
//        verify(personServiceStub, times(1)).updatePerson(any(PersonOuterClass.UpdatePersonRequest.class));
//    }

//    @Test
//    void updatePerson_OtherStatusRuntimeException() {
//        // Given
//        Long personId = 1L;
//        PersonOuterClass.Person person = PersonOuterClass.Person.newBuilder().setId(personId).build();
//
//        // Simulate an UNAVAILABLE exception
//        when(personServiceStub.updatePerson(any(PersonOuterClass.UpdatePersonRequest.class)))
//                .thenThrow(new StatusRuntimeException(Status.UNAVAILABLE));
//
//        // When & Then
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            personClientService.updatePerson(personId, person);
//        });
//
//        assertEquals("Unhandled gRPC exception: UNAVAILABLE", exception.getMessage());
//        verify(personServiceStub, times(1)).updatePerson(any(PersonOuterClass.UpdatePersonRequest.class));
//    }
}