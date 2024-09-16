import { Status } from '../model/status.enum';
import { AddressDto } from './addressDto.model';
import { PropertyDetailsDto } from './propertyDetailsDto.model';

export interface AppointmentResponseDto {
  id: number,

  // Customer data
  customerId: number,
  customerFirstName: string,
  customerLastName: string,
  customerPhoneNumber: string,
  customerPictureUrl: string,

  // Agent data
  agentId: number,
  agentFirstName: string,
  agentLastName: string,
  agentPhoneNumber: string,
  agentPictureUrl: string,

  // Agency data
  agencyId: number,
  agencyName: string,

  // Property data
  propertyId: number,
  transactionType: string,
  propertyCategory: string,
  propertyDetails: PropertyDetailsDto;
  address: AddressDto;
  price: number,
  propertyImages: string[],

  // Appointment data
  startDateTime: Date,
  endDateTime: Date,
  dateCreated: Date,
  lastModifDate: Date,

  approvalStatus: Status

}
