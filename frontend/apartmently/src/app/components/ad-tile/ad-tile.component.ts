import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-ad-tile',
  templateUrl: './ad-tile.component.html',
  styleUrls: ['./ad-tile.component.css']
})
export class AdTileComponent implements OnInit {

  @Input() price: number;
  @Input() name: string;
  @Input() location: string;
  @Input() plotSurface: number;
  @Input() bathrooms: number;
  @Input() bedrooms : number;
  @Input() isFav : boolean;
  @Input() isActive: boolean;

  constructor() { }

  ngOnInit(): void {
  }

}
