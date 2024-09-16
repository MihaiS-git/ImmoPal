import { AddressDto } from '../dto/addressDto.model';
import { PropertyDetailsDto } from '../dto/propertyDetailsDto.model';
import { PropertyDto } from '../dto/propertyDto.model';
import { Property } from './property.model';

export class AppointmentItem {
  public id: number;
  public transactionType: string;
  public propertyCategory: string;
  public propertyDetails: PropertyDetailsDto;
  public address: AddressDto;
  public price: number;
  public agentId: number;
  public propertyImages: string[];

  constructor(property: PropertyDto) {
    this.id = property.id;
    this.transactionType = property.transactionType;
    this.propertyCategory = property.propertyCategory;
    this.propertyDetails = property.propertyDetails;
    this.address = property.address;
    this.price = property.price;
    this.agentId = property.agentId;
    this.propertyImages = property.propertyImages.map(i => i.imageUrl);
  }

}
