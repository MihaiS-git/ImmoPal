package ubb.graduation24.immopal.agency_service.service;

import ubb.graduation24.immopal.agency_service.domain.AgencyProperty;
import ubb.graduation24.immopal.agency_service.model.PropertyDto;

import java.util.List;

public interface IAgencyPropertyService {
    List<AgencyProperty> getAgencyProperty();
    AgencyProperty getAgencyPropertyById(Long id);
    AgencyProperty createAgencyProperty(AgencyProperty agencyProperty);
    AgencyProperty updateAgencyProperty(Long id, AgencyProperty agencyProperty);
    void deleteAgencyPropertyById(Long id);

    List<AgencyProperty> getPropertiesByAgencyId(Long id);
}
