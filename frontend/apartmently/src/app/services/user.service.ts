import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { UserInfo } from '../common/user-info';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8080/api/user';
  private userInfo: UserInfo;
  public getImage = new Subject<string>();

  constructor(private httpClient: HttpClient) { }

  getUserInfo(id: number): Observable<UserInfo> {
    const url = `${this.baseUrl}/${id}`;
    return this.httpClient.get<UserInfo>(url);

  }

  saveUserInfo(user: UserInfo): Observable<any> {
    const url = `${this.baseUrl}/details`;
    return this.httpClient.post<UserInfo>(url, user);
  }


}


