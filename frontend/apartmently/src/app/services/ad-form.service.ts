import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AdOffer} from '../common/ad-offer';
import {City} from '../common/city';
import {Country} from '../common/country';


@Injectable({
  providedIn: 'root'
})
export class AdFormService {

  private countriesUrl = 'http://localhost:8080/api/countries';
  private adUrl = 'http://localhost:8080/api/ad';
  private adTypeUrl = 'http://localhost:8080/api/ad/ad-types';
  private cityUrl = 'http://localhost:8080/api/cities';
  private adImageUrl = 'http://localhost:8080/api/ad-image';


  constructor(private httpClient: HttpClient) { }

  getCountries(): Observable<Country[]> {

    return this.httpClient.get<Country[]>(this.countriesUrl);
  }

  getAdTypes(): Observable<string[]> {
    return this.httpClient.get<string[]>(this.adTypeUrl);
  }

  async checkCity(name: string): Promise<boolean> {
    const searchUrl = `${this.cityUrl}/check/${name}`;
    return await this.httpClient.get<boolean>(searchUrl).toPromise();
  }

  async getCityId(name: string): Promise<number> {
    const searchUrl = `${this.cityUrl}/${name}`;
    return await this.httpClient.get<number>(searchUrl).toPromise();
  }

  async placeCity(city: City): Promise<number> {
    return await this.httpClient.post<number>(this.cityUrl, city).toPromise();
  }

  placeImages(adId: number, imageUrls: string[]): Observable<any> {
    const imagesUrl = `${this.adImageUrl}/${adId}`;
    return this.httpClient.post<string[]>(imagesUrl, imageUrls);

  }

  placeAd(ad: AdOffer): Observable<any> {
    return this.httpClient.post<AdOffer>(this.adUrl, ad);
  }

  updateAd(ad: AdOffer, adId: number): Observable<any> {
    const url = `${this.adUrl}/${adId}`;
    return this.httpClient.put<AdOffer>(url, ad);
  }

}
