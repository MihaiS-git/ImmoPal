export interface ParticipantDto {
  id?: string,
  personId: number,
  firstName: string,
  lastName: string,
  phoneNumber: string,
  dateOfBirth: Date,
  address: string,
  pictureUrl: string,
  userId: number,
  email: string,
  auctions?: string[]
}
