import { ChatStatus } from "./chatStatus.enum";

export class ChatUser {
  constructor(
    public email: string,
    public fullName: string,
    public status: ChatStatus,
    public pictureUrl: string
  ) { }

}
