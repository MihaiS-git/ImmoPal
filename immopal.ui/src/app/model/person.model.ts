export class Person {
  constructor(
    public id: number,
    public firstName: string,
    public lastName: string,
    public phoneNumber: string,
    public dateOfBirth: Date,
    public address: string,
    public pictureUrl: string,
    public userId: number,
    public propertiesIds: number[] = [],
    public appointmentIds: number[] = [],
    public bidIds: number[] = []
  ) { }
}
