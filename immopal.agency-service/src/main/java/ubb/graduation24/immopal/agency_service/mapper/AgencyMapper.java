package ubb.graduation24.immopal.agency_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ubb.graduation24.immopal.agency_service.domain.Agency;
import ubb.graduation24.immopal.agency_service.model.AgencyDto;

@Mapper(componentModel = "spring", uses = {AgencyAgentMapper.class, AgencyPropertyMapper.class, AddressMapper.class})
public interface AgencyMapper {

    @Mappings({
            @Mapping(source = "agents", target = "agents"),
            @Mapping(source = "properties", target = "properties"),
            @Mapping(source = "address", target = "address", qualifiedByName = "addressToAddressDto")
    })
    AgencyDto agencyToAgencyDto(Agency agency);

    @Mappings({
            @Mapping(source = "agents", target = "agents"),
            @Mapping(source = "properties", target = "properties"),
            @Mapping(source = "address", target = "address", qualifiedByName = "addressDtoToAddress")
    })
    Agency agencyDtoToAgency(AgencyDto agencyDto);

}
