package ubb.graduation24.immopal.agency_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.agency_service.domain.AgencyAgent;
import ubb.graduation24.immopal.agency_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.agency_service.repository.AgencyAgentRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AgencyAgentServiceImpl implements IAgencyAgentService{

    @Autowired
    private AgencyAgentRepository agencyAgentRepository;

    @Override
    public List<AgencyAgent> getAgencyAgents() {
        log.info("getAgencyAgents() --- method entered");
        List<AgencyAgent> agencyAgents = agencyAgentRepository.findAll();
        if (agencyAgents.isEmpty()) {
            log.info("getAgencyAgents() --- agents not found");
            throw new ResourceNotFoundException("Agency Agents not found");
        }
        log.info("getAgencyAgents() --- agents found size {}", agencyAgents.size());
        return agencyAgents;
    }

    @Override
    public AgencyAgent getAgencyAgentById(Long id) {
        log.info("getAgencyAgentById(id) --- method entered");
        Optional<AgencyAgent> agencyAgent = agencyAgentRepository.findById(id);
        if (agencyAgent.isPresent()) {
            log.info("getAgencyAgentById(id) --- agent found {}", agencyAgent.get());
            return agencyAgent.get();
        } else {
            log.info("getAgencyAgentById(id) --- agent not found");
            throw new ResourceNotFoundException("Agency Agent not found");
        }
    }

    @Override
    public List<AgencyAgent> getAgencyAgentsByAgencyId(Long agencyId) {
        log.info("getAgentsByAgencyId() --- method entered");
        List<AgencyAgent> agencyAgents = agencyAgentRepository.getAllByAgencyId(agencyId);
        if (agencyAgents.isEmpty()) {
            log.info("getAgentsByAgencyId() --- agents not found");
            throw new ResourceNotFoundException("Agency Agent not found");
        }
        log.info("getAgentsByAgencyId() size={}", agencyAgents.size());
        return agencyAgents;
    }

    @Override
    public AgencyAgent createAgencyAgent(AgencyAgent agencyAgent) {
        log.info("createAgencyAgent(agencyAgent) --- method entered");
        AgencyAgent createdAgencyAgent = agencyAgentRepository.save(agencyAgent);
        log.info("createAgencyAgent(agencyAgent) --- agent created {}", createdAgencyAgent);
        return createdAgencyAgent;
    }

    @Override
    public AgencyAgent updateAgencyAgent(Long id, AgencyAgent agencyAgent) {
        log.info("updateAgencyAgent(id, agencyAgent) --- method entered");
        Optional<AgencyAgent> optional = agencyAgentRepository.findById(id);
        if (optional.isPresent()) {
            AgencyAgent updatedAgencyAgent = optional.get();
            log.info("updateAgencyAgent(id, agencyAgent) --- agent found {}", updatedAgencyAgent);
            updatedAgencyAgent.setAgency(agencyAgent.getAgency());
            updatedAgencyAgent.setAgentId(agencyAgent.getAgentId());
            log.info("updateAgencyAgent(): {}", updatedAgencyAgent);
            return agencyAgentRepository.save(updatedAgencyAgent);
        } else {
            log.info("updateAgencyAgent(id, agencyAgent) --- agent not found");
            throw new ResourceNotFoundException("Agency Agent not found");
        }
    }

    @Override
    public void deleteAgencyAgent(Long id) {
        log.info("deleteAgencyAgent(id) --- method entered");
        Optional<AgencyAgent> optional = agencyAgentRepository.findById(id);
        if (optional.isPresent()) {
            log.info("deleteAgencyAgent(id) --- agent found {}", optional.get());
            agencyAgentRepository.delete(optional.get());
        } else {
            log.info("deleteAgencyAgent(id) --- agent not found");
            throw new ResourceNotFoundException("Agency Agent not found");
        }
    }
}
