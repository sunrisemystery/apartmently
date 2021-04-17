import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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

  constructor(private adService: AdService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    if (this.router.url === '/for-rent') {

      this.route.paramMap.subscribe(() => {
        this.listAdsForRent();
      })
    }
    else if (this.router.url === '/for-sale') {
      this.route.paramMap.subscribe(() => {
        this.listAdsForSale();
      })
    }
    else {
      this.route.paramMap.subscribe(() => {
        this.listAllAds();
      })
    }
  }

  listAllAds() {
    this.adService.getAdsPaginate(this.thePageNumber - 1, this.thePageSize).subscribe(this.processResult());
  }
  listAdsForRent() {
    this.adService.getAdsForRentPaginate(this.thePageNumber - 1, this.thePageSize).subscribe(this.processResult());
  }

  listAdsForSale() {
    this.adService.getAdsForSalePaginate(this.thePageNumber - 1, this.thePageSize).subscribe(this.processResult());
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
