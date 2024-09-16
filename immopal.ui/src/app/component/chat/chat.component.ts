import { Component } from '@angular/core';
import { ChatUser } from '../../model/chatUser.model';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css'
})
export class ChatComponent {
  chatUsers: ChatUser[];

  constructor() { }

}
