import { AddressDto } from "./addressDto.model";
import { PropertyDetailsDto } from "./propertyDetailsDto.model";
import { PropertyImageDto } from "./propertyImageDto.model";

export interface PropertyDto{
  id: number,
  transactionType: string,
  propertyCategory: string,
  propertyDetails: PropertyDetailsDto,
  address: AddressDto,
  price: number,
  agentId: number,
  propertyImages: PropertyImageDto[];
 }
