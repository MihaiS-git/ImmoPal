package ubb.graduation24.immopal.agency_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import ubb.graduation24.immopal.agency_service.domain.AgencyAgent;

import java.util.List;
import java.util.Optional;

public interface AgencyAgentRepository extends JpaRepository<AgencyAgent, Long>,
        QueryByExampleExecutor<AgencyAgent>,
        PagingAndSortingRepository<AgencyAgent, Long> {

    Optional<AgencyAgent> findByAgentId(Long agentId);
    List<AgencyAgent> getAllByAgencyId(Long agencyId);

}
