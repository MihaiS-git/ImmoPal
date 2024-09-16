package ubb.graduation24.immopal.appointment_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.graduation24.immopal.appointment_service.domain.Appointment;
import ubb.graduation24.immopal.appointment_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.appointment_service.exception.ServiceOperationException;
import ubb.graduation24.immopal.appointment_service.mapper.AppointmentMapper;
import ubb.graduation24.immopal.appointment_service.model.*;
import ubb.graduation24.immopal.appointment_service.service.AgencyClientService;
import ubb.graduation24.immopal.appointment_service.service.IAppointmentService;
import ubb.graduation24.immopal.appointment_service.service.PersonClientService;
import ubb.graduation24.immopal.appointment_service.service.PropertyClientService;
import ubb.graduation24.immopal.grpc.AgencyOuterClass;
import ubb.graduation24.immopal.grpc.PersonOuterClass;
import ubb.graduation24.immopal.grpc.PropertyOuterClass;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppointmentController {

    private static final Logger log = LoggerFactory.getLogger(AppointmentController.class);
    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private PersonClientService personClientService;

    @Autowired
    private PropertyClientService propertyClientService;

    @Autowired
    private AgencyClientService agencyClientService;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentDto>> getAllAppointments() {
        try {
            List<Appointment> appointments = appointmentService.getAppointments();
            List<AppointmentDto> appointmentDtos = appointments.stream()
                    .map(appointment -> appointmentMapper.appointmentToAppointmentDto(appointment))
                    .toList();
            return new ResponseEntity<>(appointmentDtos, HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<AppointmentResponseDto> getAppointmentById(@PathVariable Long id) {
        try {
            Appointment appointment = appointmentService.getAppointment(id);
            PersonOuterClass.Person customerOuter = personClientService.getPerson(appointment.getCustomerId());
            PersonOuterClass.Person agentOuter = personClientService.getPerson(appointment.getAgentId());
            AgencyOuterClass.Agency agencyOuter = agencyClientService.getAgencyByAgentId(agentOuter.getId());
            PropertyOuterClass.Property propertyOuter = propertyClientService.getProperty(appointment.getPropertyId());

            AppointmentResponseDto responseDto = populateResponseDtoWithData(id, customerOuter, agentOuter, agencyOuter, propertyOuter);
            responseDto.setStartDateTime(appointment.getStartDateTime());
            responseDto.setEndDateTime(appointment.getEndDateTime());
            responseDto.setDateCreated(appointment.getDateCreated());
            responseDto.setLastModifDate(appointment.getLastModifDate());
            responseDto.setApprovalStatus(appointment.getApprovalStatus());

            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    private AppointmentResponseDto populateResponseDtoWithData(Long id,
                                                               PersonOuterClass.Person customerOuter,
                                                               PersonOuterClass.Person agentOuter,
                                                               AgencyOuterClass.Agency agencyOuter,
                                                               PropertyOuterClass.Property propertyOuter) {
        log.info("populateResponseDtoWithData() --- method entered");

        PropertyDetailsDto propertyDetails = new PropertyDetailsDto().builder()
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
                .bedroomNo(propertyOuter.getPropertyDetails().getKitchenNo())
                .parkingNo(propertyOuter.getPropertyDetails().getParkingNo())
                .balcony(propertyOuter.getPropertyDetails().getBalcony())
                .terrace(propertyOuter.getPropertyDetails().getTerrace())
                .swimmingPool(propertyOuter.getPropertyDetails().getSwimmingPool())
                .energeticCertificate(propertyOuter.getPropertyDetails().getEnergeticCertificate().toString())
                .build();

        AddressDto address = AddressDto.builder()
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

        List<String> imageUrls = propertyOuter.getPropertyImagesList().stream()
                .map(PropertyOuterClass.PropertyImage::getImageUrl)
                .toList();

        AppointmentResponseDto responseDto = new AppointmentResponseDto().builder()
                .id(id)
                .customerId(customerOuter.getId())
                .customerFirstName(customerOuter.getFirstName())
                .customerLastName(customerOuter.getLastName())
                .customerPhoneNumber(customerOuter.getPhoneNumber())
                .customerPictureUrl(customerOuter.getPictureUrl())
                .agentId(agentOuter.getId())
                .agentFirstName(agentOuter.getFirstName())
                .agentLastName(agentOuter.getLastName())
                .agentPhoneNumber(agentOuter.getPhoneNumber())
                .agentPictureUrl(agentOuter.getPictureUrl())
                .agencyId(agencyOuter.getId())
                .agencyName(agencyOuter.getName())
                .propertyId(propertyOuter.getId())
                .transactionType(propertyOuter.getTransactionType().toString())
                .propertyCategory(propertyOuter.getPropertyCategory().toString())
                .propertyDetails(propertyDetails)
                .address(address)
                .price(propertyOuter.getPrice())
                .propertyImages(imageUrls)
                .build();

        log.info("populateResponseDtoWithData() --- method exited");
        return responseDto;
    }


    @PostMapping("/appointments")
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequestDto appointmentRequestDto) {
        try{
            Appointment savedAppointment = appointmentService.addAppointment(appointmentRequestDto);
            AppointmentDto appoitmentDto = appointmentMapper.appointmentToAppointmentDto(savedAppointment);
            return new ResponseEntity<>(appoitmentDto, HttpStatus.OK);
        } catch (ServiceOperationException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/appointments/{id}")
    public ResponseEntity<AppointmentDto> updateAppointment(@RequestBody AppointmentRequestDto appointmentRequestDto,
                                                            @PathVariable Long id) {
        try {
            log.info("updateAppointment() appointmentRequestDto: {}", appointmentRequestDto);
            Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentRequestDto);
            AppointmentDto appointmentDto = appointmentMapper.appointmentToAppointmentDto(updatedAppointment);
            return new ResponseEntity<>(appointmentDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        try {
            appointmentService.deleteAppointment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
