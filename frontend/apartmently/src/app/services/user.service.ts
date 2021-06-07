import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {UserInfo} from '../common/user-info';
import {UserTile} from '../common/user-tile';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8080/api/user';
  public getImage = new Subject<string>();


  constructor(private httpClient: HttpClient) {
  }

  getUserInfo(id: number): Observable<UserInfo> {
    const url = `${this.baseUrl}/${id}`;
    return this.httpClient.get<UserInfo>(url);
  }

  saveUserInfo(user: UserInfo): Observable<any> {
    const url = `${this.baseUrl}/details`;
    return this.httpClient.post<UserInfo>(url, user);
  }

  updateUserInfo(user: UserInfo): Observable<any> {
    const url = `${this.baseUrl}/update`;
    return this.httpClient.put<UserInfo>(url, user);
  }

  getUsersPaginate(thePage: number, thePageSize: number): Observable<GetResponseUserTiles> {
    const url = `${this.baseUrl}/all?page=${thePage}&size=${thePageSize}`;
    return this.httpClient.get<GetResponseUserTiles>(url);
  }

  deleteUser(userId: number): Observable<any> {
    const url = `${this.baseUrl}/${userId}`;
    return this.httpClient.delete(url);

  }


}

interface GetResponseUserTiles {
  content: UserTile[];
  pageable: {
    size: number,
    totalElements: number,
    totalPages: number,
    number: number
  };
}



