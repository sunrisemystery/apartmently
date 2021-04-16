import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AdTile } from 'src/app/common/ad-tile';
import { AdService } from 'src/app/services/ad-service.service';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-ads-list',
  templateUrl: './ads-list.component.html',
  styleUrls: ['./ads-list.component.css']
})
export class AdsListComponent implements OnInit {

  ads: AdTile[];
  //new properties for pagination
  thePageNumber: number = 1;
  thePageSize: number = 10;
  theTotalElements: number = 0;

  constructor(private adService: AdService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.listAdsForRent();
    })
  }

  listAdsForRent() {
    this.adService.getAdsForRentPaginate(this.thePageNumber - 1, this.thePageSize).subscribe(this.processResult());
  }

  processResult() {
    return data => {
      this.ads = data.content;
      this.thePageNumber = data.pageable.number + 1;
      this.thePageSize = data.pageable.size;
      this.theTotalElements = data.pageable.totalElements;
    };
  }

  OnPageChange(event: PageEvent) {

    this.thePageSize = event.pageSize;
    this.thePageNumber = event.pageIndex;
    this.listAdsForRent();
  }
}
