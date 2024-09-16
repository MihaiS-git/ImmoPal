package ubb.graduation24.immopal.auction_service.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantRegistrationRequestDto {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Person ID is required")
    private Long personId;

    @NotNull(message = "Auction Room ID is required")
    private String auctionRoomId;
}
