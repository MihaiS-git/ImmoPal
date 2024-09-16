package ubb.graduation24.immopal.property_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.graduation24.immopal.property_service.converter.PropertyConverter;
import ubb.graduation24.immopal.property_service.domain.Property;
import ubb.graduation24.immopal.property_service.domain.PropertyCategory;
import ubb.graduation24.immopal.property_service.domain.TransactionType;
import ubb.graduation24.immopal.property_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.property_service.exception.ServiceOperationException;
import ubb.graduation24.immopal.property_service.model.PropertyDto;
import ubb.graduation24.immopal.property_service.service.IPropertyService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PropertyController {

    @Autowired
    private IPropertyService propertyService;

    @GetMapping("/properties")
    public ResponseEntity<List<PropertyDto>> getAllProperties() {
        try{
            List<PropertyDto> propertyDtos = propertyService.getProperties().stream()
                    .map(PropertyConverter::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(propertyDtos);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/properties/search/findAllWithImagesByTransactionType")
    public ResponseEntity<?> findAllWithImagesByTransactionType(
            @RequestParam("transactionType") String transactionType,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Property> propertiesPage = propertyService.findAllWithImagesByTransactionType(
                TransactionType.valueOf(transactionType), pageable);
        Page<PropertyDto> propertyDtosPage = propertiesPage.map(PropertyConverter::toDto);
        return ResponseEntity.ok(propertyDtosPage);
    }

    @GetMapping("/properties/search/findAllWithImagesByTransactionTypeAndPropertyCategory")
    public ResponseEntity<Page<PropertyDto>> findAllWithImagesByTransactionTypeAndPropertyCategory(
            @RequestParam("transactionType") String transactionType,
            @RequestParam("category") String category,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        PropertyCategory propertyCategory = PropertyCategory.valueOf(category);
        Pageable pageable = PageRequest.of(page, size);
        Page<Property> propertiesPage = propertyService.findAllWithImagesByTransactionTypeAndPropertyCategory(
                TransactionType.valueOf(transactionType), propertyCategory, pageable);
        Page<PropertyDto> propertyDtosPage = propertiesPage.map(PropertyConverter::toDto);
        return ResponseEntity.ok(propertyDtosPage);
    }

    @GetMapping("/properties/search/findAllWithImagesByAgentIdPaginated")
    public ResponseEntity<?> findAllWithImagesByAgentId(
            @RequestParam("agentId") Long agentId,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Property> propertiesPage = propertyService.findAllWithImagesByAgentId(agentId, pageable);
            Page<PropertyDto> propertyDtosPage = propertiesPage.map(PropertyConverter::toDto);
            return ResponseEntity.ok(propertyDtosPage);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/properties/search/findAllWithImagesByAgentId")
    public ResponseEntity<?> findAllWithImagesByAgentId(
            @RequestParam("agentId") Long agentId) {
        try {
            List<Property> properties = propertyService.findAllWithImagesByAgentId(agentId);
            List<PropertyDto> propertyDtos = properties.stream().map(PropertyConverter::toDto).toList();
            return ResponseEntity.ok(propertyDtos);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/properties/{id}")
    public ResponseEntity<PropertyDto> getPropertyById(@PathVariable Long id) {
        try {
            Property property = propertyService.getProperty(id);
            PropertyDto propertyDto = PropertyConverter.toDto(property);
            return new ResponseEntity<>(propertyDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/properties")
    public ResponseEntity<?> createProperty(@RequestBody PropertyDto propertyDto) {
        try {
            Property property = propertyService.saveProperty(PropertyConverter.toEntity(propertyDto));
            PropertyDto propertyDtoDto = PropertyConverter.toDto(property);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.LOCATION, "/api/properties/" + propertyDtoDto.getId());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (ServiceOperationException | ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/properties/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable Long id, @RequestBody PropertyDto propertyDto) {
        try{
            Property property = propertyService.updateProperty(id, PropertyConverter.toEntity(propertyDto));
            PropertyDto response = PropertyConverter.toDto(property);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ServiceOperationException ex){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/properties/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long id) {
        try {
            propertyService.deleteProperty(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
