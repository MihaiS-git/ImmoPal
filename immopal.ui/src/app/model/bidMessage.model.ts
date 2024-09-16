import { ParticipantDto } from "../dto/participantDto.model";
import { MessageType } from "./messageType.enum";

export class BidMessage {
  public static createNew(
    auctionRoomId: string,
    type: MessageType,
    sender: ParticipantDto,
    amount: number,
    timestamp: Date
  ): BidMessage {
    return new BidMessage(
      auctionRoomId,
      type,
      sender,
      amount,
      timestamp
    );
  }

  constructor(
    public auctionRoomId: string,
    public type: MessageType,
    public sender: ParticipantDto,
    public amount: number,
    public timestamp: Date,
    public id?: string
  ) { }
}
