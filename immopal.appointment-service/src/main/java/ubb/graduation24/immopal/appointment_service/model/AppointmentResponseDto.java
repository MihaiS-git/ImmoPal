package ubb.graduation24.immopal.appointment_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ubb.graduation24.immopal.appointment_service.domain.Status;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponseDto {
    private Long id;

    // Customer data
    private Long customerId;
    private String customerFirstName;
    private String customerLastName;
    private String customerPhoneNumber;
    private String customerPictureUrl;

    // Agent data
    private Long agentId;
    private String agentFirstName;
    private String agentLastName;
    private String agentPhoneNumber;
    private String agentPictureUrl;

    // Agency data
    private Long agencyId;
    private String agencyName;

    // Property data
    private Long propertyId;
    private String transactionType;
    private String propertyCategory;
    private PropertyDetailsDto propertyDetails;
    private AddressDto address;
    private Double price;
    private List<String> propertyImages;

    // Appointment data
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateCreated;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifDate;

    private Status approvalStatus;

}
