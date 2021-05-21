import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {NavigationComponent} from './components/navigation/navigation.component';
import {AdsListComponent} from './components/ads-list/ads-list.component';
import {AdComponent} from './components/ad/ad.component';
import {ProfileComponent} from './components/profile/profile.component';
import {AddAdComponent} from './components/add-ad/add-ad.component';
import {AdTileComponent} from './components/ad-tile/ad-tile.component';
import {RouterModule, Routes} from '@angular/router';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {MatPaginatorModule} from '@angular/material/paginator';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ReactiveFormsModule} from '@angular/forms';
import {AngularFireModule} from '@angular/fire';
import {environment} from '../environments/environment';
import {
  AngularFireStorageModule,
  AngularFireStorageReference,
  AngularFireUploadTask
} from '@angular/fire/storage';
import {LoginComponent} from './components/login/login.component';
import {LoginFormComponent} from './components/login-form/login-form.component';
import {RegisterFormComponent} from './components/register-form/register-form.component';
import {RegistrationComponent} from './components/registration/registration.component';
import {JwtInterceptor} from './security/jwt.interceptor';
import {ErrorInterceptor} from './security/error.interceptor';
import {AuthGuard} from './security/auth.guard';


const MAT_MODULES = [
  BrowserAnimationsModule,
  MatPaginatorModule
];

const routes: Routes = [
  {path: 'offers', component: AdsListComponent, canActivate: [AuthGuard]},
  {path: 'for-rent', component: AdsListComponent, canActivate: [AuthGuard]},
  {path: 'for-sale', component: AdsListComponent, canActivate: [AuthGuard]},
  {path: 'add-offer', component: AddAdComponent, canActivate: [AuthGuard]},
  {path: 'offers/:id', component: AdComponent},
  {path: 'search/:keyword', component: AdsListComponent},
  {path: 'favorites', component: AdsListComponent, canActivate: [AuthGuard]},
  {path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]},
  {path: 'profile/:id', component: ProfileComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent, data: {registration: false}},
  {path: 'register', component: LoginComponent, data: {registration: true}},
  {path: 'full-info', component: RegistrationComponent, canActivate: [AuthGuard]},
  {path: '', redirectTo: '/offers', pathMatch: 'full'},
  {path: '**', redirectTo: '/offers', pathMatch: 'full'},
];

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    AdsListComponent,
    AdComponent,
    ProfileComponent,
    AddAdComponent,
    AdTileComponent,
    LoginComponent,
    LoginFormComponent,
    RegisterFormComponent,
    RegistrationComponent,

  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    NoopAnimationsModule,
    MAT_MODULES,
    AngularFireStorageModule,
    ReactiveFormsModule,
    AngularFireModule.initializeApp(environment.firebaseConfig, "cloud"),


  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true}

  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
