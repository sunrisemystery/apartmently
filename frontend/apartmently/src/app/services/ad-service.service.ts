import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AdDetails} from '../common/ad-details';
import {AdTile} from '../common/ad-tile';
import {AuthenticationService} from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AdService {

  private baseUrl = 'http://localhost:8080/api/ad';


  constructor(private httpClient: HttpClient, private authService: AuthenticationService) {
  }

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
    const searchUrl = `${this.baseUrl}/favorites/${userId}?page=${thePage}&size=${thePageSize}`;

    return this.getAdTiles(searchUrl);
  }

  getFavoritesId(userId: number): Observable<number[]> {
    const searchUrl = `${this.baseUrl}/favlist/${userId}`;
    return this.httpClient.get<number[]>(searchUrl);

  }


  async getAdDetails(theAdId: number): Promise<AdDetails> {
    const adUrl = `${this.baseUrl}/details/${theAdId}`;

    return await this.httpClient.get<AdDetails>(adUrl).toPromise();
  }

  getAd(theAdId: number): Observable<AdTile> {
    const adUrl = `${this.baseUrl}/${theAdId}`;

    return this.httpClient.get<AdTile>(adUrl);
  }

  getUserAds(thePage: number, thePageSize: number, userId: number): Observable<GetResponseAdTiles> {
    const searchUrl = `${this.baseUrl}/user/${userId}?page=${thePage}&size=${thePageSize}`;
    return this.getAdTiles(searchUrl);

  }

  getPermittedAds(userId: number): Observable<AdTile[]> {
    const searchUrl = `${this.baseUrl}/permitted/${userId}`;
    return this.httpClient.get<AdTile[]>(searchUrl);
  }

  searchAds(thePage: number, thePageSize: number, keyword: string):
    Observable<GetResponseAdTiles> {
    const searchUrl = `${this.baseUrl}/search/${keyword}?page=${thePage}&size=${thePageSize}`;

    return this.getAdTiles(searchUrl);
  }

  getAdForEdit(adId: number): Observable<any> {
    const url = `${this.baseUrl}/edit/${adId}`;
    return this.httpClient.get<any>(url);

  }

  getAdTiles(searchUrl: string): Observable<GetResponseAdTiles> {
    return this.httpClient.get<GetResponseAdTiles>(searchUrl);
  }

  getPdf(adId: number): Observable<any> {
    const url = `${this.baseUrl}/generate-pdf/${adId}`;
    const options = {
      responseType: 'arraybuffer' as 'json'

    }
    return this.httpClient.get(url, options);
  }

  addToFavourites(adId: number): Observable<any> {
    const userId = this.authService.currentUserValue.id;
    const url = `${this.baseUrl}/favorites/${adId}/${userId}`;
    return this.httpClient.post(url, null);
  }

  givePermission(userId: number, adId: number): Observable<any> {
    const url = `${this.baseUrl}/permit-pdf/${adId}/${userId}`;
    return this.httpClient.post(url, null);
  }

  removeFromFavorites(adId: number): Observable<any> {
    const userId = this.authService.currentUserValue.id;
    const url = `${this.baseUrl}/favorites/${adId}/${userId}`;
    return this.httpClient.put(url, null);
  }

  deleteAd(adId: number): Observable<any> {
    const url = `${this.baseUrl}/${adId}`;
    return this.httpClient.delete(url);
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

