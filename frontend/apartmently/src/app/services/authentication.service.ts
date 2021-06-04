import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {LoggedUser} from '../common/logged-user';
import {UserService} from './user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<LoggedUser>;
  public currentUser: Observable<LoggedUser>;
  private baseUrl = 'http://localhost:8080/api/user';


  constructor(private http: HttpClient, private userService: UserService) {
    this.currentUserSubject = new BehaviorSubject<LoggedUser>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): LoggedUser {

    return this.currentUserSubject.value;
  }

  isLoggedIn(): boolean {
    return this.currentUserSubject.value ? true : false;
  }

  login(username: string, password: string) {
    return this.http.post<any>(`${this.baseUrl}/sign-in`, {username, password})
      .pipe(map(user => {
        // store user details and jwt token in local storage to keep user logged in between page refreshes
        localStorage.setItem('currentUser', JSON.stringify(user));
        this.currentUserSubject.next(user);
        return user;
      }));

  }

  logout(): void {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    this.userService.getImage.next('/assets/images/placeholder.png');
  }

  register(username: string, email: string, password: string) {
    return this.http.post<any>(`${this.baseUrl}/sign-up`, {email, username, password})
      .pipe(map(message => {
        // store user details and jwt token in local storage to keep user logged in between page refreshes
        return message;
      }));

  }

}
