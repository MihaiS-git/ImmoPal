package ubb.graduation24.immopal.auction_service.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAuctionRoomRequest {
    @NotNull(message = "Property ID is required")
    private Long propertyId;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;
}
