package ubb.graduation24.immopal.property_service.service;

import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.property_service.domain.*;
import ubb.graduation24.immopal.property_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.property_service.exception.ServiceOperationException;
import ubb.graduation24.immopal.property_service.repository.AddressRepository;
import ubb.graduation24.immopal.property_service.repository.PropertyDetailsRepository;
import ubb.graduation24.immopal.property_service.repository.PropertyImagesRepository;
import ubb.graduation24.immopal.property_service.repository.PropertyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PropertyServiceImpl implements IPropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyDetailsRepository propertyDetailsRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PropertyImagesRepository propertyImagesRepository;

    @Override
    public List<Property> getProperties() {
        log.info("getProperties() --- method entered");
        List<Property> properties = propertyRepository.findAllWithImages();
        if (properties.isEmpty()) {
            log.info("getProperties() --- properties list is empty");
            throw new ResourceNotFoundException("No properties found");
        }
        log.info("getProperties() --- PropertiesSize ={}", properties.size());
        return properties;
    }

    @Override
    public Page<Property> findAllWithImagesByTransactionType(TransactionType transactionType, Pageable pageable) {
        log.debug("findAllWithImagesByTransactionType --- method entered");
        Page<Property> properties = propertyRepository.findAllWithImagesByTransactionType(transactionType, pageable);
        if (properties.isEmpty()) {
            log.info("findAllWithImagesByTransactionType --- properties list is empty");
            throw new ResourceNotFoundException("No properties found");
        }
        return properties;
    }

    @Override
    public Page<Property> findAllWithImagesByTransactionTypeAndPropertyCategory(TransactionType transactionType, PropertyCategory propertyCategory, Pageable pageable) {
        Page<Property> properties = propertyRepository.findAllByTransactionTypeAndPropertyCategory(transactionType, propertyCategory, pageable);
        return properties;
    }

    @Override
    public Page<Property> findAllWithImagesByAgentId(Long agentId, Pageable pageable) {
        log.debug("findAllWithImagesByAgentId --- method entered");
        Page<Property> properties = propertyRepository.findAllWithImagesByAgentId(agentId, pageable);
        if (properties.isEmpty()) {
            log.info("findAllWithImagesByAgentId --- properties list is empty");
            throw new ResourceNotFoundException("No properties found");
        }
        return properties;
    }

    @Override
    public List<Property> findAllWithImagesByAgentId(Long agentId) {
        log.debug("findAllWithImagesByAgentId --- method entered");
        List<Property> properties = propertyRepository.findAllWithImagesByAgentId(agentId);
        if (properties.isEmpty()) {
            log.info("findAllWithImagesByAgentId --- properties list is empty");
            throw new ResourceNotFoundException("No properties found");
        }
        return properties;
    }

    @Override
    public Property getProperty(Long id) {
        log.info("getProperty(id) --- method entered");
        Optional<Property> optional = propertyRepository.findByIdWithImages(id);
        if (optional.isPresent()) {
            log.info("getProperty(id) --- Property found");
            return optional.get();
        } else {
            log.info("getProperty(id) --- Property not found");
            throw new ResourceNotFoundException("No Property found");
        }
    }

    @Override
    public Property saveProperty(Property property) {
        log.info("saveProperty(property) --- method entered with property: {}", property);
        try {
            Address address = property.getAddress();
            if (address != null) {
                Address savedAddress = addressRepository.save(address);
                property.setAddress(savedAddress);
            } else {
                log.warn("saveProperty(property) --- saving address is null");
                property.setAddress(new Address());
            }

            PropertyDetails propertyDetails = property.getPropertyDetails();
            if (propertyDetails != null) {
                PropertyDetails savedPropertyDetails = propertyDetailsRepository.save(propertyDetails);
                property.setPropertyDetails(savedPropertyDetails);
            } else {
                log.warn("saveProperty(propertyDetails) --- saving propertyDetails is null");
                property.setPropertyDetails(new PropertyDetails());
            }

            Property savedProperty = propertyRepository.save(property);
            List<PropertyImages> propertyImages = property.getPropertyImages();
            if (propertyImages != null && !propertyImages.isEmpty()) {
                List<PropertyImages> savedImages = new ArrayList<>();
                for (PropertyImages propertyImage : propertyImages) {
                    propertyImage.setProperty(savedProperty);
                    PropertyImages savedPropertyImage = propertyImagesRepository.save(propertyImage);
                    savedImages.add(savedPropertyImage);
                }
                savedProperty.setPropertyImages(savedImages);
                propertyRepository.save(savedProperty);
            } else {
                log.warn("saveProperty(propertyImages) --- saving propertyImages is null");
                property.setPropertyImages(new ArrayList<>());
            }

            log.info("saveProperty(property) --- Property saved successfully: {}", savedProperty);
            return savedProperty;
        } catch (DataAccessException | PersistenceException e) {
            log.error("saveProperty(property) --- saveProperty failed due to: {}", e.getMessage(), e);
            throw new ServiceOperationException("saveProperty(property) --- saveProperty failed");
        }
    }


    @Override
    public Property updateProperty(Long id, Property updatedProperty) {
        log.info("updateProperty(id) --- method entered");

        //TODO send propertyId to Person

        try {
            Property existingProperty = propertyRepository.findByIdWithImages(id)
                    .orElseThrow(() -> {
                        log.info("updateProperty(id) --- Property not found");
                        return new ResourceNotFoundException("No Property found");
                    });

            log.info("updateProperty(id) --- Property found");

            // Update Address
            Address updatedAddress = updatedProperty.getAddress();
            if (updatedAddress != null) {
                Address existingAddress = existingProperty.getAddress();
                if (existingAddress == null) {
                    existingProperty.setAddress(updatedAddress);
                } else {
                    existingAddress.setCountry(updatedAddress.getCountry());
                    existingAddress.setState(updatedAddress.getState());
                    existingAddress.setCity(updatedAddress.getCity());
                    existingAddress.setNeighborhood(updatedAddress.getNeighborhood());
                    existingAddress.setStreet(updatedAddress.getStreet());
                    existingAddress.setStreetNo(updatedAddress.getStreetNo());
                    existingAddress.setLatitude(updatedAddress.getLatitude());
                    existingAddress.setLongitude(updatedAddress.getLongitude());
                }
            }

            // Update PropertyDetails
            PropertyDetails updatedDetails = updatedProperty.getPropertyDetails();
            if (updatedDetails != null) {
                PropertyDetails existingDetails = existingProperty.getPropertyDetails();
                if (existingDetails == null) {
                    existingProperty.setPropertyDetails(updatedDetails);
                } else {
                    existingDetails.setDescription(updatedDetails.getDescription());
                    existingDetails.setCarpetArea(updatedDetails.getCarpetArea());
                    existingDetails.setBuiltUpArea(updatedDetails.getBuiltUpArea());
                    existingDetails.setComfortType(updatedDetails.getComfortType());
                    existingDetails.setFloor(updatedDetails.getFloor());
                    existingDetails.setStructureType(updatedDetails.getStructureType());
                    existingDetails.setYearOfConstruction(updatedDetails.getYearOfConstruction());
                    existingDetails.setBathNo(updatedDetails.getBathNo());
                    existingDetails.setKitchenNo(updatedDetails.getKitchenNo());
                    existingDetails.setBedroomNo(updatedDetails.getBedroomNo());
                    existingDetails.setParkingNo(updatedDetails.getParkingNo());
                    existingDetails.setBalcony(updatedDetails.isBalcony());
                    existingDetails.setTerrace(updatedDetails.isTerrace());
                    existingDetails.setSwimmingPool(updatedDetails.isSwimmingPool());
                    existingDetails.setEnergeticCertificate(updatedDetails.getEnergeticCertificate());
                }
            }

            // Update PropertyImages
            List<PropertyImages> updatedImages = updatedProperty.getPropertyImages();
            if (updatedImages != null && !updatedImages.isEmpty()) {
                List<PropertyImages> mutableImages = new ArrayList<>(existingProperty.getPropertyImages());
                mutableImages.clear();
                mutableImages.addAll(updatedImages);
                existingProperty.setPropertyImages(mutableImages);
            }

            // Update other fields
            existingProperty.setTransactionType(updatedProperty.getTransactionType());
            existingProperty.setPropertyCategory(updatedProperty.getPropertyCategory());
            existingProperty.setPrice(updatedProperty.getPrice());
            existingProperty.setAgentId(updatedProperty.getAgentId());

            Property savedProperty = propertyRepository.save(existingProperty);
            log.info("updateProperty(id) --- Property updated successfully");
            return savedProperty;

        } catch (PersistenceException | DataAccessException e) {
            log.error("updateProperty(id) --- saveProperty failed", e);
            throw new ServiceOperationException("updateProperty(id) --- saveProperty failed", e);
        }
    }


    @Override
    public void deleteProperty(Long id) {
        log.info("deleteProperty(id) --- method entered");
        Optional<Property> optional = propertyRepository.findById(id);
        if (optional.isPresent()) {
            log.info("deleteProperty(id) --- Property found");
            propertyRepository.deleteById(id);
        } else {
            log.info("deleteProperty(id) --- Property not found");
            throw new ResourceNotFoundException("No Property found");
        }
    }


}
