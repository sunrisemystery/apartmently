import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AdDetails } from '../common/ad-details';
import { AdTile } from '../common/ad-tile';

@Injectable({
  providedIn: 'root'
})
export class AdService {

  private baseUrl = 'http://localhost:8080/api/ad';

  constructor(private httpClient: HttpClient) { }

  getAdsPaginate(thePage: number, thePageSize: number):
    Observable<GetResponseAdTiles> {
    const searchUrl = `${this.baseUrl}?page=${thePage}&size=${thePageSize}`;

    return this.getAdTiles(searchUrl);
  }

  getAdsForRentPaginate(thePage: number, thePageSize: number):
    Observable<GetResponseAdTiles> {
    const searchUrl = `${this.baseUrl}/rent?page=${thePage}&size=${thePageSize}`;

    return this.getAdTiles(searchUrl);
  }

  getAdsForSalePaginate(thePage: number, thePageSize: number):
    Observable<GetResponseAdTiles> {
    const searchUrl = `${this.baseUrl}/sale?page=${thePage}&size=${thePageSize}`;

    return this.getAdTiles(searchUrl);
  }

  getUserFavoritesPaginate(thePage: number, thePageSize: number, userId: number):
    Observable<GetResponseAdTiles> {
    const seatchUrl = `${this.baseUrl}/favorites/${userId}?page=${thePage}&size=${thePageSize}`;

    return this.getAdTiles(seatchUrl);
  }

  getAdDetails(theAdId: number): Observable<AdDetails> {
    const adUrl = `${this.baseUrl}/details/${theAdId}`;

    return this.httpClient.get<AdDetails>(adUrl);
  }

  getAd(theAdId: number): Observable<AdTile> {
    const adUrl = `${this.baseUrl}/${theAdId}`;

    return this.httpClient.get<AdTile>(adUrl);
  }

  getUserAds(thePage: number, thePageSize: number, userId: number): Observable<GetResponseAdTiles>{
    const searchUrl = `${this.baseUrl}/user/${userId}?page=${thePage}&size=${thePageSize}`;
    return this.getAdTiles(searchUrl);

  }

  searchAds(thePage: number, thePageSize: number, keyword: string):
    Observable<GetResponseAdTiles> {
    const searchUrl = `${this.baseUrl}/search/${keyword}?page=${thePage}&size=${thePageSize}`;

    return this.getAdTiles(searchUrl);
  }

  getAdTiles(searchUrl: string): Observable<GetResponseAdTiles> {
    return this.httpClient.get<GetResponseAdTiles>(searchUrl);
  }

}

interface GetResponseAdTiles {
  content: AdTile[];
  pageable: {
    size: number,
    totalElements: number,
    totalPages: number,
    number: number
  };
}

