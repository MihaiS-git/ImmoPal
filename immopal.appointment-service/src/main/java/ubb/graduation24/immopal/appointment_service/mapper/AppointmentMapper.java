package ubb.graduation24.immopal.appointment_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ubb.graduation24.immopal.appointment_service.domain.Appointment;
import ubb.graduation24.immopal.appointment_service.model.AppointmentDto;

@Mapper(componentModel="spring")
public interface AppointmentMapper {

    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    AppointmentDto appointmentToAppointmentDto(Appointment appointment);

    Appointment appointmentDtoToAppointment(AppointmentDto appointmentDto);

}
