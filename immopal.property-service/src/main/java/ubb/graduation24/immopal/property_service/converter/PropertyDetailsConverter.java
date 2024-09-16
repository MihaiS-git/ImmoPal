package ubb.graduation24.immopal.property_service.converter;

import org.springframework.stereotype.Component;
import ubb.graduation24.immopal.property_service.domain.ComfortType;
import ubb.graduation24.immopal.property_service.domain.EnergeticCertificate;
import ubb.graduation24.immopal.property_service.domain.PropertyDetails;
import ubb.graduation24.immopal.property_service.domain.StructureType;
import ubb.graduation24.immopal.property_service.model.PropertyDetailsDto;

@Component
public class PropertyDetailsConverter {
    public static PropertyDetailsDto toDto(PropertyDetails entity) {
        if (entity == null) {
            return null;
        }

        PropertyDetailsDto dto = PropertyDetailsDto.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .carpetArea(entity.getCarpetArea())
                .builtUpArea(entity.getBuiltUpArea())
                .comfortType(entity.getComfortType().toString())
                .floor(entity.getFloor())
                .structureType(entity.getStructureType().toString())
                .yearOfConstruction(entity.getYearOfConstruction())
                .bathNo(entity.getBathNo())
                .kitchenNo(entity.getKitchenNo())
                .bedroomNo(entity.getBedroomNo())
                .parkingNo(entity.getParkingNo())
                .balcony(entity.isBalcony())
                .terrace(entity.isTerrace())
                .swimmingPool(entity.isSwimmingPool())
                .energeticCertificate(entity.getEnergeticCertificate().toString())
                .build();

        return dto;
    }

    public static PropertyDetails toEntity(PropertyDetailsDto dto) {
        if (dto == null) {
            return null;
        }

        PropertyDetails entity = PropertyDetails.builder()
                .id(dto.getId())
                .description(dto.getDescription())
                .carpetArea(dto.getCarpetArea())
                .builtUpArea(dto.getBuiltUpArea())
                .comfortType(ComfortType.valueOf(dto.getComfortType()))
                .floor(dto.getFloor())
                .structureType(StructureType.valueOf(dto.getStructureType()))
                .yearOfConstruction(dto.getYearOfConstruction())
                .bathNo(dto.getBathNo())
                .kitchenNo(dto.getKitchenNo())
                .bedroomNo(dto.getBedroomNo())
                .parkingNo(dto.getParkingNo())
                .balcony(dto.isBalcony())
                .terrace(dto.isTerrace())
                .swimmingPool(dto.isSwimmingPool())
                .energeticCertificate(EnergeticCertificate.valueOf(dto.getEnergeticCertificate()))
                .build();

        return entity;
    }
}
