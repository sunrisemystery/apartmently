import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AdOffer } from '../common/ad-offer';
import { City } from '../common/city';
import { Country } from '../common/country';


@Injectable({
  providedIn: 'root'
})
export class AdFormService {

  private countriesUrl = 'http://localhost:8080/api/countries';
  private adUrl = 'http://localhost:8080/api/ad';
  private adTypeUrl = 'http://localhost:8080/api/ad/ad-types';
  private cityUrl = 'http://localhost:8080/api/cities';


  constructor(private httpClient: HttpClient) { }

  getCountries(): Observable<Country[]> {

    return this.httpClient.get<Country[]>(this.countriesUrl);
  }

  getAdTypes(): Observable<string[]> {
    return this.httpClient.get<string[]>(this.adTypeUrl);
  }

  async checkCity(name: string): Promise<boolean> {
    const searchUrl = `${this.cityUrl}/check/${name}`;
    const s = await this.httpClient.get<boolean>(searchUrl).toPromise();
    return s;
  }

  async getCityId(name: string) {
    const searchUrl = `${this.cityUrl}/${name}`;
    const s = await this.httpClient.get<number>(searchUrl).toPromise();
    return s;
  }

  async placeCity(city: City): Promise<number> {
    const s = await this.httpClient.post<number>(this.cityUrl, city).toPromise();
    return s;
  }

  placeAd(ad: AdOffer): Observable<any> {
    return this.httpClient.post<AdOffer>(this.adUrl, ad);
  }

}
