package ubb.graduation24.immopal.property_service.converter;

import org.springframework.stereotype.Component;
import ubb.graduation24.immopal.property_service.domain.PropertyImages;
import ubb.graduation24.immopal.property_service.model.PropertyImagesDto;

@Component
public class PropertyImagesConverter {
    public static PropertyImagesDto toDto(PropertyImages entity) {
        if (entity == null) {
            return null;
        }

        PropertyImagesDto dto = PropertyImagesDto.builder()
                .id(entity.getId())
                .propertyId(entity.getProperty().getId())
                .imageUrl(entity.getImageUrl())
                .build();

        return dto;
    }

    public static PropertyImages toEntity(PropertyImagesDto dto) {
        if (dto == null) {
            return null;
        }
        PropertyImages entity = PropertyImages.builder()
                .id(dto.getId())
                .imageUrl(dto.getImageUrl())
                .build();
        return entity;
    }
}
