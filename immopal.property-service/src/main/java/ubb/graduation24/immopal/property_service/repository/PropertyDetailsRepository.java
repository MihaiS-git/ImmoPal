package ubb.graduation24.immopal.property_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import ubb.graduation24.immopal.property_service.domain.PropertyDetails;

public interface PropertyDetailsRepository extends JpaRepository<PropertyDetails, Long>,
        QueryByExampleExecutor<PropertyDetails>,
        PagingAndSortingRepository<PropertyDetails, Long> {
}
