package ubb.graduation24.immopal.agency_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import ubb.graduation24.immopal.agency_service.domain.AgencyProperty;

import java.util.List;
import java.util.Optional;

public interface AgencyPropertyRepository extends JpaRepository<AgencyProperty, Long>,
        QueryByExampleExecutor<AgencyProperty>,
        PagingAndSortingRepository<AgencyProperty, Long> {

    List<AgencyProperty> getAllByAgencyId(Long agencyId);
    Optional<AgencyProperty> findByPropertyId(Long propertyId);
}
