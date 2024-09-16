package ubb.graduation24.immopal.property_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ubb.graduation24.immopal.property_service.domain.Property;
import ubb.graduation24.immopal.property_service.domain.PropertyCategory;
import ubb.graduation24.immopal.property_service.domain.TransactionType;

import java.util.List;

public interface IPropertyService {
    List<Property> getProperties();
    Property getProperty(Long id);
    Property saveProperty(Property property);
    Property updateProperty(Long id, Property property);
    void deleteProperty(Long id);

    Page<Property> findAllWithImagesByTransactionType(TransactionType transactionType, Pageable pageable);
    Page<Property> findAllWithImagesByTransactionTypeAndPropertyCategory(TransactionType transactionType, PropertyCategory propertyCategory, Pageable pageable);

    List<Property> findAllWithImagesByAgentId(Long agentId);
    Page<Property> findAllWithImagesByAgentId(Long agentId, Pageable pageable);
}
