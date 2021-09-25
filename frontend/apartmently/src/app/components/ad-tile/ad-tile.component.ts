import {Component, OnInit, Input} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {AdTile} from 'src/app/common/ad-tile';

@Component({
  selector: 'app-ad-tile',
  templateUrl: './ad-tile.component.html',
  styleUrls: ['./ad-tile.component.css']
})
export class AdTileComponent implements OnInit {

  @Input() adTile: AdTile;
  @Input() favList: number[];
  FULL_HEART = 'fas fa-heart';
  EMPTY_HEART = 'far fa-heart';
  heart = new BehaviorSubject<string>(this.EMPTY_HEART);

  constructor() {
  }

  ngOnInit(): void {
    this.heart.subscribe();
    if (this.favList.includes(this.adTile.id)) {
      this.heart.next(this.FULL_HEART);
    }
  }

}
