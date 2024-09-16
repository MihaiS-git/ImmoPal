package ubb.graduation24.immopal.auction_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.graduation24.immopal.auction_service.converter.AuctionRoomConverter;
import ubb.graduation24.immopal.auction_service.domain.AuctionRoom;
import ubb.graduation24.immopal.auction_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.auction_service.mapper.ParticipantMapper;
import ubb.graduation24.immopal.auction_service.model.*;
import ubb.graduation24.immopal.auction_service.service.IAuctionRoomService;
import ubb.graduation24.immopal.auction_service.service.IParticipantService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auctionRooms")
public class AuctionController {

    private final IAuctionRoomService auctionRoomService;
    private final AuctionRoomConverter auctionRoomConverter;
    private final IParticipantService participantService;
    private final ParticipantMapper participantMapper;


    @GetMapping("/{auctionRoomId}")
    public ResponseEntity<AuctionRoomDto> getAuctionRoomById(@PathVariable String auctionRoomId) {
        try {
            AuctionRoom auctionRoom = auctionRoomService.getAuctionRoomById(auctionRoomId);
            AuctionRoomDto auctionRoomDto = auctionRoomConverter.toDto(auctionRoom);
            return new ResponseEntity<>(auctionRoomDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuctionRoomDto>> getAllAuctionRooms() {
        try {
            List<AuctionRoom> auctionRooms = auctionRoomService.getAllAuctionRooms();
            if (auctionRooms.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<AuctionRoomDto> auctionRoomDtos = auctionRooms.stream()
                    .map(auctionRoomConverter::toDto)
                    .toList();
            log.info("getAllAuctionRooms: {}", auctionRoomDtos);
            return new ResponseEntity<>(auctionRoomDtos, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/participant/{email}")
    public ResponseEntity<ParticipantDto> getParticipantByEmail(@PathVariable String email) {
        try {
            ParticipantDto participantDto = participantMapper.toDto(participantService.getParticipantByEmail(email));
            return new ResponseEntity<>(participantDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<AuctionRoomDto> createAuction(@RequestBody CreateAuctionRoomRequest requestBody) {
        try {
            AuctionRoom createdAuctionRoom = auctionRoomService.createAuctionRoom(requestBody);
            log.info("Controller Created auction room: {}", createdAuctionRoom);
            AuctionRoomDto auctionRoomDto = auctionRoomConverter.toDto(createdAuctionRoom);
            log.info("Controller Created auction room DTO: {}", auctionRoomDto);
            return new ResponseEntity<>(auctionRoomDto, HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("Controller AuctionRoom not created.", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
