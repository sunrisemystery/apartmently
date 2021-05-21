import {Component, OnInit, Input} from '@angular/core';
import {AdTile} from 'src/app/common/ad-tile';

@Component({
  selector: 'app-ad-tile',
  templateUrl: './ad-tile.component.html',
  styleUrls: ['./ad-tile.component.css']
})
export class AdTileComponent implements OnInit {

  @Input() adTile: AdTile;

  constructor() {
  }

  ngOnInit(): void {
  }

}
