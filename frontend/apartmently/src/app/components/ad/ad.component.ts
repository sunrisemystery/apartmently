import { Component, OnInit } from '@angular/core';
declare const L:any;

@Component({
  selector: 'app-ad',
  templateUrl: './ad.component.html',
  styleUrls: ['./ad.component.css']
})
export class AdComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {

    if (!navigator.geolocation) {
      console.log('not supported');

    }
    navigator.geolocation.getCurrentPosition((position) => {
      const coords = position.coords;
      const latLong: number[] = [coords.latitude, coords.longitude];
      const latLong2: number[] = [50.092035, 20.003633]
  
      console.log(`lat: ${position.coords.latitude}, long: ${position.coords.longitude}`);
      let mymap = L.map('mapid').setView(latLong2, 14); //14 - zoom level
      L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1Ijoic3VucmlzZW15c3RlcnkiLCJhIjoiY2tscXNvZng5MDVyZTJxbXA1bzZoYTVqOCJ9._g_3XgNOvfLxWP2NlK8IOw', {
        attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
        maxZoom: 18,
        id: 'mapbox/streets-v11',
        tileSize: 512,
        zoomOffset: -1,
        accessToken: 'your.mapbox.access.token'
      }).addTo(mymap);
      let circle = L.circle(latLong2, {
        color: 'red',
        fillColor: '#f03',
        fillOpacity: 0.5,
        radius: 500
    }).addTo(mymap);
      let marker = L.marker(latLong2).addTo(mymap);
      let marker2 = L.marker(latLong).addTo(mymap);
      marker.bindPopup('<b>hi!</b>').openPopup();

    }
    );
    this.watchPosition();
  }

  watchPosition() {
    let destLat: number = 0;
    let destLon: number = 0;

    let id: any = navigator.geolocation.watchPosition((position) => {
      console.log(`lat: ${position.coords.latitude}, long: ${position.coords.longitude}`);
      if (position.coords.latitude === destLat) {
        navigator.geolocation.clearWatch(id);
      }
    }, (err) => {
      console.log(err);

    },
      {
        enableHighAccuracy: true,
        timeout: 5000,
        maximumAge: 0,

      });
  }
  }

