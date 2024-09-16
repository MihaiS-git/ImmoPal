package ubb.graduation24.immopal.auction_service.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ubb.graduation24.immopal.auction_service.domain.AuctionRoom;
import ubb.graduation24.immopal.auction_service.domain.BidMessage;
import ubb.graduation24.immopal.auction_service.domain.Participant;
import ubb.graduation24.immopal.auction_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.auction_service.model.*;
import ubb.graduation24.immopal.auction_service.service.*;
import ubb.graduation24.immopal.grpc.AgencyOuterClass;
import ubb.graduation24.immopal.grpc.PersonOuterClass;
import ubb.graduation24.immopal.grpc.PropertyOuterClass;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AuctionRoomConverter {

    @Autowired
    private PropertyClientService propertyServiceClient;

    @Autowired
    private PersonClientService personClientService;

    @Autowired
    private AgencyClientService agencyClientService;

    @Autowired
    private IParticipantService participantService;

    public AuctionRoom toEntity(AuctionRoomDto auctionRoomDto) {
        if (auctionRoomDto == null) {
            throw new IllegalArgumentException("AuctionRoomDto cannot be null");
        }

        return AuctionRoom.builder()
                .id(auctionRoomDto.getId())
                .propertyId(auctionRoomDto.getProperty() != null ? auctionRoomDto.getProperty().getId() : null)
                .agencyId(auctionRoomDto.getAgency() != null ? auctionRoomDto.getAgency().getId() : null)
                .agentId(auctionRoomDto.getAgent() != null ? auctionRoomDto.getAgent().getId() : null)
                .startDate(auctionRoomDto.getStartDate())
                .endDate(auctionRoomDto.getEndDate())
                .lastModifiedDate(auctionRoomDto.getLastModifiedDate())
                .auctionStatus(auctionRoomDto.getAuctionStatus())
                .maxBidAmount(auctionRoomDto.getMaxBidAmount())
                .startBidAmount(auctionRoomDto.getStartBidAmount())
                .participantIds(auctionRoomDto.getParticipants() != null ?
                        auctionRoomDto.getParticipants().stream()
                                .map(p -> p.getId())
                                .collect(Collectors.toList())
                        : null)
                .winnerId(auctionRoomDto.getWinner() != null ? auctionRoomDto.getWinner().getId() : null)
                .winningBid(auctionRoomDto.getWinningBid())
                .build();
    }

    public AuctionRoomDto toDto(AuctionRoom auctionRoom) {
        if (auctionRoom == null) {
            throw new IllegalArgumentException("AuctionRoom cannot be null");
        }
        PropertyOuterClass.Property propertyOut = propertyServiceClient.getProperty(auctionRoom.getPropertyId());
        if (propertyOut == null) {
            throw new ResourceNotFoundException("Property not found");
        }
        PropertyDetailsDto propertyDetailsDto = PropertyDetailsDto.builder()
                .id(propertyOut.getPropertyDetails().getId())
                .description(propertyOut.getPropertyDetails().getDescription())
                .carpetArea(propertyOut.getPropertyDetails().getCarpetArea())
                .builtUpArea(propertyOut.getPropertyDetails().getBuiltUpArea())
                .comfortType(propertyOut.getPropertyDetails().getComfortType().toString())
                .floor(propertyOut.getPropertyDetails().getFloor())
                .structureType(propertyOut.getPropertyDetails().getStructureType().toString())
                .yearOfConstruction(propertyOut.getPropertyDetails().getYearOfConstruction())
                .bathNo(propertyOut.getPropertyDetails().getBathNo())
                .kitchenNo(propertyOut.getPropertyDetails().getKitchenNo())
                .bedroomNo(propertyOut.getPropertyDetails().getBedroomNo())
                .parkingNo(propertyOut.getPropertyDetails().getParkingNo())
                .balcony(propertyOut.getPropertyDetails().hasBalcony())
                .terrace(propertyOut.getPropertyDetails().hasTerrace())
                .swimmingPool(propertyOut.getPropertyDetails().hasSwimmingPool())
                .energeticCertificate(propertyOut.getPropertyDetails().getEnergeticCertificate().toString())
                .build();

        AddressDto addressDto = AddressDto.builder()
                .id(propertyOut.getAddress().getId())
                .country(propertyOut.getAddress().getCountry())
                .state(propertyOut.getAddress().getState())
                .city(propertyOut.getAddress().getCity())
                .neighborhood(propertyOut.getAddress().getNeighborhood())
                .street(propertyOut.getAddress().getStreet())
                .streetNo(propertyOut.getAddress().getStreetNo())
                .latitude(propertyOut.getAddress().getLatitude())
                .longitude(propertyOut.getAddress().getLongitude())
                .build();

        List<PropertyOuterClass.PropertyImage> theImages = propertyOut.getPropertyImagesList();
        List<PropertyImagesDto> theImagesDto = theImages.stream()
                .map(i -> PropertyImagesDto.builder()
                        .id(i.getId())
                        .imageUrl(i.getImageUrl())
                        .build()).toList();

        PropertyDto propertyDto = PropertyDto.builder()
                .id(propertyOut.getId())
                .transactionType(propertyOut.getTransactionType().toString())
                .propertyCategory(propertyOut.getPropertyCategory().toString())
                .propertyDetails(propertyDetailsDto)
                .address(addressDto)
                .price(propertyOut.getPrice())
                .agentId(propertyOut.getAgentId())
                .propertyImages(theImagesDto)
                .build();

        Long agentId = propertyOut.getAgentId();
        PersonOuterClass.Person agentOut = personClientService.getPerson(agentId);
        PersonDto agentDto = PersonDto.builder()
                .id(agentOut.getId())
                .firstName(agentOut.getFirstName())
                .lastName(agentOut.getLastName())
                .phoneNumber(agentOut.getPhoneNumber())
                .dateOfBirth(LocalDate.parse(agentOut.getDateOfBirth()))
                .address(agentOut.getAddress())
                .pictureUrl(agentOut.getPictureUrl())
                .userId(agentOut.getUserId())
                .build();

        AgencyOuterClass.Agency agencyOut = agencyClientService.getAgencyByAgentId(agentId);
        AgencyOuterClass.Address addressOut = agencyOut.getAddress();
        AddressDto agencyAddressDto = AddressDto.builder()
                .id(addressOut.getId())
                .country(addressOut.getCountry())
                .state(addressOut.getState())
                .city(addressOut.getCity())
                .neighborhood(addressOut.getNeighborhood())
                .street(addressOut.getStreet())
                .streetNo(String.valueOf(addressOut.getStreetNo()))
                .latitude(addressOut.getLatitude())
                .longitude(addressOut.getLongitude())
                .build();

        List<AgencyAgentDto> agentsDtoList = agencyOut.getAgentsList().stream()
                .map(aOut -> AgencyAgentDto.builder()
                        .id(aOut.getId())
                        .agencyId(aOut.getAgencyId())
                        .agentId(aOut.getAgentId())
                        .build())
                .toList();

        List<AgencyPropertyDto> propertyDtoList = agencyOut.getPropertiesList().stream()
                .map(pOut -> AgencyPropertyDto.builder()
                        .id(pOut.getId())
                        .agencyId(pOut.getAgencyId())
                        .propertyId(pOut.getPropertyId())
                        .build()
                ).toList();

        AgencyDto agencyDto = AgencyDto.builder()
                .id(agencyOut.getId())
                .name(agencyOut.getName())
                .address(agencyAddressDto)
                .phone(agencyOut.getPhone())
                .email(agencyOut.getEmail())
                .description(agencyOut.getDescription())
                .logoUrl(agencyOut.getLogoUrl())
                .agents(agentsDtoList)
                .properties(propertyDtoList)
                .build();

        log.info("Converter LOGO: {}", agencyDto.getLogoUrl());

        ParticipantDto winnerDto = null;
        String winnerId = auctionRoom.getWinnerId();
        if (winnerId != null) {
            try {
                Participant participant = participantService.getParticipantById(winnerId);
                winnerDto = ParticipantDto.builder()
                        .id(participant.getId())
                        .personId(participant.getPersonId())
                        .firstName(participant.getFirstName())
                        .lastName(participant.getLastName())
                        .phoneNumber(participant.getPhoneNumber())
                        .dateOfBirth(participant.getDateOfBirth())
                        .address(participant.getAddress())
                        .pictureUrl(participant.getPictureUrl())
                        .userId(participant.getUserId())
                        .email(participant.getEmail())
                        .auctions(participant.getAuctions())
                        .build();
            } catch (ResourceNotFoundException e) {
                log.error("Winner not found with ID: {}", winnerId, e);
                throw new ResourceNotFoundException("Winner not found with ID: " + winnerId);
            }
        }

        List<ParticipantDto> participantDtos = auctionRoom.getParticipantIds() != null ?
                auctionRoom.getParticipantIds().stream()
                        .map(id -> {
                            try {
                                Participant p = participantService.getParticipantById(id);
                                return ParticipantDto.builder()
                                        .id(p.getId())
                                        .personId(p.getPersonId())
                                        .firstName(p.getFirstName())
                                        .lastName(p.getLastName())
                                        .phoneNumber(p.getPhoneNumber())
                                        .dateOfBirth(p.getDateOfBirth())
                                        .address(p.getAddress())
                                        .pictureUrl(p.getPictureUrl())
                                        .userId(p.getUserId())
                                        .email(p.getEmail())
                                        .auctions(p.getAuctions())
                                        .build();
                            } catch (ResourceNotFoundException e) {
                                log.error("Participant not found with ID: {}", id, e);
                                throw new ResourceNotFoundException("Participant not found with ID: " + id);
                            }
                        })
                        .collect(Collectors.toList())
                : List.of();

        double maxBidAmt = auctionRoom.getMaxBidAmount() == null ? auctionRoom.getStartBidAmount() : auctionRoom.getMaxBidAmount();
        BidMessage wb = auctionRoom.getWinningBid() != null ? auctionRoom.getWinningBid() : null;

        return AuctionRoomDto.builder()
                .id(auctionRoom.getId())
                .property(propertyDto)
                .agent(agentDto)
                .agency(agencyDto)
                .startDate(auctionRoom.getStartDate())
                .endDate(auctionRoom.getEndDate())
                .lastModifiedDate(auctionRoom.getLastModifiedDate())
                .auctionStatus(auctionRoom.getAuctionStatus())
                .maxBidAmount(maxBidAmt)
                .startBidAmount(auctionRoom.getStartBidAmount())
                .participants(participantDtos)
                .winner(winnerDto)
                .winningBid(wb)
                .bids(auctionRoom.getBids())
                .build();
    }
}
