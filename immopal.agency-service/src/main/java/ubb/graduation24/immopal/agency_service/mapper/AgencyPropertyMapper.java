package ubb.graduation24.immopal.agency_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ubb.graduation24.immopal.agency_service.domain.AgencyProperty;
import ubb.graduation24.immopal.agency_service.model.AgencyPropertyDto;

@Mapper(componentModel = "spring")
public interface AgencyPropertyMapper {

    @Mappings({
            @Mapping(source = "agency.id", target = "agencyId"),
            @Mapping(source = "propertyId", target = "propertyId")
    })
    AgencyPropertyDto agencyPropertyToAgencyPropertyDto(AgencyProperty agencyProperty);

    @Mappings({
            @Mapping(source = "agencyId", target = "agency.id"),
            @Mapping(source = "propertyId", target = "propertyId")
    })
    AgencyProperty agencyPropertyDtoToAgencyProperty(AgencyPropertyDto agencyPropertyDto);

}
