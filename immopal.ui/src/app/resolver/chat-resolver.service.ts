import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { ChatService } from '../service/chat.service';
import { Observable } from 'rxjs';
import { ChatUser } from '../model/chatUser.model';

@Injectable({
  providedIn: 'root'
})
export class ChatResolverService implements Resolve<ChatUser> {

  constructor(private chatService: ChatService) { }

  resolve(): Observable<any> {
    return this.chatService.fetchChatUsers();
  }
}

