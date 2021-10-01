import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
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

  @Output() goBack = new EventEmitter<boolean>(false);

  constructor() {
  }

  ngOnInit(): void {
    this.toBottom();
  }

  toBottom(): void {
    let el = document.getElementById('test');
    el.scrollIntoView();
  }

  emitGoBack(): void {
    this.goBack.emit(true);
  }
}
