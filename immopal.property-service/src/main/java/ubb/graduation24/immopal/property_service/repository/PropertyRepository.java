package ubb.graduation24.immopal.property_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import ubb.graduation24.immopal.property_service.domain.Property;
import ubb.graduation24.immopal.property_service.domain.PropertyCategory;
import ubb.graduation24.immopal.property_service.domain.TransactionType;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long>,
        QueryByExampleExecutor<Property>,
        PagingAndSortingRepository<Property, Long> {

    @Query("SELECT p FROM Property p LEFT JOIN FETCH p.propertyImages WHERE p.id = :propertyId")
    Optional<Property> findByIdWithImages(@Param("propertyId") Long propertyId);

    @Query("SELECT DISTINCT p FROM Property p LEFT JOIN FETCH p.propertyImages")
    List<Property> findAllWithImages();

    @Query("SELECT DISTINCT p FROM Property p LEFT JOIN FETCH p.propertyImages WHERE p.transactionType = :transactionType")
    Page<Property> findAllWithImagesByTransactionType(@Param("transactionType") TransactionType transactionType, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Property p LEFT JOIN FETCH p.propertyImages WHERE p.agentId = :agentId")
    Page<Property> findAllWithImagesByAgentId(@Param("agentId") Long agentId, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Property p LEFT JOIN FETCH p.propertyImages WHERE p.agentId = :agentId")
    List<Property> findAllWithImagesByAgentId(@Param("agentId") Long agentId);

    @Query("SELECT DISTINCT p FROM Property p LEFT JOIN FETCH p.propertyImages WHERE p.transactionType = :transactionType AND p.propertyCategory = :propertyCategory")
    Page<Property> findAllByTransactionTypeAndPropertyCategory(@Param("transactionType") TransactionType transactionType, @Param("propertyCategory") PropertyCategory propertyCategory, Pageable pageable);
}
