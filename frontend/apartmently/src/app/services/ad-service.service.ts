import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AdDetails } from '../common/ad-details';
import { AdTile } from '../common/ad-tile';

@Injectable({
  providedIn: 'root'
})
export class AdService {

  private baseUrl = 'http://localhost:8080/api/ad'

  constructor(private httpClient: HttpClient) { }

  getAdsForRentPaginate(thePage: number, thePageSize: number):
    Observable<GetResponseAdTiles> {
    const searchUrl = `${this.baseUrl}/rent?page=${thePage}&size=${thePageSize}`;

    return this.httpClient.get<GetResponseAdTiles>(searchUrl);
  }

  getAdDetails(theAdId: number): Observable<AdDetails> {

    const adUrl = `${this.baseUrl}/details/${theAdId}`;
    return this.httpClient.get<AdDetails>(adUrl);
  }

  getAd(theAdId: number): Observable<AdTile> {

    const adUrl = `${this.baseUrl}/${theAdId}`;
    return this.httpClient.get<AdTile>(adUrl);
  }
}

interface GetResponseAdTiles {
  content: AdTile[],
  pageable: {
    size: number,
    totalElements: number,
    totalPages: number,
    number: number
  }
}

