package ubb.graduation24.immopal.agency_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.agency_service.domain.AgencyProperty;
import ubb.graduation24.immopal.agency_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.agency_service.repository.AgencyPropertyRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AgencyPropertyServiceImpl implements IAgencyPropertyService {

    @Autowired
    private AgencyPropertyRepository agencyPropertyRepository;


    @Override
    public List<AgencyProperty> getAgencyProperty() {
        log.info("getAgencyProperty() --- method entered");
        List<AgencyProperty> agencyPropertyList = agencyPropertyRepository.findAll();
        if(agencyPropertyList.isEmpty()) {
            log.info("getAgencyProperty() --- nothing found");
            throw new ResourceNotFoundException("No agency property found");
        }
        log.info("getAgencyProperty() --- size={}", agencyPropertyList.size());
        return agencyPropertyList;
    }

    @Override
    public AgencyProperty getAgencyPropertyById(Long id) {
        log.info("getAgencyPropertyById() --- method entered");
        Optional<AgencyProperty> agencyPropertyOptional = agencyPropertyRepository.findById(id);
        if(agencyPropertyOptional.isEmpty()){
            log.info("getAgencyPropertyById() --- nothing found");
            throw new ResourceNotFoundException("No agency property found");
        }
        log.info("getAgencyPropertyById() result ={}", agencyPropertyOptional.get());
        return agencyPropertyOptional.get();
    }

    @Override
    public List<AgencyProperty> getPropertiesByAgencyId(Long agencyId) {
        log.info("getPropertiesByAgencyId() --- method entered");
        List<AgencyProperty> agencyProperties = agencyPropertyRepository.getAllByAgencyId(agencyId);
        if (agencyProperties.isEmpty()) {
            log.info("getPropertiesByAgencyId() --- nothing found");
            throw new ResourceNotFoundException("No property found");
        }
        log.info("getPropertiesByAgencyId() --- size={}", agencyProperties.size());
        return agencyProperties;
    }

    @Override
    public AgencyProperty createAgencyProperty(AgencyProperty agencyProperty) {
        log.info("createAgencyProperty() --- method entered");
        AgencyProperty aProperty = agencyPropertyRepository.save(agencyProperty);
        log.info("createAgencyProperty() saved : {}", aProperty);
        return aProperty;
    }

    @Override
    public AgencyProperty updateAgencyProperty(Long id, AgencyProperty agencyProperty) {
        log.info("updateAgencyProperty() --- method entered");
        Optional<AgencyProperty> agencyPropertyOptional = agencyPropertyRepository.findById(id);
        if(agencyPropertyOptional.isPresent()){
            AgencyProperty updatedProperty = agencyPropertyOptional.get();
            log.info("updateAgencyProperty() data found : {}", updatedProperty);
            updatedProperty.setPropertyId(agencyProperty.getPropertyId());
            updatedProperty.setAgency(agencyProperty.getAgency());
            log.info("updateAgencyProperty() saved : {}", updatedProperty);
            return agencyPropertyRepository.save(updatedProperty);
        } else {
            log.info("updateAgencyProperty() --- nothing found");
            throw new ResourceNotFoundException("No agency property found");
        }
    }

    @Override
    public void deleteAgencyPropertyById(Long id) {
        log.info("deleteAgencyPropertyById() --- method entered");
        Optional<AgencyProperty> agencyPropertyOptional = agencyPropertyRepository.findById(id);
        if(agencyPropertyOptional.isPresent()){
            log.info("deleteAgencyPropertyById() data found : {}", agencyPropertyOptional.get());
            agencyPropertyRepository.delete(agencyPropertyOptional.get());
        } else {
            log.info("deleteAgencyPropertyById() --- nothing found");
            throw new ResourceNotFoundException("No agency property found");
        }
    }
}
