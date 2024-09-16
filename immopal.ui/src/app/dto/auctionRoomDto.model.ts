import { Agency } from "../model/agency.model"
import { AuctionRoomStatus } from "../model/auctionRoomStatus.enum"
import { BidMessage } from "../model/bidMessage.model"
import { ParticipantDto } from "./participantDto.model"
import { PersonDto } from "./personDto.model"
import { PropertyDto } from "./propertyDto.model"

export interface AuctionRoomDto {
  id: string,
  property: PropertyDto,
  agent: PersonDto,
  agency: Agency,
  startDate: Date,
  endDate: Date,
  lastModifiedDate: Date,
  auctionStatus: AuctionRoomStatus,
  maxBidAmount: number,
  startBidAmount: number,
  participants: ParticipantDto[],
  winner: ParticipantDto,
  winningBid: BidMessage,
  bids: BidMessage[]
}
