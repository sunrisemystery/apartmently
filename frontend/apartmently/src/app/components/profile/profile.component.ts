import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AdTile} from 'src/app/common/ad-tile';
import {UserInfo} from 'src/app/common/user-info';
import {AdService} from 'src/app/services/ad-service.service';
import {AuthenticationService} from 'src/app/services/authentication.service';
import {UserService} from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  ads: AdTile[];
  thePageNumber = 0;
  thePageSize = 6;
  theTotalElements = 0;
  user: UserInfo = new UserInfo();
  favIdList: number[];


  constructor(private userService: UserService, private adService: AdService,
              private router: Router, public authService: AuthenticationService) {
  }

  ngOnInit(): void {


    this.adService.getUserAds(this.thePageNumber, this.thePageSize, this.authService.currentUserValue.id)
      .subscribe(this.processResult());

    this.userService.getUserInfo(this.authService.currentUserValue.id).subscribe({
      next: response => {
        this.user = response;
      }
    });

    this.adService.getFavoritesId(this.authService.currentUserValue.id).subscribe((val) => {
      this.favIdList = val;
    });
  }

  editData(): void {
    this.router.navigateByUrl('/update-info');
  }

  processResult() {
    return data => {
      this.ads = data.content;
      this.thePageNumber = data.number;
      this.thePageSize = data.size;
      this.theTotalElements = data.totalElements;
    };
  }

}
