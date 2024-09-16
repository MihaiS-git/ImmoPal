package ubb.graduation24.immopal.property_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {

    private Long id;
    private String country;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String streetNo;
    private Double latitude;
    private Double longitude;

}
