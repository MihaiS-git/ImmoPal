package ubb.graduation24.immopal.appointment_service.service;

import ubb.graduation24.immopal.appointment_service.domain.Appointment;
import ubb.graduation24.immopal.appointment_service.model.AppointmentRequestDto;

import java.util.List;

public interface IAppointmentService {
    List<Appointment> getAppointments();
    Appointment getAppointment(Long id);
    Appointment addAppointment(AppointmentRequestDto appointmentRequestDto);
    Appointment updateAppointment(Long id, AppointmentRequestDto appointmentRequestDto);
    void deleteAppointment(Long id);
}
