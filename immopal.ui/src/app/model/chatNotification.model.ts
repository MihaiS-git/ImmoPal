export class ChatNotification {
  constructor(
    public senderId: string,
    public recipientId: string,
    public content: string,
  ) { }
}
