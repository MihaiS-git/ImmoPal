import { Status } from "../model/status.enum";

export class AppointmentRequestDto {
  customerId: number;
  agentId: number;
  propertyId: number;
  startDateTime: string;
  approvalStatus: Status;

  constructor(customerId: number, agentId: number,
    propertyId: number, startDateTime: string) {
    this.customerId = customerId;
    this.agentId = agentId;
    this.propertyId = propertyId;
    this.startDateTime = startDateTime;
    this.approvalStatus = Status.PENDING;
  }
}
