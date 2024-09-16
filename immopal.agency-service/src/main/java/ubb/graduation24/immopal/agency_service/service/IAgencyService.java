package ubb.graduation24.immopal.agency_service.service;

import ubb.graduation24.immopal.agency_service.domain.Agency;

import java.util.List;

public interface IAgencyService {
    List<Agency> getAgencies();
    Agency getAgencyById(Long id);
    Agency createAgency(Agency agency);
    Agency updateAgency(Long id, Agency agency);
    void deleteAgency(Long id);
}
