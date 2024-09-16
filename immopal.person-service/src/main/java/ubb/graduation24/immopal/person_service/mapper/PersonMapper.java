package ubb.graduation24.immopal.person_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ubb.graduation24.immopal.person_service.domain.Person;
import ubb.graduation24.immopal.person_service.model.PersonDto;
import ubb.graduation24.immopal.person_service.model.SavePersonRequestDto;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDto personToPersonDto(Person person);

    Person personDtoToPerson(PersonDto personDto);

    SavePersonRequestDto savePersonRequestDtoToPerson(SavePersonRequestDto personDto);


}
