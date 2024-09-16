package ubb.graduation24.immopal.agency_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.graduation24.immopal.agency_service.domain.Agency;
import ubb.graduation24.immopal.agency_service.domain.AgencyAgent;
import ubb.graduation24.immopal.agency_service.domain.AgencyProperty;
import ubb.graduation24.immopal.agency_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.agency_service.exception.ServiceOperationException;
import ubb.graduation24.immopal.agency_service.mapper.AgencyMapper;
import ubb.graduation24.immopal.agency_service.model.*;
import ubb.graduation24.immopal.agency_service.service.*;
import ubb.graduation24.immopal.grpc.PersonOuterClass;
import ubb.graduation24.immopal.grpc.PropertyOuterClass;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AgencyController {

    private final IAgencyService agencyService;
    private final IAgencyAgentService agencyAgentService;
    private final IAgencyPropertyService agencyPropertyService;
    private final AgencyMapper agencyMapper;
    private final PersonClientService personClientService;
    private final PropertyClientService propertyClientService;

    @GetMapping("/agencies")
    public ResponseEntity<List<AgencyDto>> getAllAgencies() {
        try {
            List<Agency> agencies = agencyService.getAgencies();
            List<AgencyDto> agencyDtos = agencies.stream()
                    .map(agencyMapper::agencyToAgencyDto)
                    .toList();
            return new ResponseEntity<>(agencyDtos, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/agencies/{id}")
    public ResponseEntity<AgencyDto> getAgencyById(@PathVariable Long id) {
        try {
            Agency agency = agencyService.getAgencyById(id);
            AgencyDto agencyDto = agencyMapper.agencyToAgencyDto(agency);
            return new ResponseEntity<>(agencyDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/agencies/{id}/agents")
    public ResponseEntity<List<AgentDto>> getAgentsByAgencyId(@PathVariable Long id) {
        try {
            List<AgentDto> agents = agencyAgentService.getAgencyAgentsByAgencyId(id).stream()
                    .map(agencyAgent -> {
                        Long agentId = agencyAgent.getAgentId();
                        PersonOuterClass.Person agentOuter = personClientService.getPerson(agentId);
                        return buildAgentDto(agentOuter);
                    }).toList();
            return new ResponseEntity<>(agents, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/agencies/{id}/properties")
    public ResponseEntity<List<PropertyDto>> getPropertiesByAgencyId(@PathVariable Long id) {
        try {
            List<AgencyProperty> agencyProperties = agencyPropertyService.getPropertiesByAgencyId(id);
            List<PropertyDto> propertyDtos = agencyProperties.stream()
                    .map(agencyProperty -> {
                        Long propertyId = agencyProperty.getPropertyId();
                        PropertyOuterClass.Property propertyOuter = propertyClientService.getPropertyById(propertyId);
                        PropertyDetailsDto propertyDetailsDto = buildPropertyDetailDto(propertyOuter);
                        AddressDto addressDto = buildAddressDto(propertyOuter);
                        List<PropertyImagesDto> propertyImagesDtos = IntStream.range(0, propertyOuter.getPropertyImagesCount())
                                .mapToObj(i -> PropertyImagesDto.builder()
                                        .id(propertyOuter.getPropertyImages(i).getId())
                                        .imageUrl(propertyOuter.getPropertyImages(i).getImageUrl())
                                        .build())
                                .toList();
                        return buildPropertyDto(propertyOuter, propertyDetailsDto, addressDto, propertyImagesDtos);
                    }).toList();
            return ResponseEntity.ok(propertyDtos);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/agencies")
    public ResponseEntity<?> createAgency(@RequestBody AgencyDto agencyDto) {
        try {
            Agency agency = agencyMapper.agencyDtoToAgency(agencyDto);
            Agency createdAgency = agencyService.createAgency(agency);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.LOCATION, "/api/agencies/" + createdAgency.getId());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (ServiceOperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/agencies/{id}")
    public ResponseEntity<AgencyDto> updateAgency(@PathVariable Long id, @RequestBody AgencyDto agencyDto) {
        try {
            Agency newAgency = agencyMapper.agencyDtoToAgency(agencyDto);
            Agency updatedAgency = agencyService.updateAgency(id, newAgency);
            AgencyDto updatedAgencyDto = agencyMapper.agencyToAgencyDto(updatedAgency);
            return new ResponseEntity<>(updatedAgencyDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/agencies/{id}")
    public ResponseEntity<?> deleteAgency(@PathVariable Long id) {
        try {
            agencyService.deleteAgency(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    private static PropertyDto buildPropertyDto(PropertyOuterClass.Property propertyOuter,
                                                PropertyDetailsDto propertyDetailsDto,
                                                AddressDto addressDto,
                                                List<PropertyImagesDto> propertyImagesDtos) {
        return PropertyDto.builder()
                .id(propertyOuter.getId())
                .transactionType(propertyOuter.getTransactionType().toString())
                .propertyCategory(propertyOuter.getPropertyCategory().toString())
                .propertyDetails(propertyDetailsDto)
                .address(addressDto)
                .price(propertyOuter.getPrice())
                .agentId(propertyOuter.getAgentId())
                .propertyImages(propertyImagesDtos)
                .build();
    }

    private static AddressDto buildAddressDto(PropertyOuterClass.Property propertyOuter) {
        return AddressDto.builder()
                .id(propertyOuter.getAddress().getId())
                .country(propertyOuter.getAddress().getCountry())
                .state(propertyOuter.getAddress().getState())
                .city(propertyOuter.getAddress().getCity())
                .neighborhood(propertyOuter.getAddress().getNeighborhood())
                .street(propertyOuter.getAddress().getStreet())
                .streetNo(propertyOuter.getAddress().getStreetNo())
                .latitude(propertyOuter.getAddress().getLatitude())
                .longitude(propertyOuter.getAddress().getLongitude())
                .build();
    }

    private static PropertyDetailsDto buildPropertyDetailDto(PropertyOuterClass.Property propertyOuter) {
        return PropertyDetailsDto.builder()
                .id(propertyOuter.getPropertyDetails().getId())
                .description(propertyOuter.getPropertyDetails().getDescription())
                .carpetArea(propertyOuter.getPropertyDetails().getCarpetArea())
                .builtUpArea(propertyOuter.getPropertyDetails().getBuiltUpArea())
                .comfortType(propertyOuter.getPropertyDetails().getComfortType().toString())
                .floor(propertyOuter.getPropertyDetails().getFloor())
                .structureType(propertyOuter.getPropertyDetails().getStructureType().toString())
                .yearOfConstruction(propertyOuter.getPropertyDetails().getYearOfConstruction())
                .bathNo(propertyOuter.getPropertyDetails().getBathNo())
                .kitchenNo(propertyOuter.getPropertyDetails().getKitchenNo())
                .bedroomNo(propertyOuter.getPropertyDetails().getBedroomNo())
                .parkingNo(propertyOuter.getPropertyDetails().getParkingNo())
                .balcony(propertyOuter.getPropertyDetails().hasBalcony())
                .terrace(propertyOuter.getPropertyDetails().hasTerrace())
                .swimmingPool(propertyOuter.getPropertyDetails().hasSwimmingPool())
                .energeticCertificate(propertyOuter.getPropertyDetails().getEnergeticCertificate().toString())
                .build();
    }

    private static AgentDto buildAgentDto(PersonOuterClass.Person agentOuter) {
        return AgentDto.builder()
                .id(agentOuter.getId())
                .firstName(agentOuter.getFirstName())
                .lastName(agentOuter.getLastName())
                .phoneNumber(agentOuter.getPhoneNumber())
                .dateOfBirth(LocalDate.parse(agentOuter.getDateOfBirth()))
                .address(agentOuter.getAddress())
                .pictureUrl(agentOuter.getPictureUrl())
                .userId(agentOuter.getUserId())
                .build();
    }
}
