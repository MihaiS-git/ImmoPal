package ubb.graduation24.immopal.agency_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgencyDto {

    private Long id;
    private String name;
    private AddressDto address;
    private String phone;
    private String email;
    private String description;
    private String logoUrl;
    private List<AgencyAgentDto> agents;
    private List<AgencyPropertyDto> properties;

}
