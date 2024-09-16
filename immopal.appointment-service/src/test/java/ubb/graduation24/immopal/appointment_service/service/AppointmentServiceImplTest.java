package ubb.graduation24.immopal.appointment_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ubb.graduation24.immopal.appointment_service.domain.Appointment;
import ubb.graduation24.immopal.appointment_service.domain.Status;
import ubb.graduation24.immopal.appointment_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.appointment_service.exception.ServiceOperationException;
import ubb.graduation24.immopal.appointment_service.model.AppointmentRequestDto;
import ubb.graduation24.immopal.appointment_service.repository.AppointmentRepository;
import ubb.graduation24.immopal.grpc.PersonOuterClass;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceImplTest {

    private List<Appointment> appointments;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private RabbitMQSender rabbitMQSender;

    @Mock
    private PersonClientService personClientService;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @BeforeEach
    public void setUp() {
        Appointment appointment1 = Appointment.builder()
                .id(1L)
                .customerId(1L)
                .agentId(1L)
                .propertyId(1L)
                .startDateTime(LocalDateTime.now().plusDays(1))
                .endDateTime(LocalDateTime.now().plusDays(1).plusMinutes(60))
                .dateCreated(LocalDateTime.now())
                .lastModifDate(LocalDateTime.now())
                .approvalStatus(Status.PENDING)
                .build();

        Appointment appointment2 = Appointment.builder()
                .id(2L)
                .customerId(2L)
                .agentId(2L)
                .propertyId(2L)
                .startDateTime(LocalDateTime.now().plusDays(1))
                .endDateTime(LocalDateTime.now().plusDays(1).plusMinutes(60))
                .dateCreated(LocalDateTime.now())
                .lastModifDate(LocalDateTime.now())
                .approvalStatus(Status.PENDING)
                .build();

        appointments = new ArrayList<>(Arrays.asList(appointment1, appointment2));
    }

    @Test
    void getAppointments_Success() {
        when(appointmentRepository.findAll()).thenReturn(appointments);

        List<Appointment> retrievedAppointments = appointmentService.getAppointments();

        assertNotNull(retrievedAppointments);
        assertEquals(2, retrievedAppointments.size());
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    void getAppointments_EmptyList() {
        when(appointmentRepository.findAll()).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            appointmentService.getAppointments();
        });

        assertEquals("No appointments found", exception.getMessage());
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    void getAppointment_Success() {
        Appointment appointment = appointments.get(0);
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(appointment));

        Appointment retrievedAppointment = appointmentService.getAppointment(1L);

        assertNotNull(retrievedAppointment);
        assertEquals(1L, retrievedAppointment.getId());
        verify(appointmentRepository, times(1)).findById(anyLong());
    }

    @Test
    void getAppointment_NotFound() {
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            appointmentService.getAppointment(1L);
        });

        assertEquals("No appointment found", exception.getMessage());
        verify(appointmentRepository, times(1)).findById(anyLong());
    }

    @Test
    void addAppointment_AgentAvailable_Success() {
        // Given
        Long agentId = 1L;
        Long customerId = 1L;
        Long propertyId = 1L;
        LocalDateTime requestStartDateTime = LocalDateTime.now().plusDays(1);
        LocalDateTime requestEndDateTime = requestStartDateTime.plusMinutes(60);

        // Existing non-conflicting appointment
        LocalDateTime existingAppointmentStartDateTime = requestStartDateTime.minusMinutes(60);
        LocalDateTime existingAppointmentEndDateTime = existingAppointmentStartDateTime.plusMinutes(60);

        Appointment existingAppointment = Appointment.builder()
                .id(2L)
                .agentId(agentId)
                .customerId(customerId)
                .startDateTime(existingAppointmentStartDateTime)
                .endDateTime(existingAppointmentEndDateTime)
                .build();

        // New appointment to be added
        Appointment newAppointment = Appointment.builder()
                .id(1L)
                .customerId(customerId)
                .agentId(agentId)
                .propertyId(propertyId)
                .startDateTime(requestStartDateTime)
                .endDateTime(requestEndDateTime)
                .dateCreated(LocalDateTime.now())
                .lastModifDate(LocalDateTime.now())
                .approvalStatus(Status.PENDING)
                .build();

        // Mock repository to return the existing non-conflicting appointment
        when(appointmentRepository.findAll()).thenReturn(Collections.singletonList(existingAppointment));

        // Mock other dependencies
        doNothing().when(rabbitMQSender).notifyCustomerNewAppointment(any(Appointment.class));
        doNothing().when(rabbitMQSender).notifyAgentNewAppointment(any(Appointment.class));

        // Mock save to return the new appointment
        when(appointmentRepository.save(any(Appointment.class))).thenAnswer(invocation -> {
            Appointment appointment = invocation.getArgument(0);
            appointment.setId(1L);
            return appointment;
        });

        // Appointment request DTO
        AppointmentRequestDto requestDto = AppointmentRequestDto.builder()
                .customerId(customerId)
                .agentId(agentId)
                .propertyId(propertyId)
                .startDateTime(requestStartDateTime)
                .approvalStatus(Status.PENDING)
                .build();

        // Act
        Appointment savedAppointment = appointmentService.addAppointment(requestDto);

        // Assert
        assertNotNull(savedAppointment);
        assertEquals(Status.PENDING, savedAppointment.getApprovalStatus());
        assertEquals(agentId, savedAppointment.getAgentId());
        assertEquals(customerId, savedAppointment.getCustomerId());

        // Verify interactions
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
        verify(rabbitMQSender, times(1)).notifyCustomerNewAppointment(any(Appointment.class));
        verify(rabbitMQSender, times(1)).notifyAgentNewAppointment(any(Appointment.class));
    }


    @Test
    void addAppointment_AgentNotAvailable_Failure() {
        // Given
        Long agentId = 1L;
        Long customerId = 1L;
        Long propertyId = 1L;
        LocalDateTime requestStartDateTime = LocalDateTime.now().plusDays(1);

        // Create a conflicting appointment for the agent
        LocalDateTime existingAppointmentStartDateTime = requestStartDateTime.minusMinutes(30); // Overlapping time
        LocalDateTime existingAppointmentEndDateTime = existingAppointmentStartDateTime.plusMinutes(60);

        Appointment existingAppointment = new Appointment();
        existingAppointment.setAgentId(agentId);
        existingAppointment.setStartDateTime(existingAppointmentStartDateTime);
        existingAppointment.setEndDateTime(existingAppointmentEndDateTime);

        // Mock repository to return the conflicting appointment
        when(appointmentRepository.findAll()).thenReturn(Collections.singletonList(existingAppointment));

        // Mock person service to return an existing agent
        PersonOuterClass.Person agent = PersonOuterClass.Person.newBuilder()
                .setId(agentId)
                .build();

        // Appointment request DTO
        AppointmentRequestDto requestDto = AppointmentRequestDto.builder()
                .customerId(customerId)
                .agentId(agentId)
                .propertyId(propertyId)
                .startDateTime(requestStartDateTime)
                .approvalStatus(Status.PENDING)
                .build();

        // Assert that the exception is thrown
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            appointmentService.addAppointment(requestDto);
        });

        // Validate exception message
        assertEquals("Agent is not available.", exception.getMessage());

        // Verify that the repository was not called to save a new appointment
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    void updateAppointment_Success() {
        Appointment existingAppointment = appointments.get(0);
        AppointmentRequestDto requestDto = AppointmentRequestDto.builder()
                .customerId(2L)
                .agentId(1L)
                .propertyId(2L)
                .startDateTime(LocalDateTime.now().plusDays(2))
                .approvalStatus(Status.APPROVED)
                .build();

        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(existingAppointment));
        when(appointmentRepository.save(any(Appointment.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        Appointment updatedAppointment = appointmentService.updateAppointment(1L, requestDto);

        assertNotNull(updatedAppointment);
        assertEquals(2L, updatedAppointment.getCustomerId());
        assertEquals(Status.APPROVED, updatedAppointment.getApprovalStatus());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void updateAppointment_AgentNotAvailable_Failure() {
        // Given
        Long appointmentId = 1L;
        Long agentId = 1L;
        Long customerId = 1L;
        Long propertyId = 1L;
        LocalDateTime requestStartDateTime = LocalDateTime.now().plusDays(1);

        // Create a conflicting appointment for the agent
        LocalDateTime existingAppointmentStartDateTime = requestStartDateTime.minusMinutes(30); // Overlapping time
        LocalDateTime existingAppointmentEndDateTime = existingAppointmentStartDateTime.plusMinutes(60);

        Appointment existingAppointment = new Appointment();
        existingAppointment.setId(appointmentId);
        existingAppointment.setAgentId(agentId);
        existingAppointment.setStartDateTime(existingAppointmentStartDateTime);
        existingAppointment.setEndDateTime(existingAppointmentEndDateTime);

        // Mock repository to return the existing appointment
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(existingAppointment));
        when(appointmentRepository.findAll()).thenReturn(Collections.singletonList(existingAppointment));

        // Appointment request DTO
        AppointmentRequestDto requestDto = AppointmentRequestDto.builder()
                .customerId(customerId)
                .agentId(agentId)
                .propertyId(propertyId)
                .startDateTime(requestStartDateTime)
                .approvalStatus(Status.PENDING)
                .build();

        // Assert that the exception is thrown
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            appointmentService.updateAppointment(appointmentId, requestDto);
        });

        // Validate exception message
        assertEquals("Agent is not available.", exception.getMessage());

        // Verify that the repository was not called to save the appointment
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    void updateAppointment_NotFound() {
        AppointmentRequestDto requestDto = AppointmentRequestDto.builder()
                .customerId(2L)
                .agentId(1L)
                .propertyId(2L)
                .startDateTime(LocalDateTime.now().plusDays(2))
                .approvalStatus(Status.APPROVED)
                .build();

        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            appointmentService.updateAppointment(1L, requestDto);
        });

        assertEquals("Appointment not found.", exception.getMessage());
        verify(appointmentRepository, times(1)).findById(anyLong());
        verify(appointmentRepository, times(0)).save(any(Appointment.class));
    }

    @Test
    void deleteAppointment_Success() {
        // Given
        Long appointmentId = 1L;
        Appointment existingAppointment = appointments.get(0);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(existingAppointment));
        doNothing().when(appointmentRepository).delete(existingAppointment);
        doNothing().when(rabbitMQSender).notifyCustomerDeleteAppointment(existingAppointment);
        doNothing().when(rabbitMQSender).notifyAgentDeleteAppointment(existingAppointment);

        // When
        appointmentService.deleteAppointment(appointmentId);

        // Then
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(appointmentRepository, times(1)).delete(existingAppointment);
        verify(rabbitMQSender, times(1)).notifyCustomerDeleteAppointment(existingAppointment);
        verify(rabbitMQSender, times(1)).notifyAgentDeleteAppointment(existingAppointment);
    }

    @Test
    void deleteAppointment_NotFound() {
        // Given
        Long appointmentId = 1L;

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            appointmentService.deleteAppointment(appointmentId);
        });

        assertEquals("No appointment found", exception.getMessage());
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(appointmentRepository, never()).delete(any(Appointment.class));
        verify(rabbitMQSender, never()).notifyCustomerDeleteAppointment(any(Appointment.class));
        verify(rabbitMQSender, never()).notifyAgentDeleteAppointment(any(Appointment.class));
    }

    @Test
    void deleteAppointment_NotificationException() {
        // Given
        Long appointmentId = 1L;
        Appointment existingAppointment = appointments.get(0);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(existingAppointment));
        doNothing().when(appointmentRepository).delete(existingAppointment);
        doThrow(new ServiceOperationException("Notification failed")).when(rabbitMQSender).notifyCustomerDeleteAppointment(existingAppointment);
        // Make sure the other method is a no-op
        doNothing().when(rabbitMQSender).notifyAgentDeleteAppointment(existingAppointment);

        // When
        appointmentService.deleteAppointment(appointmentId);

        // Then
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(appointmentRepository, times(1)).delete(existingAppointment);
        verify(rabbitMQSender, times(1)).notifyCustomerDeleteAppointment(existingAppointment);
        verify(rabbitMQSender, times(1)).notifyAgentDeleteAppointment(existingAppointment);
    }

}