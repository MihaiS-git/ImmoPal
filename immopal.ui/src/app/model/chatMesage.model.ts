export class ChatMessage {
  constructor(
    public id: string,
    public chatId : string,
    public senderId: string,
    public content: string,
    public timestamp: Date
  ) { }
}
