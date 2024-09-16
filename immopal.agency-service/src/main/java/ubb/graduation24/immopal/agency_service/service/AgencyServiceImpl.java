package ubb.graduation24.immopal.agency_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.agency_service.domain.Agency;
import ubb.graduation24.immopal.agency_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.agency_service.exception.ServiceOperationException;
import ubb.graduation24.immopal.agency_service.repository.AgencyRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AgencyServiceImpl implements IAgencyService {

    @Autowired
    private AgencyRepository agencyRepository;

    @Override
    public List<Agency> getAgencies() {
        log.info("getAgencies() --- method entered");
        List<Agency> agencies = agencyRepository.findAll();
        if (agencies.isEmpty()) {
            log.info("getAgencies() --- agencies not found");
            throw new ResourceNotFoundException("Agencies not found");
        }
        log.info("getAgencies() --- agencies size ={}", agencies.size());
        return agencies;
    }

    @Override
    public Agency getAgencyById(Long id) {
        log.info("getAgencyById() --- method entered");
        Optional<Agency> agency = agencyRepository.findById(id);
        if (agency.isPresent()) {
            log.info("getAgencyById() --- agency found {}", agency.get());
            return agency.get();
        } else {
            log.info("getAgencyById() --- agency not found");
            throw new ResourceNotFoundException("Agency not found");
        }
    }

    @Override
    public Agency createAgency(Agency agency) {
        log.info("createAgency() --- method entered");
        try {
            Agency newAgency = agencyRepository.save(agency);
            log.info("createAgency() --- agency created {}", newAgency);
            return newAgency;
        } catch (Exception e) {
            log.info("createAgency() --- agency creation failed");
            throw new ServiceOperationException("Agency creation failed");
        }
    }

    @Override
    public Agency updateAgency(Long id, Agency agency) {
        log.info("updateAgency() --- method entered");
        Optional<Agency> agencyOptional = agencyRepository.findById(id);
        if (agencyOptional.isPresent()) {
            log.info("updateAgency() --- agency found {}", agencyOptional.get());
            Agency updatedAgency = agencyRepository.save(agency);
            log.info("updateAgency() --- agency updated {}", updatedAgency);
            return updatedAgency;
        } else {
            log.info("updateAgency() --- agency not found");
            throw new ResourceNotFoundException("Agency not found");
        }
    }

    @Override
    public void deleteAgency(Long id) {
        log.info("deleteAgency() --- method entered");
        Optional<Agency> agencyOptional = agencyRepository.findById(id);
        if (agencyOptional.isPresent()) {
            log.info("deleteAgency() --- agency found");
            agencyRepository.deleteById(id);
        } else {
            log.info("deleteAgency() --- agency not found");
            throw new ResourceNotFoundException("Agency not found");
        }
    }
}
