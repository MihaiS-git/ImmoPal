package ubb.graduation24.immopal.person_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.person_service.domain.Person;
import ubb.graduation24.immopal.person_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.person_service.exception.ServiceOperationException;
import ubb.graduation24.immopal.person_service.model.PersonDto;
import ubb.graduation24.immopal.person_service.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements IPersonService {

    private final PersonRepository personRepository;

    @Override
    public List<Person> getAllPersons() {
        log.trace("getAllPersons() --- method entered");
        List<Person> persons = personRepository.findAll();
        if (persons.isEmpty()) {
            log.error("getAllPersons() --- persons list is empty");
            throw new ResourceNotFoundException("Persons not found");
        }
        log.trace("getAllPersons(): PersonsSize={}", persons.size());
        return persons;
    }

    @Override
    public Person getPersonById(Long id) {
        log.trace("getPersonById() --- method entered");
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            log.trace("getPersonById: person = {}", person);
            return person;
        } else {
            log.error("getPersonById: Person not found");
            throw new ResourceNotFoundException("Person with Id = " + id + " not found");
        }
    }

    @Override
    public Person savePerson(PersonDto personDto) {
        log.trace("savePerson() --- method entered");
        try {
            Person toSave = Person.builder()
                    .firstName(personDto.getFirstName())
                    .lastName(personDto.getLastName())
                    .phoneNumber(personDto.getPhoneNumber())
                    .dateOfBirth(personDto.getDateOfBirth())
                    .address(personDto.getAddress())
                    .pictureUrl(personDto.getPictureUrl())
                    .userId(personDto.getUserId())
                    .propertyIds(personDto.getPropertyIds() != null ? personDto.getPropertyIds() : new ArrayList<>())
                    .appointmentIds(personDto.getAppointmentIds() != null ? personDto.getAppointmentIds() : new ArrayList<>())
                    .bidIds(personDto.getBidIds() != null ? personDto.getBidIds() : new ArrayList<>())
                    .build();
            Person savedPerson = personRepository.save(toSave);
            log.trace("savedPerson(): savedPerson={}", savedPerson);
            return savedPerson;
        } catch (DataIntegrityViolationException e) {
            log.error("Error while saving person: {}", e.getMessage());
            throw new ServiceOperationException("Error while saving person: " + e.getMessage());
        }
    }

    @Override
    public Person updatePerson(Long id, PersonDto personDto) {
        log.trace("updatePerson() --- method entered");
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person personToUpdate = optionalPerson.get();
            personToUpdate.setFirstName(personDto.getFirstName());
            personToUpdate.setLastName(personDto.getLastName());
            personToUpdate.setPhoneNumber(personDto.getPhoneNumber());
            personToUpdate.setDateOfBirth(personDto.getDateOfBirth());
            personToUpdate.setAddress(personDto.getAddress());
            personToUpdate.setPictureUrl(personDto.getPictureUrl() != null ? personDto.getPictureUrl() : personToUpdate.getPictureUrl());

            personToUpdate.setPropertyIds(personDto.getPropertyIds() != null ? personDto.getPropertyIds() : new ArrayList<>());
            personToUpdate.setAppointmentIds(personDto.getAppointmentIds() != null ? personDto.getAppointmentIds() : new ArrayList<>());
            personToUpdate.setBidIds(personDto.getBidIds() != null ? personDto.getBidIds() : new ArrayList<>());

            log.trace("updatePerson(): PersonUpdated = {}", personToUpdate);
            return personRepository.save(personToUpdate);
        } else {
            log.error("updatePerson(): person not found");
            throw new ResourceNotFoundException("Person with Id = " + id + " not found");
        }
    }

    @Override
    public void deletePerson(Long id) {
        log.trace("deletePerson() --- method entered");
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            personRepository.deleteById(id);
            log.trace("deletePerson(): person with Id = {}", id);
        } else {
            log.error("deletePerson(): Person with Id = {} not found", id);
            throw new ResourceNotFoundException("Person with Id = " + id + " not found");
        }
    }
}

