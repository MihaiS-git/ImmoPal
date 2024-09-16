package ubb.graduation24.immopal.agency_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ubb.graduation24.immopal.agency_service.domain.Address;
import ubb.graduation24.immopal.agency_service.model.AddressDto;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Named("addressToAddressDto")
    AddressDto addressToAddressDto(Address address);

    @Named("addressDtoToAddress")
    Address addressDtoToAddress(AddressDto addressDto);

}
