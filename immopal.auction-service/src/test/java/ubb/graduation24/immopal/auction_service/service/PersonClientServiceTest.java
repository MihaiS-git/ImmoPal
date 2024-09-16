package ubb.graduation24.immopal.auction_service.service;

import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ubb.graduation24.immopal.auction_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.grpc.PersonOuterClass;
import ubb.graduation24.immopal.grpc.PersonServiceRPCGrpc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonClientServiceTest {

    @Mock
    private PersonServiceRPCGrpc.PersonServiceRPCBlockingStub personServiceStub;

    @InjectMocks
    private PersonClientService personClientService;

    @Test
    void getPerson_ShouldReturnPerson_WhenSuccess() {
        // Arrange
        Long personId = 1L;
        PersonOuterClass.Person expectedPerson = PersonOuterClass.Person.newBuilder()
                .setId(personId)
                .setFirstName("John")
                .setLastName("Doe")
                .build();
        PersonOuterClass.GetPersonResponse response = PersonOuterClass.GetPersonResponse.newBuilder()
                .setPerson(expectedPerson)
                .build();
        PersonOuterClass.GetPersonRequest request = PersonOuterClass.GetPersonRequest.newBuilder()
                .setPersonId(personId)
                .build();

        when(personServiceStub.getPerson(request)).thenReturn(response);

        // Act
        PersonOuterClass.Person person = personClientService.getPerson(personId);

        // Assert
        assertEquals(expectedPerson, person);
        verify(personServiceStub, times(1)).getPerson(request);
    }

    @Test
    void getPerson_ShouldThrowException_WhenGrpcCallFails() {
        // Arrange
        Long personId = 1L;
        PersonOuterClass.GetPersonRequest request = PersonOuterClass.GetPersonRequest.newBuilder()
                .setPersonId(personId)
                .build();

        when(personServiceStub.getPerson(request)).thenThrow(new StatusRuntimeException(io.grpc.Status.NOT_FOUND));

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            personClientService.getPerson(personId);
        });
        assertEquals("Failed to get Person with Id 1", exception.getMessage());
    }
}
