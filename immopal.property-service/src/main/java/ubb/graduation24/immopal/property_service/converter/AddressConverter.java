package ubb.graduation24.immopal.property_service.converter;

import org.springframework.stereotype.Component;
import ubb.graduation24.immopal.property_service.domain.Address;
import ubb.graduation24.immopal.property_service.model.AddressDto;

@Component
public class AddressConverter {
    public static AddressDto toDto(Address entity) {
        if (entity == null) {
            return null;
        }

        AddressDto dto = AddressDto.builder()
                .id(entity.getId())
                .country(entity.getCountry())
                .state(entity.getState())
                .city(entity.getCity())
                .neighborhood(entity.getNeighborhood())
                .street(entity.getStreet())
                .streetNo(entity.getStreetNo())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .build();

        return dto;
    }

    public static Address toEntity(AddressDto dto) {
        if (dto == null) {
            return null;
        }

        Address entity = Address.builder()
                .id(dto.getId())
                .country(dto.getCountry())
                .state(dto.getState())
                .city(dto.getCity())
                .neighborhood(dto.getNeighborhood())
                .street(dto.getStreet())
                .streetNo(dto.getStreetNo())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();

        return entity;
    }
}