package ubb.graduation24.immopal.property_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyImagesDto {
    private Long id;
    private Long propertyId;
    private String imageUrl;
}
