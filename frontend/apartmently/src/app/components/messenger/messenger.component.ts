import {Component, OnInit} from '@angular/core';
import {Conversation} from 'src/app/common/conversation';
import {ConversationList} from 'src/app/common/conversation-list';
import {AuthenticationService} from 'src/app/services/authentication.service';
import {ChatService} from 'src/app/services/chat.service';

@Component({
  selector: 'app-messenger',
  templateUrl: './messenger.component.html',
  styleUrls: ['./messenger.component.css']
})
export class MessengerComponent implements OnInit {

  conversations: ConversationList[];
  conversation: Conversation = new Conversation();
  username: string;
  userId: number;
  conversationId: number;

  constructor(private chatService: ChatService, private authService: AuthenticationService) {
  }

  ngOnInit(): void {

    this.userId = this.authService.currentUserValue.id;

  }


  handleConversationId(id: number): void {
    this.conversationId = id;
    this.chatService.getConversationById(id).subscribe((val) => {
      this.conversation = val;
    });
  }

  handleName(name: string): void {
    this.username = name;
  }

}
