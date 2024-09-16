package ubb.graduation24.immopal.person_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import ubb.graduation24.immopal.person_service.domain.Person;
import ubb.graduation24.immopal.person_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.person_service.exception.ServiceOperationException;
import ubb.graduation24.immopal.person_service.model.PersonDto;
import ubb.graduation24.immopal.person_service.repository.PersonRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {
    private PersonDto personDto;
    private Person person;
    private Long personId;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        personId = 1L;

        personDto = PersonDto.builder()
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("1234567890")
                .dateOfBirth(LocalDate.parse("1990-01-01"))
                .address("123 Main St")
                .pictureUrl("http://example.com/pic.jpg")
                .userId(1L)
                .propertyIds(new ArrayList<>(Arrays.asList(101L, 102L)))
                .appointmentIds(new ArrayList<>(Arrays.asList(201L, 202L)))
                .bidIds(new ArrayList<>(Arrays.asList("bid1", "bid2")))
                .build();

        person = Person.builder()
                .firstName(personDto.getFirstName())
                .lastName(personDto.getLastName())
                .phoneNumber(personDto.getPhoneNumber())
                .dateOfBirth(personDto.getDateOfBirth())
                .address(personDto.getAddress())
                .pictureUrl(personDto.getPictureUrl())
                .userId(personDto.getUserId())
                .propertyIds(personDto.getPropertyIds())
                .appointmentIds(personDto.getAppointmentIds())
                .bidIds(personDto.getBidIds())
                .build();
    }

    @Test
    void testGetAllPersons_Success() {
        Person person1 = new Person();
        Person person2 = new Person();
        List<Person> persons = List.of(person1, person2);

        when(personRepository.findAll()).thenReturn(persons);

        List<Person> result = personService.getAllPersons();

        assertEquals(2, result.size());
        verify(personRepository).findAll();
    }

    @Test
    void testGetAllPersons_NoPersons() {
        when(personRepository.findAll()).thenReturn(new ArrayList<>());

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> personService.getAllPersons(),
                "Expected getAllPersons() to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Persons not found"));
    }

    @Test
    void testGetPersonById_Success() {
        Person person = new Person();
        Long personId = 1L;
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        Person result = personService.getPersonById(personId);

        assertNotNull(result);
        verify(personRepository).findById(personId);
    }

    @Test
    void testGetPersonById_NotFound() {
        Long personId = 1L;
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> personService.getPersonById(personId),
                "Expected getPersonById() to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Person with Id = " + personId + " not found"));
    }

    @Test
    void testSavePerson_Success() {
        when(personRepository.save(any(Person.class))).thenReturn(person);

        Person result = personService.savePerson(personDto);

        assertNotNull(result);
        assertEquals(personDto.getFirstName(), result.getFirstName());
        assertEquals(personDto.getLastName(), result.getLastName());
        assertEquals(personDto.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(personDto.getDateOfBirth(), result.getDateOfBirth());
        assertEquals(personDto.getAddress(), result.getAddress());
        assertEquals(personDto.getPictureUrl(), result.getPictureUrl());
        assertEquals(personDto.getUserId(), result.getUserId());
        assertEquals(personDto.getPropertyIds(), result.getPropertyIds());
        assertEquals(personDto.getAppointmentIds(), result.getAppointmentIds());
        assertEquals(personDto.getBidIds(), result.getBidIds());
        verify(personRepository).save(any(Person.class));
    }

    @Test
    void testSavePerson_EmptyLists() {
        personDto.setPropertyIds(new ArrayList<>());
        personDto.setAppointmentIds(new ArrayList<>());
        personDto.setBidIds(new ArrayList<>());

        Person personWithEmptyLists = Person.builder()
                .propertyIds(new ArrayList<>())
                .appointmentIds(new ArrayList<>())
                .bidIds(new ArrayList<>())
                .build();

        when(personRepository.save(any(Person.class))).thenReturn(personWithEmptyLists);

        Person result = personService.savePerson(personDto);

        assertNotNull(result);
        assertEquals(new ArrayList<>(), result.getPropertyIds());
        assertEquals(new ArrayList<>(), result.getAppointmentIds());
        assertEquals(new ArrayList<>(), result.getBidIds());
        verify(personRepository).save(any(Person.class));
    }

    @Test
    void testSavePerson_NullLists() {
        personDto.setPropertyIds(null);
        personDto.setAppointmentIds(null);
        personDto.setBidIds(null);

        Person personWithNullLists = Person.builder()
                .propertyIds(new ArrayList<>())
                .appointmentIds(new ArrayList<>())
                .bidIds(new ArrayList<>())
                .build();

        when(personRepository.save(any(Person.class))).thenReturn(personWithNullLists);

        Person result = personService.savePerson(personDto);

        assertNotNull(result);
        assertEquals(new ArrayList<>(), result.getPropertyIds());
        assertEquals(new ArrayList<>(), result.getAppointmentIds());
        assertEquals(new ArrayList<>(), result.getBidIds());
        verify(personRepository).save(any(Person.class));
    }

    @Test
    void testSavePerson_DataIntegrityViolation() {
        when(personRepository.save(any(Person.class))).thenThrow(new DataIntegrityViolationException("Database error"));

        ServiceOperationException thrown = assertThrows(
                ServiceOperationException.class,
                () -> personService.savePerson(personDto),
                "Expected savePerson() to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Error while saving person"));
    }

    @Test
    void testUpdatePerson_Success() {
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class))).thenReturn(person);

        Person result = personService.updatePerson(personId, personDto);

        assertNotNull(result);
        assertEquals(personDto.getFirstName(), result.getFirstName());
        assertEquals(personDto.getLastName(), result.getLastName());
        assertEquals(personDto.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(personDto.getDateOfBirth(), result.getDateOfBirth());
        assertEquals(personDto.getAddress(), result.getAddress());
        assertEquals(personDto.getPictureUrl(), result.getPictureUrl());
        assertEquals(personDto.getPropertyIds(), result.getPropertyIds());
        assertEquals(personDto.getAppointmentIds(), result.getAppointmentIds());
        assertEquals(personDto.getBidIds(), result.getBidIds());
        verify(personRepository).findById(personId);
        verify(personRepository).save(any(Person.class));
    }

    @Test
    void testUpdatePerson_EmptyLists() {
        personDto.setPropertyIds(new ArrayList<>());
        personDto.setAppointmentIds(new ArrayList<>());
        personDto.setBidIds(new ArrayList<>());

        Person personWithEmptyLists = Person.builder()
                .propertyIds(new ArrayList<>())
                .appointmentIds(new ArrayList<>())
                .bidIds(new ArrayList<>())
                .build();

        when(personRepository.findById(personId)).thenReturn(Optional.of(personWithEmptyLists));
        when(personRepository.save(any(Person.class))).thenReturn(personWithEmptyLists);

        Person result = personService.updatePerson(personId, personDto);

        assertNotNull(result);
        assertEquals(new ArrayList<>(), result.getPropertyIds());
        assertEquals(new ArrayList<>(), result.getAppointmentIds());
        assertEquals(new ArrayList<>(), result.getBidIds());
        verify(personRepository).findById(personId);
        verify(personRepository).save(any(Person.class));
    }

    @Test
    void testUpdatePerson_NullLists() {
        personDto.setPropertyIds(null);
        personDto.setAppointmentIds(null);
        personDto.setBidIds(null);

        Person personWithNullLists = Person.builder()
                .propertyIds(new ArrayList<>())
                .appointmentIds(new ArrayList<>())
                .bidIds(new ArrayList<>())
                .build();

        when(personRepository.findById(personId)).thenReturn(Optional.of(personWithNullLists));
        when(personRepository.save(any(Person.class))).thenReturn(personWithNullLists);

        Person result = personService.updatePerson(personId, personDto);

        assertNotNull(result);
        assertEquals(new ArrayList<>(), result.getPropertyIds());
        assertEquals(new ArrayList<>(), result.getAppointmentIds());
        assertEquals(new ArrayList<>(), result.getBidIds());
        verify(personRepository).findById(personId);
        verify(personRepository).save(any(Person.class));
    }

    @Test
    void testUpdatePerson_NotFound() {
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> personService.updatePerson(personId, personDto),
                "Expected updatePerson() to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Person with Id = " + personId + " not found"));
    }

    @Test
    void testDeletePerson_Success() {
        Long personId = 1L;
        Person person = new Person();
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        personService.deletePerson(personId);

        verify(personRepository).deleteById(personId);
    }

    @Test
    void testDeletePerson_NotFound() {
        Long personId = 1L;
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> personService.deletePerson(personId),
                "Expected deletePerson() to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Person with Id = " + personId + " not found"));
    }
}
