package ubb.graduation24.immopal.agency_service.service;

import ubb.graduation24.immopal.agency_service.domain.AgencyAgent;

import java.util.List;

public interface IAgencyAgentService {
    List<AgencyAgent> getAgencyAgents();
    AgencyAgent getAgencyAgentById(Long id);
    AgencyAgent createAgencyAgent(AgencyAgent agencyAgent);
    AgencyAgent updateAgencyAgent(Long id, AgencyAgent agencyAgent);
    void deleteAgencyAgent(Long id);

    List<AgencyAgent> getAgencyAgentsByAgencyId(Long agencyId);
}
