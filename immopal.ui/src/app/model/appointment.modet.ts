import { Status } from "./status.enum";

export class Appointment {
  constructor(
    public appointmentId: number,
    public customerId: number,
    public agentId: number,
    public propertyId: number,
    public startDateTime: Date,
    public endDateTime: Date,
    public dateCreated: Date,
    public lastModifDate: Date,
    public approvalStatus: Status,
  ) { }
}
