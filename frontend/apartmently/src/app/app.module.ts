import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NavigationComponent } from './components/navigation/navigation.component';
import { AdsListComponent } from './components/ads-list/ads-list.component';
import { AdComponent } from './components/ad/ad.component';
import { ProfileComponent } from './components/profile/profile.component';
import { AddAdComponent } from './components/add-ad/add-ad.component';
import { AdTileComponent } from './components/ad-tile/ad-tile.component';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatPaginatorModule } from '@angular/material/paginator';
import { ReactiveFormsModule } from '@angular/forms';
import { AngularFireModule } from "@angular/fire";
import { environment } from "../environments/environment";
import {
  AngularFireStorageModule,
  AngularFireStorageReference,
  AngularFireUploadTask
} from "@angular/fire/storage";




const routes: Routes = [
  { path: 'offers', component: AdsListComponent },
  { path: 'for-rent', component: AdsListComponent },
  { path: 'for-sale', component: AdsListComponent },
  { path: 'add-offer', component: AddAdComponent },
  { path: 'offers/:id', component: AdComponent },
  { path: 'search/:keyword', component: AdsListComponent },
  { path: 'favorites', component: AdsListComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'profile/:id', component: ProfileComponent },
  { path: '', redirectTo: '/offers', pathMatch: 'full' },
  { path: '**', redirectTo: '/offers', pathMatch: 'full' },
];

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    AdsListComponent,
    AdComponent,
    ProfileComponent,
    AddAdComponent,
    AdTileComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    NoopAnimationsModule,
    MatPaginatorModule,
    AngularFireStorageModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
