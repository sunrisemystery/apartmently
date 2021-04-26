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
  thePageSize: number = 5;
  theTotalElements: number = 0;
  pageTitle: string = "All properties";
  searchMode: boolean = false;
  //hardcoded value
  userId: number = 1;

  previousKeyword: string = null;

  constructor(private adService: AdService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    if (this.router.url === '/for-rent') {
      this.pageTitle = "Properties for rent";

      this.route.paramMap.subscribe(() => {
        this.listAdsForRent();
      })
    } else if (this.router.url === '/for-sale') {
      this.pageTitle = "Properties for sale";
      this.route.paramMap.subscribe(() => {
        this.listAdsForSale();
      })
    } else if (this.router.url === '/favorites') {
      this.pageTitle = "Your favorites";
      this.route.paramMap.subscribe(() => {
        this.listUserFavorites();
      })
    } else {
      this.pageTitle = "All properties";
      this.route.paramMap.subscribe(() => {
        this.listAllAds();
      })
    }
  }

  listAllAds() {

    this.searchMode = this.route.snapshot.paramMap.has('keyword');
    if (this.searchMode) {
      this.searchAds();
    } else {
      this.adService.getAdsPaginate(this.thePageNumber - 1, this.thePageSize)
        .subscribe(this.processResult());
    }
  }

  listAdsForRent() {
    this.adService.getAdsForRentPaginate(this.thePageNumber - 1, this.thePageSize)
      .subscribe(this.processResult());
  }

  listAdsForSale() {
    this.adService.getAdsForSalePaginate(this.thePageNumber - 1, this.thePageSize)
      .subscribe(this.processResult());
  }

  listUserFavorites() {
    this.adService.getUserFavoritesPaginate(this.thePageNumber - 1, this.thePageSize, this.userId)
      .subscribe(this.processResult());
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
    console.log(this.thePageSize);
    if (this.router.url === '/offers') {
      this.listAllAds();
    } else if (this.router.url === '/for-sale') {
      this.listAdsForSale();
    } else {
      this.listAdsForRent();
    }

  }

  searchAds() {
    this.pageTitle = "Search results";
    const keyword: string = this.route.snapshot.paramMap.get('keyword');
    if (this.previousKeyword != keyword) {
      this.thePageNumber = 1;
    }
    this.previousKeyword = keyword;
    this.adService.searchAds(this.thePageNumber - 1, this.thePageSize, keyword)
      .subscribe(this.processResult());
  }

  doSearch(value: string) {
    if (value) {
      this.router.navigateByUrl(`/search/${value}`);
    } else {
      this.router.navigateByUrl(`/offers`);
    }
  }
}
