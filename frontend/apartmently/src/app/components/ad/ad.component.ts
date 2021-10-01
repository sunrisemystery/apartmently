import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {BehaviorSubject} from 'rxjs';
import {AdDetails} from 'src/app/common/ad-details';
import {AdTile} from 'src/app/common/ad-tile';
import {AdService} from 'src/app/services/ad-service.service';
import {AuthenticationService} from 'src/app/services/authentication.service';
import {ChatService} from 'src/app/services/chat.service';


declare const L: any;

@Component({
  selector: 'app-ad',
  templateUrl: './ad.component.html',
  styleUrls: ['./ad.component.css']
})
export class AdComponent implements OnInit {

  adTile: AdTile = new AdTile();
  adDetails: AdDetails = new AdDetails();
  images: string[] = [];
  currentImage: string;
  FULL_HEART_CSS = 'fas fa-heart';
  EMPTY_HEART_CSS = 'far fa-heart';
  ADD_TEXT = 'Add to favorites';
  REMOVE_TEXT = 'Remove from favorites';
  heart = new BehaviorSubject<string>(this.EMPTY_HEART_CSS);
  favText = new BehaviorSubject<string>(this.ADD_TEXT);
  counter = 0;


  constructor(private adService: AdService,
              private route: ActivatedRoute,
              public authService: AuthenticationService,
              private router: Router,
              private chatService: ChatService) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.handleAdDetails();
    });

    this.heart.subscribe();
    this.favText.subscribe();
  }

  handleAdDetails(): void {
    const adId: number = +this.route.snapshot.paramMap.get('id');

    this.adService.getAd(adId).subscribe(
      data => {
        this.adTile = data;

        this.adTile.adImages.forEach((element) => {
          const pathImage: any = JSON.parse(JSON.stringify(element));
          this.images.push(pathImage.imageUrl);
        });

        this.currentImage = this.images[0];
      }
    );

    this.adService.getAdDetails(adId).then((data) => {
      this.adDetails = data;
      this.initializeMap([this.adDetails.latitude, this.adDetails.longitude]);
      this.adService.getFavoritesId(this.authService.currentUserValue.id).subscribe((val) => {
        if (val.includes(this.adTile.id)) {
          this.heart.next(this.FULL_HEART_CSS);
          this.favText.next(this.REMOVE_TEXT);
        }
      });

    });

  }

  handler(val: string): void {
    if (val === this.FULL_HEART_CSS) {
      this.removeFromFavorites();
    } else {
      this.addToFavourites();
    }
  }

  addToFavourites(): void {
    const adId: number = +this.route.snapshot.paramMap.get('id');
    this.adService.addToFavourites(adId).subscribe(
      data => {
        alert('Offer added successfully!');
        this.heart.next(this.FULL_HEART_CSS);
        this.favText.next(this.REMOVE_TEXT);
      }
    );
  }

  removeFromFavorites(): void {
    const adId: number = +this.route.snapshot.paramMap.get('id');
    if (confirm('Do you want to remove this offer from favorites?')) {
      this.adService.removeFromFavorites(adId).subscribe(
        data => {
          this.heart.next(this.EMPTY_HEART_CSS);
          this.favText.next(this.ADD_TEXT);
          alert('Offer removed from favorites!');
        }
      );
    }
  }

  deleteAd(): void {
    if (this.adDetails.userId === this.authService.currentUserValue.id) {
      if (confirm('Are you sure?')) {
        const adId: number = +this.route.snapshot.paramMap.get('id');
        this.adService.deleteAd(adId).subscribe(
          data => {
            alert(data.message);
            this.router.navigateByUrl('/');
          }
        );
      }
    } else {
      alert('This is not your offer');
    }
  }

  initializeMap(coordinates: number[]): void {

    navigator.geolocation.getCurrentPosition((position) => {

        const mymap = L.map('mapid').setView(coordinates, 14); // 14 - zoom level

        L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1Ijoic3VucmlzZW15c3RlcnkiLCJhIjoiY2tscXNvZng5MDVyZTJxbXA1bzZoYTVqOCJ9._g_3XgNOvfLxWP2NlK8IOw', {
          attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
          maxZoom: 18,
          id: 'mapbox/streets-v11',
          tileSize: 512,
          zoomOffset: -1,
          accessToken: 'your.mapbox.access.token'
        }).addTo(mymap);
        const circle = L.circle(coordinates, {
          color: 'red',
          fillColor: '#f03',
          fillOpacity: 0.5,
          radius: 500
        }).addTo(mymap);

      }
    );
  }

  mySlide(event): void {
    const max = this.images.length;
    this.counter = (this.counter < max - 1) ? (this.counter + 1) : 0;
    this.currentImage = this.images[this.counter];
  }

  editAd(): void {

    (this.adDetails.userId === this.authService.currentUserValue.id)
      ? this.router.navigateByUrl(`/edit/${this.adDetails.id}`) : alert('This is not your offer');

  }

  messageUser(): void {
    this.chatService.createConversation(this.adDetails.userId).subscribe(() => {
      this.router.navigateByUrl('/messenger');
    });
  }

}

