package ubb.graduation24.immopal.auction_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ubb.graduation24.immopal.auction_service.domain.Participant;
import ubb.graduation24.immopal.auction_service.model.ParticipantDto;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {

    ParticipantMapper INSTANCE = Mappers.getMapper(ParticipantMapper.class);

    ParticipantDto toDto(Participant participant);

    Participant toEntity(ParticipantDto participantDto);

}
