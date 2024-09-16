package ubb.graduation24.immopal.chat_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConnectUserRequest {
    private String email;
    private String fullName;
    private String status;
    private String pictureUrl;
}
