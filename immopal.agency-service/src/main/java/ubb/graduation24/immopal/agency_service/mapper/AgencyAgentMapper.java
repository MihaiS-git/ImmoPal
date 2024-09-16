package ubb.graduation24.immopal.agency_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ubb.graduation24.immopal.agency_service.domain.AgencyAgent;
import ubb.graduation24.immopal.agency_service.model.AgencyAgentDto;

@Mapper(componentModel = "spring")
public interface AgencyAgentMapper {

    @Mappings({
            @Mapping(source = "agency.id", target = "agencyId"),
            @Mapping(source = "agentId", target = "agentId")
    })
    AgencyAgentDto agencyAgentToAgencyAgentDto(AgencyAgent agencyAgent);

    @Mappings({
            @Mapping(source = "agencyId", target = "agency.id"),
            @Mapping(source = "agentId", target = "agentId")
    })
    AgencyAgent agencyAgentDtoToAgencyAgent(AgencyAgentDto agencyAgentDto);
}
