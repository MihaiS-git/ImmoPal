import { AddressDto } from "../dto/addressDto.model";
import { PropertyDetailsDto } from "../dto/propertyDetailsDto.model";

export class Property {
  constructor(
    public id: number,
    public transactionType: string,
    public propertyCategory: string,
    public propertyDetails: PropertyDetailsDto,
    public address: AddressDto,
    public price: number,
    public agentId: number,
    public propertyImages: string[] = []
  ) { }
 }
