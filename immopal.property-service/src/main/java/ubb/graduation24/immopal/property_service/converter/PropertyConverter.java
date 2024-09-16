package ubb.graduation24.immopal.property_service.converter;

import org.springframework.stereotype.Component;
import ubb.graduation24.immopal.property_service.domain.Property;
import ubb.graduation24.immopal.property_service.domain.PropertyCategory;
import ubb.graduation24.immopal.property_service.domain.PropertyImages;
import ubb.graduation24.immopal.property_service.domain.TransactionType;
import ubb.graduation24.immopal.property_service.model.PropertyDto;
import ubb.graduation24.immopal.property_service.model.PropertyImagesDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PropertyConverter {
    public static PropertyDto toDto(Property entity) {
        if (entity == null) {
            return null;
        }

        List<String> imageUrls = entity.getPropertyImages().stream()
                .map(PropertyImages::getImageUrl)
                .collect(Collectors.toList());

        PropertyDto dto = PropertyDto.builder()
                .id(entity.getId())
                .transactionType(entity.getTransactionType().toString())
                .propertyCategory(entity.getPropertyCategory().toString())
                .propertyDetails(PropertyDetailsConverter.toDto(entity.getPropertyDetails()))
                .address(AddressConverter.toDto(entity.getAddress()))
                .price(entity.getPrice())
                .agentId(entity.getAgentId())
                .propertyImages(imageUrls)
                .build();

        return dto;
    }

    public static Property toEntity(PropertyDto dto) {
        if (dto == null) {
            return null;
        }

        Property entity = Property.builder()
                .id(dto.getId())
                .transactionType(TransactionType.valueOf(dto.getTransactionType()))
                .propertyCategory(PropertyCategory.valueOf(dto.getPropertyCategory()))
                .propertyDetails(PropertyDetailsConverter.toEntity(dto.getPropertyDetails()))
                .address(AddressConverter.toEntity(dto.getAddress()))
                .price(dto.getPrice())
                .agentId(dto.getAgentId())
                .build();

        List<PropertyImages> imagesProperty = dto.getPropertyImages().stream()
                .map(imageUrl -> {
                    PropertyImagesDto imageDto = PropertyImagesDto.builder()
                            .id(null) // id is set to null for new images
                            .propertyId(entity.getId())
                            .imageUrl(imageUrl)
                            .build();
                    PropertyImages image = PropertyImagesConverter.toEntity(imageDto);
                    image.setProperty(entity);
                    return image;
                })
                .collect(Collectors.toList());
        entity.setPropertyImages(imagesProperty);

        return entity;
    }
}