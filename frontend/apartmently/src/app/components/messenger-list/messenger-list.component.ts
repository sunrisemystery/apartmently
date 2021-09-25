import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ConversationList} from 'src/app/common/conversation-list';
import {AuthenticationService} from 'src/app/services/authentication.service';
import {ChatService} from 'src/app/services/chat.service';

@Component({
  selector: 'app-messenger-list',
  templateUrl: './messenger-list.component.html',
  styleUrls: ['./messenger-list.component.css']
})
export class MessengerListComponent implements OnInit {

  conversations: ConversationList[] = [];
  @Output() clickedConversation = new EventEmitter<number>();
  @Output() clickedName = new EventEmitter<string>();


  constructor(private chatService: ChatService, private authService: AuthenticationService) {
  }

  ngOnInit(): void {
    this.chatService.getList().subscribe((val) => {
      this.conversations = val;
    });
  }

  emitValue(id: number): void {
    this.clickedConversation.emit(id);
    this.conversations.forEach(conv => {
      if (conv.id === id) {
        if (conv.user1Id !== this.authService.currentUserValue.id) {
          this.clickedName.emit(conv.user1Name);
        } else {
          this.clickedName.emit(conv.user2Name);
        }
      }
    });
  }

}
