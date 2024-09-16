package ubb.graduation24.immopal.person_service.service;

import ubb.graduation24.immopal.person_service.domain.Person;
import ubb.graduation24.immopal.person_service.model.PersonDto;

import java.util.List;

public interface IPersonService {
    List<Person> getAllPersons();
    Person getPersonById(Long id);
    Person savePerson(PersonDto person);
    Person updatePerson(Long id, PersonDto person);
    void deletePerson(Long id);
}
