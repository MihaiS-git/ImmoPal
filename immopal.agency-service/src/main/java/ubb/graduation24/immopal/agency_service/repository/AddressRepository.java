package ubb.graduation24.immopal.agency_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ubb.graduation24.immopal.agency_service.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
