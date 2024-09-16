package ubb.graduation24.immopal.appointment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import ubb.graduation24.immopal.appointment_service.domain.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>,
        QueryByExampleExecutor<Appointment>,
        PagingAndSortingRepository<Appointment, Long> {
}
