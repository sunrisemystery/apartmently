import { Component, OnInit } from '@angular/core';
import { GeocoderAutocomplete } from '@geoapify/geocoder-autocomplete';

@Component({
  selector: 'app-add-ad',
  templateUrl: './add-ad.component.html',
  styleUrls: ['./add-ad.component.css']
})
export class AddAdComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {

    const autocomplete = new GeocoderAutocomplete(
      document.getElementById("autocomplete"),
      '067d039a00d245019a1a8e4be86031f8',
      { /* Geocoder options */ skipIcons: true, placeholder: 'Enter address for autocomplete fields and choose the correct one' });
   autocomplete.addFilterByCountry(['pl']);

   autocomplete.on('select', (location) => {
    // check selected location here 
});

autocomplete.on('suggestions', (suggestions) => {
    // process suggestions here
});


  }

  

}
