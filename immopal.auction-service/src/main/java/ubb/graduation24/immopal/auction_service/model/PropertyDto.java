package ubb.graduation24.immopal.auction_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyDto {
    private Long id;
    private String transactionType;
    private String propertyCategory;
    private PropertyDetailsDto propertyDetails;
    private AddressDto address;
    private Double price;
    private Long agentId;
    private List<PropertyImagesDto> propertyImages;
}
