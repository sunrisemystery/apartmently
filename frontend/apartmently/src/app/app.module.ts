import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NavigationComponent } from './components/navigation/navigation.component';
import { AdsListComponent } from './components/ads-list/ads-list.component';
import { AdComponent } from './components/ad/ad.component';
import { FavoritesComponent } from './components/favorites/favorites.component';
import { ProfileComponent } from './components/profile/profile.component';
import { AddAdComponent } from './components/add-ad/add-ad.component';
import { AdTileComponent } from './components/ad-tile/ad-tile.component';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';


const routes: Routes = [
{path: 'offers', component: AdsListComponent},
{path: 'add-offer', component: AddAdComponent},
{path: 'offers/:id', component: AdComponent},
{path: 'offers/:keyword', component: AdsListComponent},
{path: 'favorites', component: FavoritesComponent},
{path: 'profile', component: ProfileComponent},
{path: 'profile/:id', component: ProfileComponent},
{path: 'ad', component: AdComponent},
{path: '', redirectTo:'/offers',pathMatch:'full'},
{path: '**', redirectTo:'/offers',pathMatch:'full'},
];

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    AdsListComponent,
    AdComponent,
    FavoritesComponent,
    ProfileComponent,
    AddAdComponent,
    AdTileComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
