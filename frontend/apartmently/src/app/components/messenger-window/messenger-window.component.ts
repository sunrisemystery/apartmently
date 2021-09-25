import {Component, Input, OnInit} from '@angular/core';
import {Conversation} from 'src/app/common/conversation';

@Component({
  selector: 'app-messenger-window',
  templateUrl: './messenger-window.component.html',
  styleUrls: ['./messenger-window.component.css']
})
export class MessengerWindowComponent implements OnInit {

  @Input() conversation: Conversation;
  @Input() userId: number;
  @Input() username: string;

  constructor() {
  }

  ngOnInit(): void {

  }


}
