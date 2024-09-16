package ubb.graduation24.immopal.person_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.graduation24.immopal.person_service.domain.Person;
import ubb.graduation24.immopal.person_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.person_service.mapper.PersonMapper;
import ubb.graduation24.immopal.person_service.model.PersonDto;
import ubb.graduation24.immopal.person_service.service.IPersonService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private IPersonService personService;

    @Autowired
    private PersonMapper personMapper;

    @GetMapping(value = "/persons", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonDto>> getAllPersons() {
        try {
            List<Person> persons = personService.getAllPersons();
            List<PersonDto> personDto = persons.stream()
                    .map(person -> personMapper.personToPersonDto(person))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(personDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/persons/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDto> getPersonById(@PathVariable Long id) {
        try {
            Person person = personService.getPersonById(id);
            PersonDto personDto = personMapper.personToPersonDto(person);
            return ResponseEntity.ok(personDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable Long id, @RequestBody PersonDto personDto) {
        try {
            Person savedPerson = personService.updatePerson(id, personDto);
            return ResponseEntity.ok(personMapper.personToPersonDto(savedPerson));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
