package ubb.graduation24.immopal.agency_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import ubb.graduation24.immopal.agency_service.domain.Agency;

public interface AgencyRepository extends JpaRepository<Agency, Long>,
        QueryByExampleExecutor<Agency>,
        PagingAndSortingRepository<Agency, Long> {
}
