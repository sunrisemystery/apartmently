import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserInfo} from 'src/app/common/user-info';
import {AuthenticationService} from 'src/app/services/authentication.service';
import {UserService} from 'src/app/services/user.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  public imageUrl = '/assets/images/placeholder.png';
  private userInfo: UserInfo;

  constructor(public authService: AuthenticationService, private router: Router, private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.getImage.subscribe(img => this.imageUrl = img);

    if (this.authService.isLoggedIn()) {
      this.userService.getUserInfo(this.authService.currentUserValue.id).subscribe(
        response => {
          this.userInfo = response;
          this.userService.getImage.next(this.userInfo.imageUrl);
        });
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}
