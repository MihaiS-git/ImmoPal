package ubb.graduation24.immopal.appointment_service.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.appointment_service.domain.Appointment;
import ubb.graduation24.immopal.appointment_service.domain.Status;
import ubb.graduation24.immopal.appointment_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.appointment_service.exception.ServiceOperationException;
import ubb.graduation24.immopal.appointment_service.model.AppointmentRequestDto;
import ubb.graduation24.immopal.appointment_service.repository.AppointmentRepository;
import ubb.graduation24.immopal.grpc.PersonOuterClass;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class AppointmentServiceImpl implements IAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PersonClientService personClientService;
    private final RabbitMQSender rabbitMQSender;

    @Override
    public List<Appointment> getAppointments() {
        log.info("getAppointments() --- method entered");
        List<Appointment> appointments = appointmentRepository.findAll();
        if (appointments.isEmpty()) {
            log.info("getAppointments() --- method returned empty list");
            throw new ResourceNotFoundException("No appointments found");
        }
        log.info("getAppointments() size={}", appointments.size());
        return appointments;
    }

    @Override
    public Appointment getAppointment(Long id) {
        log.info("getAppointment --- method entered");
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (appointment.isPresent()) {
            log.info("getAppointment --- method returned");
            return appointment.get();
        } else {
            log.info("getAppointment --- method returned null");
            throw new ResourceNotFoundException("No appointment found");
        }
    }

    public List<Appointment> getAppointmentsByPersonId(Long personId) {
        log.info("getAppointmentsByPersonId() --- method entered");
        List<Appointment> appointments = appointmentRepository.findAll().stream()
                .filter(appointment -> {
                    return appointment.getAgentId().equals(personId);
                }).toList();
        log.info("getAppointmentsByPersonId() size={}", appointments.size());
        return appointments;
    }

    public boolean isPersonAvailable(Long personId, LocalDateTime appointmentDateTime) {
        log.info("isPersonAvailable() --- method entered");
        List<Appointment> appointments = getAppointmentsByPersonId(personId);

        for (Appointment appointment : appointments) {
            LocalDateTime existingStart = appointment.getStartDateTime();
            LocalDateTime existingEnd = appointment.getEndDateTime();
            LocalDateTime newAppointmentEnd = appointmentDateTime.plusMinutes(60);

            if (appointmentDateTime.isBefore(existingEnd) && newAppointmentEnd.isAfter(existingStart)) {
                log.info("Agent is not available.");
                return false;
            }
        }

        log.info("Agent is available.");
        return true;
    }

    @Override
    @Transactional
    public Appointment addAppointment(AppointmentRequestDto appointmentRequestDto) {
        log.info("addAppointment --- method entered");
        LocalDateTime startDateTime = appointmentRequestDto.getStartDateTime();
        Long agentId = appointmentRequestDto.getAgentId();
        boolean isAgentAvailable = isPersonAvailable(agentId, startDateTime);

        if (isAgentAvailable) {
            Appointment appointment = new Appointment();
            appointment.setCustomerId(appointmentRequestDto.getCustomerId());
            appointment.setAgentId(appointmentRequestDto.getAgentId());
            appointment.setPropertyId(appointmentRequestDto.getPropertyId());
            appointment.setStartDateTime(appointmentRequestDto.getStartDateTime());
            appointment.setEndDateTime(appointment.getStartDateTime().plusMinutes(60));
            appointment.setDateCreated(LocalDateTime.now());
            appointment.setLastModifDate(LocalDateTime.now());
            appointment.setApprovalStatus(Status.PENDING);
            Appointment savedAppointment = appointmentRepository.save(appointment);
            log.info("appointment saved={}", savedAppointment);

            try {
                rabbitMQSender.notifyCustomerNewAppointment(savedAppointment);
            } catch (ServiceOperationException e) {
                log.error("Error while sending new appointment notification: {}", e.getMessage());
            }

            try {
                rabbitMQSender.notifyAgentNewAppointment(savedAppointment);
            } catch (ServiceOperationException e) {
                log.error("Error while sending new appointment notification: {}", e.getMessage());
            }

            return savedAppointment;
        } else {
            log.info("Agent is not available.");
            throw new ResourceNotFoundException("Agent is not available.");
        }
    }

    @Override
    @Transactional
    public Appointment updateAppointment(Long id, AppointmentRequestDto appointmentRequestDto) {
        log.info("updateAppointment --- method entered");

        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
        if (appointmentOptional.isPresent()) {
            Appointment existingAppointment = appointmentOptional.get();

            LocalDateTime newStartDateTime = appointmentRequestDto.getStartDateTime();
            LocalDateTime currentStartDateTime = existingAppointment.getStartDateTime();
            Long agentId = appointmentRequestDto.getAgentId();

            // Only check availability if the startDateTime has changed
            boolean isAgentAvailable = newStartDateTime.equals(currentStartDateTime) || isPersonAvailable(agentId, newStartDateTime);

            if (isAgentAvailable) {
                existingAppointment.setCustomerId(appointmentRequestDto.getCustomerId());
                existingAppointment.setAgentId(appointmentRequestDto.getAgentId());
                existingAppointment.setPropertyId(appointmentRequestDto.getPropertyId());
                existingAppointment.setStartDateTime(newStartDateTime);
                existingAppointment.setEndDateTime(newStartDateTime.plusMinutes(60));
                existingAppointment.setLastModifDate(LocalDateTime.now());
                existingAppointment.setApprovalStatus(appointmentRequestDto.getApprovalStatus());
                Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
                log.info("updatedAppointment {}", updatedAppointment);
                return updatedAppointment;
            } else {
                log.info("updateAppointment --- method returned null");
                throw new ResourceNotFoundException("Agent is not available.");
            }
        } else {
            log.info("Appointment not found.");
            throw new ResourceNotFoundException("Appointment not found.");
        }
    }


    @Override
    @Transactional
    public void deleteAppointment(Long id) {
        log.info("deleteAppointment --- method entered");
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (appointment.isPresent()) {
            Appointment appointmentToDelete = appointment.get();
            appointmentRepository.delete(appointmentToDelete);
            try {
                rabbitMQSender.notifyCustomerDeleteAppointment(appointmentToDelete);
            } catch (ServiceOperationException e) {
                log.error("Error while sending new appointment notification: {}", e.getMessage());
            }
            try {
                rabbitMQSender.notifyAgentDeleteAppointment(appointmentToDelete);
            } catch (ServiceOperationException e) {
                log.error("Error while sending new appointment notification: {}", e.getMessage());
            }
            log.info("deleteAppointment with id={}", id);
        } else {
            log.info("deleteAppointment --- method returned null");
            throw new ResourceNotFoundException("No appointment found");
        }
    }
}
