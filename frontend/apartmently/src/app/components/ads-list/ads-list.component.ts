import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AdTile} from 'src/app/common/ad-tile';
import {AdService} from 'src/app/services/ad-service.service';
import {MatPaginator, PageEvent} from '@angular/material/paginator';
import {AuthenticationService} from 'src/app/services/authentication.service';


@Component({
  selector: 'app-ads-list',
  templateUrl: './ads-list.component.html',
  styleUrls: ['./ads-list.component.css']
})
export class AdsListComponent implements OnInit {

  ads: AdTile[];
  favIdList: number[];
  // new properties for pagination
  thePageNumber = 0;
  thePageSize = 6;
  theTotalElements = 0;
  pageTitle = 'All properties';
  searchMode = false;

  userId: number;

  previousKeyword: string = null;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  pageEvent: PageEvent;


  constructor(private adService: AdService, private route: ActivatedRoute, private router: Router,
              private authService: AuthenticationService) {
  }

  ngOnInit(): void {

    if (this.router.url === '/for-rent') {
      this.pageTitle = 'Properties for rent';

      this.route.paramMap.subscribe(() => {
        this.listAdsForRent();
      });
    } else if (this.router.url === '/for-sale') {
      this.pageTitle = 'Properties for sale';
      this.route.paramMap.subscribe(() => {
        this.listAdsForSale();
      });
    } else if (this.router.url === '/favorites') {
      this.pageTitle = 'Your favorites';
      this.route.paramMap.subscribe(() => {
        this.listUserFavorites();
      });
    } else {
      this.pageTitle = 'All properties';
      this.route.paramMap.subscribe(() => {
        this.listAllAds();
      });

    }
    this.adService.getFavoritesId(this.authService.currentUserValue.id).subscribe((val) => {
      this.favIdList = val;
    })
  }

  sortByPrice(val: boolean): void {

    this.ads = this.ads.sort((a, b) => val ? a.price - b.price : b.price - a.price);
  }


  listAllAds(): void {

    this.searchMode = this.route.snapshot.paramMap.has('keyword');

    this.searchMode ? this.searchAds() : this.adService.getAdsPaginate(this.thePageNumber, this.thePageSize)
      .subscribe(this.processResult());
  }

  listAdsForRent(): void {
    this.adService.getAdsForRentPaginate(this.thePageNumber, this.thePageSize)
      .subscribe(this.processResult());
  }

  listAdsForSale(): void {
    this.adService.getAdsForSalePaginate(this.thePageNumber, this.thePageSize)
      .subscribe(this.processResult());
  }

  listUserFavorites(): void {
    this.userId = this.authService.currentUserValue.id;
    this.adService.getUserFavoritesPaginate(this.thePageNumber, this.thePageSize, this.userId)
      .subscribe(this.processResult());
  }

  processResult() {
    return data => {
      this.ads = data.content;
      this.thePageNumber = data.number;
      this.thePageSize = data.size;
      this.theTotalElements = data.totalElements;
    };
  }

  OnPageChange(event?: PageEvent): void {

    this.thePageSize = event.pageSize;
    this.thePageNumber = event.pageIndex;
    if (this.router.url === '/offers') {
      this.listAllAds();
    } else if (this.router.url === '/for-sale') {
      this.listAdsForSale();
    } else {
      this.listAdsForRent();
    }

  }

  searchAds(): void {
    this.pageTitle = 'Search results';
    const keyword: string = this.route.snapshot.paramMap.get('keyword');
    if (this.previousKeyword !== keyword) {
      this.thePageNumber = 0;
    }
    this.previousKeyword = keyword;
    this.adService.searchAds(this.thePageNumber, this.thePageSize, keyword)
      .subscribe(this.processResult());
  }

  doSearch(value: string): void {
    if (value) {
      this.router.navigateByUrl(`/search/${value}`);
    } else {
      this.router.navigateByUrl(`/offers`);
    }
  }
}
