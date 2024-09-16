package ubb.graduation24.immopal.auction_service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ubb.graduation24.immopal.auction_service.domain.AuctionRoom;
import ubb.graduation24.immopal.auction_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.auction_service.model.CreateAuctionRoomRequest;
import ubb.graduation24.immopal.auction_service.repository.AuctionRoomRepository;
import ubb.graduation24.immopal.grpc.AgencyOuterClass;
import ubb.graduation24.immopal.grpc.PropertyOuterClass;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuctionRoomServiceImplTest {

    @Mock
    private AuctionRoomRepository auctionRoomRepository;

    @Mock
    private AgencyClientService agencyClientService;

    @Mock
    private PropertyClientService propertyClientService;

    @InjectMocks
    private AuctionRoomServiceImpl auctionRoomService;

    @Test
    void getAllAuctionRooms() {
        List<AuctionRoom> auctionRooms = new ArrayList<>();
        auctionRooms.add(new AuctionRoom());
        when(auctionRoomRepository.findAll()).thenReturn(auctionRooms);

        List<AuctionRoom> result = auctionRoomService.getAllAuctionRooms();

        assertEquals(1, result.size());
        verify(auctionRoomRepository, times(1)).findAll();
    }

    @Test
    void getAuctionRoomById() {
        AuctionRoom auctionRoom = new AuctionRoom();
        when(auctionRoomRepository.findById(anyString())).thenReturn(Optional.of(auctionRoom));

        AuctionRoom result = auctionRoomService.getAuctionRoomById("1");

        assertNotNull(result);
        verify(auctionRoomRepository, times(1)).findById("1");
    }

    @Test
    void getAuctionRoomById_NotFound() {
        when(auctionRoomRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auctionRoomService.getAuctionRoomById("1"));
        verify(auctionRoomRepository, times(1)).findById("1");
    }

    @Test
    void createAuctionRoom() {
        CreateAuctionRoomRequest request = new CreateAuctionRoomRequest();
        request.setPropertyId(1L);
        request.setStartDate(LocalDateTime.now());

        PropertyOuterClass.Property property = PropertyOuterClass.Property.newBuilder()
                .setAgentId(1L)
                .setPrice(1000.0)
                .build();
        AgencyOuterClass.Agency agency = AgencyOuterClass.Agency.newBuilder()
                .setId(1L)
                .build();

        when(propertyClientService.getProperty(anyLong())).thenReturn(property);
        when(agencyClientService.getAgencyByAgentId(anyLong())).thenReturn(agency);
        when(auctionRoomRepository.save(any(AuctionRoom.class))).thenReturn(new AuctionRoom());

        AuctionRoom result = auctionRoomService.createAuctionRoom(request);

        assertNotNull(result);
        verify(propertyClientService, times(1)).getProperty(1L);
        verify(agencyClientService, times(1)).getAgencyByAgentId(1L);
        verify(auctionRoomRepository, times(1)).save(any(AuctionRoom.class));
    }

    @Test
    void getAllAuctionRooms_RepositoryThrowsException() {
        when(auctionRoomRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> auctionRoomService.getAllAuctionRooms());

        assertEquals("Database error", thrown.getMessage());
        verify(auctionRoomRepository, times(1)).findAll();
    }

    @Test
    void getAuctionRoomById_RepositoryThrowsException() {
        when(auctionRoomRepository.findById(anyString())).thenThrow(new RuntimeException("Database error"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> auctionRoomService.getAuctionRoomById("1"));

        assertEquals("Database error", thrown.getMessage());
        verify(auctionRoomRepository, times(1)).findById("1");
    }

    @Test
    void createAuctionRoom_PropertyClientServiceThrowsException() {
        CreateAuctionRoomRequest request = new CreateAuctionRoomRequest();
        request.setPropertyId(1L);
        request.setStartDate(LocalDateTime.now());
        when(propertyClientService.getProperty(anyLong())).thenThrow(new RuntimeException("Property service error"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> auctionRoomService.createAuctionRoom(request));

        assertEquals("Property service error", thrown.getMessage());
        verify(propertyClientService, times(1)).getProperty(1L);
        verify(agencyClientService, never()).getAgencyByAgentId(anyLong());
        verify(auctionRoomRepository, never()).save(any(AuctionRoom.class));
    }

    @Test
    void createAuctionRoom_AgencyClientServiceThrowsException() {
        CreateAuctionRoomRequest request = new CreateAuctionRoomRequest();
        request.setPropertyId(1L);
        request.setStartDate(LocalDateTime.now());

        PropertyOuterClass.Property property = PropertyOuterClass.Property.newBuilder()
                .setAgentId(1L)
                .setPrice(1000.0)
                .build();
        when(propertyClientService.getProperty(anyLong())).thenReturn(property);
        when(agencyClientService.getAgencyByAgentId(anyLong())).thenThrow(new RuntimeException("Agency service error"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> auctionRoomService.createAuctionRoom(request));

        assertEquals("Agency service error", thrown.getMessage());
        verify(propertyClientService, times(1)).getProperty(1L);
        verify(agencyClientService, times(1)).getAgencyByAgentId(1L);
        verify(auctionRoomRepository, never()).save(any(AuctionRoom.class));
    }
}
