package ubb.graduation24.immopal.appointment_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ubb.graduation24.immopal.appointment_service.domain.Status;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentRequestDto {
    private Long customerId;
    private Long agentId;
    private Long propertyId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDateTime;

    private Status approvalStatus;
}
