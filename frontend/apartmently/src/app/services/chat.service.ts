import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Conversation} from '../common/conversation';
import {ConversationList} from '../common/conversation-list';
import {AuthenticationService} from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private baseUrl = 'http://localhost:8080/api/conversation';

  constructor(private httpClient: HttpClient, private authService: AuthenticationService) {
  }

  getConversationById(id: number): Observable<Conversation> {
    const url = `${this.baseUrl}/${id}`;
    return this.httpClient.get<Conversation>(url);
  }

  getList(): Observable<ConversationList[]> {
    const url = `${this.baseUrl}/user/${this.authService.currentUserValue.id}`;
    return this.httpClient.get<ConversationList[]>(url);
  }

  createConversation(userId: number): Observable<any> {
    const url = `${this.baseUrl}/user/${this.authService.currentUserValue.id}/${userId}`;
    return this.httpClient.post(url, null);
  }

  sendMessage(convId: number, content: string): Observable<any> {
    const url = `${this.baseUrl}/message/${convId}/${this.authService.currentUserValue.id}`;
    const contentMap = {};
    contentMap['content'] = content;
    return this.httpClient.post(url, contentMap);
  }

}
