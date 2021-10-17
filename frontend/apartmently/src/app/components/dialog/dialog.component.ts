import {Component, OnInit} from '@angular/core';
import {MatDialogModule} from '@angular/material/dialog';
import {AdTile} from 'src/app/common/ad-tile';

import {AdService} from 'src/app/services/ad-service.service';
import {AuthenticationService} from 'src/app/services/authentication.service';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {
  adsList: AdTile[];
  permitted: AdTile[];

  constructor(private adService: AdService, private auth: AuthenticationService) {

  }

  ngOnInit(): void {
    this.adService.getUserAds(0, 100, this.auth.currentUserValue.id).subscribe(cal => {
        this.adsList = cal.content;
      }
    );
    this.adService.getPermittedAds(this.auth.currentUserValue.id).subscribe(val => {
      this.permitted = val;
    });

  }

}
