package ubb.graduation24.immopal.auction_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.auction_service.domain.AuctionRoom;
import ubb.graduation24.immopal.auction_service.domain.AuctionRoomStatus;
import ubb.graduation24.immopal.auction_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.auction_service.model.*;
import ubb.graduation24.immopal.auction_service.repository.AuctionRoomRepository;
import ubb.graduation24.immopal.grpc.AgencyOuterClass;
import ubb.graduation24.immopal.grpc.PropertyOuterClass;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuctionRoomServiceImpl implements IAuctionRoomService {

    private final AuctionRoomRepository auctionRoomRepository;
    private final AgencyClientService agencyClientService;
    private final PropertyClientService propertyClientService;

    @Override
    public List<AuctionRoom> getAllAuctionRooms() {
        log.info("getAllAuctionRooms() --- method entered");
        List<AuctionRoom> rooms = auctionRoomRepository.findAll();
        log.info("getAllAuctionRooms() --- size={}", rooms.size());
        return rooms;
    }

    @Override
    public AuctionRoom getAuctionRoomById(String id) {
        log.info("getAuctionRoomById() --- method entered");
        return auctionRoomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auction room not found with id: " + id));
    }

    @Override
    public AuctionRoom createAuctionRoom(CreateAuctionRoomRequest requestBody) {
        log.info("createAuctionRoom() --- method entered with requestBody: {}", requestBody);

        try {
            PropertyOuterClass.Property propertyOut = propertyClientService.getProperty(requestBody.getPropertyId());
            AgencyOuterClass.Agency agencyOut = agencyClientService.getAgencyByAgentId(propertyOut.getAgentId());

            log.info("createAuctionRoom getAgencyByAgentId: {}", agencyOut.toString());

            Long agencyId = agencyOut.getId();

            AuctionRoom auctionRoom = new AuctionRoom();
            auctionRoom.setPropertyId(requestBody.getPropertyId());
            auctionRoom.setAgencyId(agencyId);
            auctionRoom.setAgentId(propertyOut.getAgentId());
            auctionRoom.setStartDate(requestBody.getStartDate());
            auctionRoom.setEndDate(requestBody.getStartDate().plusDays(30));
            auctionRoom.setLastModifiedDate(LocalDateTime.now(ZoneId.of("Europe/Bucharest")));
            auctionRoom.setAuctionStatus(AuctionRoomStatus.INACTIVE);
            auctionRoom.setStartBidAmount(propertyOut.getPrice());
            auctionRoom.setMaxBidAmount(propertyOut.getPrice());
            log.info("Building auction room: {}", auctionRoom);

            AuctionRoom createdAuctionRoom = auctionRoomRepository.save(auctionRoom);
            log.info("createAuctionRoom() --- created auction room with Id: {}", createdAuctionRoom.getId());

            return createdAuctionRoom;
        } catch (Exception e) {
            log.error("createAuctionRoom() --- error occurred: {}", e.getMessage(), e);
            throw e;
        }
    }
}
