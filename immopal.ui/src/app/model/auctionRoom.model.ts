import { ParticipantDto } from "../dto/participantDto.model";
import { PropertyDto } from "../dto/propertyDto.model";
import { Agency } from "./agency.model";
import { AuctionRoomStatus } from "./auctionRoomStatus.enum";
import { BidMessage } from "./bidMessage.model";
import { Person } from "./person.model";

export class AuctionRoom {
  constructor(
    public id: string,
    public property: PropertyDto,
    public agent: Person,
    public agency: Agency,
    public startDate: Date,
    public endDate: Date,
    public lastModifiedDate: Date,
    public auctionStatus: AuctionRoomStatus,
    public maxBidAmount: number,
    public startBidAmount: number,
    public participants: ParticipantDto[],
    public winner: ParticipantDto,
    public winningBid: BidMessage
  ) { }
}

