package ubb.graduation24.immopal.person_service.service;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ubb.graduation24.immopal.grpc.PersonOuterClass;
import ubb.graduation24.immopal.person_service.domain.Person;
import ubb.graduation24.immopal.person_service.repository.PersonRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceRPCImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceRPCImpl personServiceRPC;

    @Mock
    private StreamObserver<PersonOuterClass.GetPersonResponse> getPersonResponseObserver;

    @Mock
    private StreamObserver<PersonOuterClass.AddPersonResponse> addPersonResponseObserver;

    @Mock
    private StreamObserver<PersonOuterClass.UpdatePersonResponse> updatePersonResponseObserver;

    @Mock
    private StreamObserver<PersonOuterClass.DeletePersonResponse> deletePersonResponseObserver;

    @Mock
    private StreamObserver<PersonOuterClass.GetPersonsResponse> getPersonsResponseObserver;


    @Test
    void testGetPersons_Success() {
        // Arrange
        Person person1 = Person.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("1234567890")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Main St, Anytown, USA")
                .pictureUrl("http://example.com/pic.jpg")
                .userId(1001L)
                .build();

        Person person2 = Person.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Doe")
                .phoneNumber("0987654321")
                .dateOfBirth(LocalDate.of(1985, 5, 15))
                .address("456 Elm St, Othertown, USA")
                .pictureUrl("http://example.com/jane.jpg")
                .userId(2002L)
                .build();

        List<Person> persons = Arrays.asList(person1, person2);

        when(personRepository.findAll()).thenReturn(persons);

        // Convert Person to gRPC Person
        List<PersonOuterClass.Person> grpcPersons = persons.stream()
                .map(p -> PersonOuterClass.Person.newBuilder()
                        .setId(p.getId())
                        .setFirstName(p.getFirstName())
                        .setLastName(p.getLastName())
                        .setPhoneNumber(p.getPhoneNumber())
                        .setDateOfBirth(p.getDateOfBirth().toString())
                        .setAddress(p.getAddress())
                        .setPictureUrl(p.getPictureUrl())
                        .setUserId(p.getUserId())
                        .build())
                .collect(Collectors.toList());

        PersonOuterClass.GetPersonsResponse expectedResponse = PersonOuterClass.GetPersonsResponse.newBuilder()
                .addAllPerson(grpcPersons)
                .build();

        // Act
        personServiceRPC.getPersons(
                PersonOuterClass.GetPersonsRequest.newBuilder().build(),
                getPersonsResponseObserver
        );

        // Assert
        verify(getPersonsResponseObserver).onNext(expectedResponse);
        verify(getPersonsResponseObserver).onCompleted();
    }

    @Test
    void testGetPerson_Success() {
        // Arrange
        Person person = Person.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("1234567890")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Main St, Anytown, USA")
                .pictureUrl("http://example.com/pic.jpg")
                .userId(1001L)
                .appointmentIds(Collections.singletonList(1L))
                .propertyIds(Collections.singletonList(2L))
                .bidIds(Collections.singletonList("bid123"))
                .build();

        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        // Act
        personServiceRPC.getPerson(
                PersonOuterClass.GetPersonRequest.newBuilder()
                        .setPersonId(1L)
                        .build(),
                getPersonResponseObserver
        );

        // Expected gRPC Person object
        PersonOuterClass.Person grpcPerson = PersonOuterClass.Person.newBuilder()
                .setId(1L)
                .setFirstName("John")
                .setLastName("Doe")
                .setPhoneNumber("1234567890")
                .setDateOfBirth(LocalDate.of(1990, 1, 1).toString())
                .setAddress("123 Main St, Anytown, USA")
                .setPictureUrl("http://example.com/pic.jpg")
                .setUserId(1001L)
                .addAllAppointmentIds(Collections.singletonList(1L))
                .addAllPropertyIds(Collections.singletonList(2L))
                .addAllBidIds(Collections.singletonList("bid123"))
                .build();

        // Expected response
        PersonOuterClass.GetPersonResponse expectedResponse = PersonOuterClass.GetPersonResponse.newBuilder()
                .setPerson(grpcPerson)
                .build();

        // Assert
        verify(getPersonResponseObserver).onNext(expectedResponse);
        verify(getPersonResponseObserver).onCompleted();
    }

    @Test
    void testGetPerson_NotFound() {
        // Arrange
        Long nonExistentPersonId = 1L;

        // Mock repository to return empty for nonExistentPersonId
        when(personRepository.findById(nonExistentPersonId)).thenReturn(Optional.empty());

        // Act
        personServiceRPC.getPerson(
                PersonOuterClass.GetPersonRequest.newBuilder().setPersonId(nonExistentPersonId).build(),
                getPersonResponseObserver
        );

        // Assert
        verify(getPersonResponseObserver).onError(argThat(throwable ->
                throwable instanceof StatusRuntimeException &&
                        ((StatusRuntimeException) throwable).getStatus().getCode() == Status.Code.NOT_FOUND &&
                        ((StatusRuntimeException) throwable).getStatus().getDescription()
                                .equals("Person with id " + nonExistentPersonId + " not found")
        ));
    }


    @Test
    void testAddPerson_Success() {
        // Arrange
        Person savedPerson = Person.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Doe")
                .phoneNumber("0987654321")
                .dateOfBirth(LocalDate.of(1985, 5, 15))
                .address("456 Elm St, Othertown, USA")
                .pictureUrl("http://example.com/jane.jpg")
                .userId(2002L)
                .appointmentIds(Collections.emptyList())
                .propertyIds(Collections.emptyList())
                .bidIds(Collections.emptyList())
                .build();

        when(personRepository.save(any(Person.class))).thenReturn(savedPerson);

        // Act
        personServiceRPC.addPerson(
                PersonOuterClass.AddPersonRequest.newBuilder()
                        .setPerson(PersonOuterClass.Person.newBuilder()
                                .setFirstName("Jane")
                                .setLastName("Doe")
                                .setPhoneNumber("0987654321")
                                .setDateOfBirth(LocalDate.of(1985, 5, 15).toString())
                                .setAddress("456 Elm St, Othertown, USA")
                                .setPictureUrl("http://example.com/jane.jpg")
                                .setUserId(2002L)
                                .build())
                        .build(),
                addPersonResponseObserver
        );

        // Expected gRPC response
        PersonOuterClass.Person grpcPerson = PersonOuterClass.Person.newBuilder()
                .setId(2L)
                .setFirstName("Jane")
                .setLastName("Doe")
                .setPhoneNumber("0987654321")
                .setDateOfBirth(LocalDate.of(1985, 5, 15).toString())
                .setAddress("456 Elm St, Othertown, USA")
                .setPictureUrl("http://example.com/jane.jpg")
                .setUserId(2002L)
                .build();

        PersonOuterClass.AddPersonResponse expectedResponse = PersonOuterClass.AddPersonResponse.newBuilder()
                .setPerson(grpcPerson)
                .build();

        // Assert
        verify(addPersonResponseObserver).onNext(expectedResponse);
        verify(addPersonResponseObserver).onCompleted();
    }


    @Test
    void testUpdatePerson_Success() {
        // Arrange
        Person existingPerson = Person.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("1234567890")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Main St, Anytown, USA")
                .pictureUrl("http://example.com/pic.jpg")
                .userId(1001L)
                .appointmentIds(Collections.singletonList(1L))
                .propertyIds(Collections.singletonList(2L))
                .bidIds(Collections.singletonList("bid123"))
                .build();

        Person updatedPerson = Person.builder()
                .id(1L)
                .firstName("John Updated")
                .lastName("Doe Updated")
                .phoneNumber("0987654321")
                .dateOfBirth(LocalDate.of(1991, 1, 1))
                .address("456 Main St, Anytown, USA")
                .pictureUrl("http://example.com/updated_pic.jpg")
                .userId(1001L)
                .appointmentIds(Arrays.asList(1L, 2L)) // Updated lists
                .propertyIds(Arrays.asList(2L, 3L))
                .bidIds(Arrays.asList("bid123", "bid456"))
                .build();

        when(personRepository.findById(1L)).thenReturn(Optional.of(existingPerson));
        when(personRepository.save(any(Person.class))).thenReturn(updatedPerson);

        // Act
        personServiceRPC.updatePerson(
                PersonOuterClass.UpdatePersonRequest.newBuilder()
                        .setPerson(PersonOuterClass.Person.newBuilder()
                                .setId(1L)
                                .setFirstName("John Updated")
                                .setLastName("Doe Updated")
                                .setPhoneNumber("0987654321")
                                .setDateOfBirth(LocalDate.of(1991, 1, 1).toString())
                                .setAddress("456 Main St, Anytown, USA")
                                .setPictureUrl("http://example.com/updated_pic.jpg")
                                .setUserId(1001L)
                                .addAllAppointmentIds(Arrays.asList(1L, 2L))
                                .addAllPropertyIds(Arrays.asList(2L, 3L))
                                .addAllBidIds(Arrays.asList("bid123", "bid456"))
                                .build())
                        .build(),
                updatePersonResponseObserver
        );

        // Expected gRPC response
        PersonOuterClass.UpdatePersonResponse expectedResponse = PersonOuterClass.UpdatePersonResponse.newBuilder()
                .setPerson(PersonOuterClass.Person.newBuilder()
                        .setId(1L)
                        .setFirstName("John Updated")
                        .setLastName("Doe Updated")
                        .setPhoneNumber("0987654321")
                        .setDateOfBirth(LocalDate.of(1991, 1, 1).toString())
                        .setAddress("456 Main St, Anytown, USA")
                        .setPictureUrl("http://example.com/updated_pic.jpg")
                        .setUserId(1001L)
                        .addAllAppointmentIds(Arrays.asList(1L, 2L))
                        .addAllPropertyIds(Arrays.asList(2L, 3L))
                        .addAllBidIds(Arrays.asList("bid123", "bid456"))
                        .build())
                .build();

        // Assert
        verify(updatePersonResponseObserver).onNext(expectedResponse);
        verify(updatePersonResponseObserver).onCompleted();
    }


    @Test
    void testDeletePersonById_Success() {
        // Arrange
        Long personId = 1L;
        Person person = Person.builder()
                .id(personId)
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("1234567890")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Main St, Anytown, USA")
                .pictureUrl("http://example.com/pic.jpg")
                .userId(1001L)
                .appointmentIds(Collections.singletonList(1L))
                .propertyIds(Collections.singletonList(2L))
                .bidIds(Collections.singletonList("bid123"))
                .build();

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        // Act
        personServiceRPC.deletePersonById(
                PersonOuterClass.DeletePersonRequest.newBuilder().setId(personId).build(),
                deletePersonResponseObserver
        );

        // Expected gRPC response
        PersonOuterClass.DeletePersonResponse expectedResponse = PersonOuterClass.DeletePersonResponse.newBuilder()
                .setMessage("Person deleted successfully")
                .build();

        // Assert
        verify(personRepository).delete(person);
        verify(deletePersonResponseObserver).onNext(expectedResponse);
        verify(deletePersonResponseObserver).onCompleted();
    }

    @Test
    void testDeletePersonById_NotFound() {
        // Arrange
        Long personId = 1L;
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        // Act
        personServiceRPC.deletePersonById(
                PersonOuterClass.DeletePersonRequest.newBuilder().setId(personId).build(),
                deletePersonResponseObserver
        );

        // Assert
        verify(deletePersonResponseObserver).onError(argThat(throwable ->
                throwable instanceof StatusRuntimeException &&
                        ((StatusRuntimeException) throwable).getStatus().getCode() == Status.Code.NOT_FOUND &&
                        ((StatusRuntimeException) throwable).getStatus().getDescription()
                                .equals("Person with id " + personId + " not found")
        ));
    }
}