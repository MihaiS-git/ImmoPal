package ubb.graduation24.immopal.property_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.JpaSystemException;
import ubb.graduation24.immopal.property_service.domain.*;
import ubb.graduation24.immopal.property_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.property_service.exception.ServiceOperationException;
import ubb.graduation24.immopal.property_service.repository.AddressRepository;
import ubb.graduation24.immopal.property_service.repository.PropertyDetailsRepository;
import ubb.graduation24.immopal.property_service.repository.PropertyImagesRepository;
import ubb.graduation24.immopal.property_service.repository.PropertyRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropertyServiceImplTest {

    @InjectMocks
    private PropertyServiceImpl propertyService;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private PropertyDetailsRepository propertyDetailsRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private PropertyImagesRepository propertyImagesRepository;

    private Property property;

    @BeforeEach
    void setUp() {
        Address address = new Address();
        address.setId(1L);
        address.setCountry("Country");
        address.setState("State");
        address.setCity("City");
        address.setNeighborhood("Neighborhood");
        address.setStreet("Street");
        address.setStreetNo("Street No");
        address.setLatitude(45.0);
        address.setLongitude(90.0);

        PropertyDetails details = new PropertyDetails();
        details.setId(1L);
        details.setDescription("Description");
        details.setCarpetArea(100.0);
        details.setBuiltUpArea(120.0);
        details.setComfortType(ComfortType.BASIC);
        details.setFloor("3");
        details.setStructureType(StructureType.WOOD);
        details.setYearOfConstruction(2015);
        details.setBathNo(2);
        details.setKitchenNo(1);
        details.setBedroomNo(3);
        details.setParkingNo(1);
        details.setBalcony(true);
        details.setTerrace(false);
        details.setSwimmingPool(true);
        details.setEnergeticCertificate(EnergeticCertificate.B);

        PropertyImages image = new PropertyImages();
        image.setId(1L);
        image.setImageUrl("http://example.com/image.jpg");

        property = new Property();
        property.setId(1L);
        property.setAgentId(1L);
        property.setTransactionType(TransactionType.RENT);
        property.setPropertyCategory(PropertyCategory.APARTMENT);
        property.setPrice(1000.0);
        property.setAddress(address);
        property.setPropertyDetails(details);
        property.setPropertyImages(List.of(image));
    }

    @Test
    void getProperties_Success() {
        List<Property> properties = List.of(property);
        when(propertyRepository.findAllWithImages()).thenReturn(properties);

        List<Property> result = propertyService.getProperties();

        assertEquals(1, result.size());
        verify(propertyRepository, times(1)).findAllWithImages();
    }

    @Test
    void getProperties_Empty() {
        when(propertyRepository.findAllWithImages()).thenReturn(Collections.emptyList());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            propertyService.getProperties();
        });

        assertEquals("No properties found", exception.getMessage());
        verify(propertyRepository, times(1)).findAllWithImages();
    }

    @Test
    void findAllWithImagesByTransactionType_Success() {
        Page<Property> properties = new PageImpl<>(List.of(property));
        Pageable pageable = PageRequest.of(0, 10);
        when(propertyRepository.findAllWithImagesByTransactionType(TransactionType.RENT, pageable)).thenReturn(properties);

        Page<Property> result = propertyService.findAllWithImagesByTransactionType(TransactionType.RENT, pageable);

        assertEquals(1, result.getTotalElements());
        verify(propertyRepository, times(1)).findAllWithImagesByTransactionType(TransactionType.RENT, pageable);
    }

    @Test
    void findAllWithImagesByTransactionType_Empty() {
        Pageable pageable = PageRequest.of(0, 10);
        when(propertyRepository.findAllWithImagesByTransactionType(TransactionType.RENT, pageable)).thenReturn(Page.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            propertyService.findAllWithImagesByTransactionType(TransactionType.RENT, pageable);
        });

        assertEquals("No properties found", exception.getMessage());
        verify(propertyRepository, times(1)).findAllWithImagesByTransactionType(TransactionType.RENT, pageable);
    }

    @Test
    void findAllWithImagesByAgentId_Success() {
        Page<Property> properties = new PageImpl<>(List.of(property));
        Pageable pageable = PageRequest.of(0, 10);
        when(propertyRepository.findAllWithImagesByAgentId(1L, pageable)).thenReturn(properties);

        Page<Property> result = propertyService.findAllWithImagesByAgentId(1L, pageable);

        assertEquals(1, result.getTotalElements());
        verify(propertyRepository, times(1)).findAllWithImagesByAgentId(1L, pageable);
    }

    @Test
    void findAllWithImagesByAgentId_Empty() {
        Pageable pageable = PageRequest.of(0, 10);
        when(propertyRepository.findAllWithImagesByAgentId(1L, pageable)).thenReturn(Page.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            propertyService.findAllWithImagesByAgentId(1L, pageable);
        });

        assertEquals("No properties found", exception.getMessage());
        verify(propertyRepository, times(1)).findAllWithImagesByAgentId(1L, pageable);
    }

    @Test
    void getProperty_Success() {
        when(propertyRepository.findByIdWithImages(1L)).thenReturn(Optional.of(property));

        Property result = propertyService.getProperty(1L);

        assertEquals(property, result);
        verify(propertyRepository, times(1)).findByIdWithImages(1L);
    }

    @Test
    void getProperty_NotFound() {
        when(propertyRepository.findByIdWithImages(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            propertyService.getProperty(1L);
        });

        assertEquals("No Property found", exception.getMessage());
        verify(propertyRepository, times(1)).findByIdWithImages(1L);
    }

    @Test
    void saveProperty_Success() {
        Address savedAddress = property.getAddress();
        PropertyDetails savedDetails = property.getPropertyDetails();
        Property savedProperty = property;
        PropertyImages savedImage = property.getPropertyImages().get(0);

        when(addressRepository.save(any(Address.class))).thenReturn(savedAddress);
        when(propertyDetailsRepository.save(any(PropertyDetails.class))).thenReturn(savedDetails);
        when(propertyRepository.save(any(Property.class))).thenReturn(savedProperty);
        when(propertyImagesRepository.save(any(PropertyImages.class))).thenReturn(savedImage);

        Property result = propertyService.saveProperty(property);

        assertEquals(savedProperty, result);
        verify(addressRepository, times(1)).save(any(Address.class));
        verify(propertyDetailsRepository, times(1)).save(any(PropertyDetails.class));
        verify(propertyRepository, times(2)).save(any(Property.class));
        verify(propertyImagesRepository, times(1)).save(any(PropertyImages.class));
    }

    @Test
    void saveProperty_AddressIsNull() {
        Property property = new Property();
        property.setPropertyDetails(new PropertyDetails());
        property.setPropertyImages(new ArrayList<>());

        when(propertyRepository.save(any(Property.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        Property savedProperty = propertyService.saveProperty(property);

        assertNotNull(savedProperty);
        assertNotNull(savedProperty.getAddress());
        assertTrue(savedProperty.getAddress() instanceof Address);
        verify(addressRepository, never()).save(any(Address.class));
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void saveProperty_AddressIsNotNull() {
        Address address = new Address();
        Property property = new Property();
        property.setAddress(address);
        property.setPropertyDetails(new PropertyDetails());
        property.setPropertyImages(new ArrayList<>());

        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(propertyRepository.save(any(Property.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        Property savedProperty = propertyService.saveProperty(property);

        assertNotNull(savedProperty);
        assertEquals(address, savedProperty.getAddress());
        verify(addressRepository, times(1)).save(any(Address.class));
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void saveProperty_PropertyDetailsIsNull() {
        Property property = new Property();
        property.setAddress(new Address());
        property.setPropertyImages(new ArrayList<>());

        when(propertyRepository.save(any(Property.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        Property savedProperty = propertyService.saveProperty(property);

        assertNotNull(savedProperty);
        assertNotNull(savedProperty.getPropertyDetails());
        assertTrue(savedProperty.getPropertyDetails() instanceof PropertyDetails);
        verify(propertyDetailsRepository, never()).save(any(PropertyDetails.class));
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void saveProperty_PropertyDetailsIsNotNull() {
        PropertyDetails propertyDetails = new PropertyDetails();
        Property property = new Property();
        property.setPropertyDetails(propertyDetails);
        property.setAddress(new Address());
        property.setPropertyImages(new ArrayList<>());

        when(propertyDetailsRepository.save(any(PropertyDetails.class))).thenReturn(propertyDetails);
        when(propertyRepository.save(any(Property.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        Property savedProperty = propertyService.saveProperty(property);

        assertNotNull(savedProperty);
        assertEquals(propertyDetails, savedProperty.getPropertyDetails());
        verify(propertyDetailsRepository, times(1)).save(any(PropertyDetails.class));
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void saveProperty_PropertyImagesIsNull() {
        Property property = new Property();
        property.setAddress(new Address());
        property.setPropertyDetails(new PropertyDetails());
        property.setPropertyImages(null);

        when(propertyRepository.save(any(Property.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        Property savedProperty = propertyService.saveProperty(property);

        assertNotNull(savedProperty);
        assertNotNull(savedProperty.getPropertyImages());
        assertTrue(savedProperty.getPropertyImages().isEmpty());
        verify(propertyImagesRepository, never()).save(any(PropertyImages.class));
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void saveProperty_PropertyImagesIsNotNull() {
        // Arrange
        PropertyImages image = new PropertyImages();
        List<PropertyImages> images = new ArrayList<>();
        images.add(image);

        Property property = new Property();
        property.setPropertyImages(images);
        property.setAddress(new Address());
        property.setPropertyDetails(new PropertyDetails());

        // Mock behavior
        when(propertyImagesRepository.save(any(PropertyImages.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        when(propertyRepository.save(any(Property.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        Property savedProperty = propertyService.saveProperty(property);

        // Assert
        assertNotNull(savedProperty);
        assertNotNull(savedProperty.getPropertyImages());
        assertEquals(1, savedProperty.getPropertyImages().size());

        // Verify interactions
        verify(propertyImagesRepository, times(1)).save(any(PropertyImages.class));
        verify(propertyRepository, times(2)).save(any(Property.class));
    }


    @Test
    void saveProperty_ExceptionThrown() {
        Property property = new Property();
        property.setAddress(new Address());
        property.setPropertyDetails(new PropertyDetails());
        property.setPropertyImages(new ArrayList<>());

        when(propertyRepository.save(any(Property.class))).thenThrow(new JpaSystemException(new RuntimeException()));

        ServiceOperationException exception = assertThrows(ServiceOperationException.class, () -> propertyService.saveProperty(property));

        assertTrue(exception.getMessage().contains("saveProperty(property) --- saveProperty failed"));
    }

    @Test
    void updateProperty_Success() {
        Property existingProperty = new Property();
        existingProperty.setId(1L);
        existingProperty.setAddress(new Address());
        existingProperty.setPropertyDetails(new PropertyDetails());

        Property updatedProperty = new Property();
        updatedProperty.setId(1L);
        updatedProperty.setPropertyImages(new ArrayList<>());

        when(propertyRepository.findByIdWithImages(1L)).thenReturn(Optional.of(existingProperty));
        when(propertyRepository.save(any(Property.class))).thenReturn(existingProperty);

        Property result = propertyService.updateProperty(1L, updatedProperty);

        assertEquals(existingProperty, result);
        verify(propertyRepository, times(1)).findByIdWithImages(1L);
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void updateProperty_NullAddressOnUpdate() {
        Property existingProperty = new Property();
        existingProperty.setId(1L);
        existingProperty.setAddress(new Address());

        Property updatedProperty = new Property();

        when(propertyRepository.findByIdWithImages(1L)).thenReturn(Optional.of(existingProperty));
        when(propertyRepository.save(any(Property.class))).thenReturn(existingProperty);

        Property result = propertyService.updateProperty(1L, updatedProperty);

        assertEquals(existingProperty, result);
        verify(propertyRepository, times(1)).findByIdWithImages(1L);
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void updateProperty_NullPropertyDetailsOnUpdate() {
        Property existingProperty = new Property();
        existingProperty.setId(1L);
        existingProperty.setPropertyDetails(new PropertyDetails());

        Property updatedProperty = new Property();

        when(propertyRepository.findByIdWithImages(1L)).thenReturn(Optional.of(existingProperty));
        when(propertyRepository.save(any(Property.class))).thenReturn(existingProperty);

        Property result = propertyService.updateProperty(1L, updatedProperty);

        assertEquals(existingProperty, result);
        verify(propertyRepository, times(1)).findByIdWithImages(1L);
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void updateProperty_PropertyNotFound() {
        Long propertyId = 1L;
        Property updatedProperty = new Property();

        when(propertyRepository.findByIdWithImages(propertyId)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> propertyService.updateProperty(propertyId, updatedProperty)
        );

        assertEquals("No Property found", thrown.getMessage());
        verify(propertyRepository, times(1)).findByIdWithImages(propertyId);
        verify(propertyRepository, never()).save(any(Property.class));
    }

    @Test
    void updateProperty_UpdateAddress() {
        Long propertyId = 1L;
        Property existingProperty = new Property();
        Property updatedProperty = new Property();
        updatedProperty.setAddress(new Address());

        when(propertyRepository.findByIdWithImages(propertyId)).thenReturn(Optional.of(existingProperty));
        when(propertyRepository.save(any(Property.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        Property result = propertyService.updateProperty(propertyId, updatedProperty);

        assertNotNull(result.getAddress());
        verify(propertyRepository, times(1)).findByIdWithImages(propertyId);
        verify(propertyRepository, times(1)).save(existingProperty);
    }

    @Test
    void updateProperty_UpdatePropertyDetails() {
        Long propertyId = 1L;
        Property existingProperty = new Property();
        PropertyDetails existingDetails = new PropertyDetails();
        existingProperty.setPropertyDetails(existingDetails);

        Property updatedProperty = new Property();
        PropertyDetails updatedDetails = new PropertyDetails();
        updatedProperty.setPropertyDetails(updatedDetails);

        when(propertyRepository.findByIdWithImages(propertyId)).thenReturn(Optional.of(existingProperty));
        when(propertyRepository.save(any(Property.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        Property result = propertyService.updateProperty(propertyId, updatedProperty);

        assertEquals(updatedDetails.getDescription(), existingProperty.getPropertyDetails().getDescription());
        verify(propertyRepository, times(1)).findByIdWithImages(propertyId);
        verify(propertyRepository, times(1)).save(existingProperty);
    }

    @Test
    void updateProperty_UpdatePropertyImages() {
        Long propertyId = 1L;
        Property existingProperty = new Property();
        List<PropertyImages> existingImages = Arrays.asList(new PropertyImages());
        existingProperty.setPropertyImages(existingImages);

        Property updatedProperty = new Property();
        List<PropertyImages> updatedImages = Arrays.asList(new PropertyImages(), new PropertyImages());
        updatedProperty.setPropertyImages(updatedImages);

        when(propertyRepository.findByIdWithImages(propertyId)).thenReturn(Optional.of(existingProperty));
        when(propertyRepository.save(any(Property.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        Property result = propertyService.updateProperty(propertyId, updatedProperty);

        assertEquals(2, result.getPropertyImages().size());
        verify(propertyRepository, times(1)).findByIdWithImages(propertyId);
        verify(propertyRepository, times(1)).save(existingProperty);
    }

    @Test
    void updateProperty_FailureOnSave() {
        Property existingProperty = new Property();
        existingProperty.setId(1L);
        existingProperty.setPropertyImages(new ArrayList<>());

        Property updatedProperty = new Property();

        when(propertyRepository.findByIdWithImages(1L)).thenReturn(Optional.of(existingProperty));
        when(propertyRepository.save(any(Property.class))).thenThrow(new DataAccessException("Error") {});

        ServiceOperationException exception = assertThrows(ServiceOperationException.class, () -> {
            propertyService.updateProperty(1L, updatedProperty);
        });

        assertEquals("updateProperty(id) --- saveProperty failed", exception.getMessage());
        verify(propertyRepository, times(1)).findByIdWithImages(1L);
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void updateProperty_UpdateAddress_Success() {
        Long id = 1L;

        // Existing property with an address
        Address existingAddress = new Address();
        existingAddress.setCountry("CountryA");

        Property existingProperty = new Property();
        existingProperty.setAddress(existingAddress);

        // Updated property with new address
        Address updatedAddress = new Address();
        updatedAddress.setCountry("CountryB");

        Property updatedProperty = new Property();
        updatedProperty.setAddress(updatedAddress);

        when(propertyRepository.findByIdWithImages(id)).thenReturn(Optional.of(existingProperty));
        when(propertyRepository.save(any(Property.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        Property result = propertyService.updateProperty(id, updatedProperty);

        assertEquals("CountryB", result.getAddress().getCountry());

        verify(propertyRepository, times(1)).findByIdWithImages(id);
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void updateProperty_UpdateDetails_Success() {
        Long id = 1L;

        // Existing property with details
        PropertyDetails existingDetails = new PropertyDetails();
        existingDetails.setDescription("Old Description");

        Property existingProperty = new Property();
        existingProperty.setPropertyDetails(existingDetails);

        // Updated property with new details
        PropertyDetails updatedDetails = new PropertyDetails();
        updatedDetails.setDescription("New Description");

        Property updatedProperty = new Property();
        updatedProperty.setPropertyDetails(updatedDetails);

        when(propertyRepository.findByIdWithImages(id)).thenReturn(Optional.of(existingProperty));
        when(propertyRepository.save(any(Property.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        Property result = propertyService.updateProperty(id, updatedProperty);

        assertEquals("New Description", result.getPropertyDetails().getDescription());

        verify(propertyRepository, times(1)).findByIdWithImages(id);
        verify(propertyRepository, times(1)).save(any(Property.class));
    }


    @Test
    void deleteProperty_Success() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        propertyService.deleteProperty(1L);

        verify(propertyRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteProperty_NotFound() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            propertyService.deleteProperty(1L);
        });

        assertEquals("No Property found", exception.getMessage());
        verify(propertyRepository, times(0)).deleteById(1L);
    }
}
