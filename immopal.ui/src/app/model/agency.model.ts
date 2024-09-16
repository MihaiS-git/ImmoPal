import { AddressDto } from "../dto/addressDto.model";

export class Agency {
  constructor(
    public id: number,
    public name: string,
    public address: AddressDto,
    public phone: string,
    public email: string,
    public description: string,
    public logoUrl: string,
    public agentsId: number[] = [],
    public propertiesId: number[] = []
  ) { }
}
