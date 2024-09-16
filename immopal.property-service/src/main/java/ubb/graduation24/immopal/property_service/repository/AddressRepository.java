package ubb.graduation24.immopal.property_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import ubb.graduation24.immopal.property_service.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long>,
        QueryByExampleExecutor<Address>,
        PagingAndSortingRepository<Address, Long> {
}
