import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-ads-list',
  templateUrl: './ads-list.component.html',
  styleUrls: ['./ads-list.component.css']
})
export class AdsListComponent implements OnInit {

  currentName: string = 'Cozy House';
  currPrice: number = 10000;

  constructor() { }

  ngOnInit(): void {
  }

}
