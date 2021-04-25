import { Component, OnInit } from '@angular/core';
import { GeocoderAutocomplete } from '@geoapify/geocoder-autocomplete';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Country } from 'src/app/common/country';
import { AdFormService } from 'src/app/services/ad-form.service';
import { Router } from '@angular/router';
import { AdOffer } from 'src/app/common/ad-offer';

@Component({
  selector: 'app-add-ad',
  templateUrl: './add-ad.component.html',
  styleUrls: ['./add-ad.component.css']
})
export class AddAdComponent implements OnInit {

  adFormGroup: FormGroup;
  countries: Country[] = [];
  addedAd: AdOffer = new AdOffer();
  adTypes: string[] = [];
  cityId: number;
  cityExists: boolean;


  constructor(private formBuilder: FormBuilder, private adFormService: AdFormService, private router: Router) { }

  ngOnInit(): void {


    const autocomplete = new GeocoderAutocomplete(
      document.getElementById("autocomplete"),
      '067d039a00d245019a1a8e4be86031f8',
      { /* Geocoder options */ skipIcons: true, placeholder: 'Enter address for autocomplete fields and choose the correct one' });
    autocomplete.addFilterByCountry(['pl']);

    autocomplete.on('select', (location) => {
      if (location) {
        console.log(location);

        this.addedAd.address.postalCode = location.properties.postcode;
        this.addedAd.address.streetNumber = location.properties.housenumber;
        this.addedAd.address.streetName = location.properties.street;
        this.addedAd.address.latitude = location.properties.lat;
        this.addedAd.address.longitude = location.properties.lon;
        this.addedAd.address.city.name = location.properties.city;
        if (this.addedAd.address.streetName === undefined) {
          this.addedAd.address.streetName = location.properties.name;
        }

        this.changeVal();

      }
    });

    autocomplete.on('suggestions', (suggestions) => {
      // process suggestions here
    });

    this.adFormGroup = this.formBuilder.group({
      ad: this.formBuilder.group({
        name: new FormControl('', [Validators.required]),
        country: new FormControl('', [Validators.required]),
        city: new FormControl('', [Validators.required]),
        streetName: new FormControl('', [Validators.required]),
        streetNumber: new FormControl('', [Validators.required]),
        postalCode: new FormControl('', [Validators.required]),
        adType: new FormControl('', [Validators.required]),
        plotSurface: new FormControl('', [Validators.required]),
        numberOfBedrooms: new FormControl('', [Validators.required]),
        numberOfBathrooms: new FormControl('', [Validators.required]),
        floor: new FormControl('', [Validators.required]),
        price: new FormControl('', [Validators.required]),
        description: new FormControl('', [Validators.required]),

      })
    });

    //populate countries
    this.adFormService.getCountries().subscribe(
      data => {
        this.countries = data;
      }
    );

    this.adFormService.getAdTypes().subscribe(
      data => {
        this.adTypes = data;
      }
    );

  }

  onSubmit() {
    if (this.adFormGroup.invalid) {
      this.adFormGroup.markAllAsTouched();
      return;
    }

    this.addedAd.adName = this.adFormGroup.get('ad.name').value;
    this.addedAd.adType = this.adFormGroup.get('ad.adType').value;
    this.addedAd.description = this.adFormGroup.get('ad.description').value;
    this.addedAd.numberOfBathrooms = +this.adFormGroup.get('ad.numberOfBathrooms').value;
    this.addedAd.numberOfBedrooms = +this.adFormGroup.get('ad.numberOfBedrooms').value;
    this.addedAd.plotSurface = +this.adFormGroup.get('ad.plotSurface').value;
    this.addedAd.price = +this.adFormGroup.get('ad.price').value;
    //hardcoded value TODO
    this.addedAd.user.id = 1;
    this.addedAd.address.city.name = this.adFormGroup.get('ad.city').value;
    this.addedAd.address.country = this.adFormGroup.get('ad.country').value;
    this.addedAd.address.postalCode = this.adFormGroup.get('ad.postalCode').value;
    this.addedAd.address.streetName = this.adFormGroup.get('ad.streetName').value;
    this.addedAd.address.streetNumber = +this.adFormGroup.get('ad.streetNumber').value;

    this.adFormService.checkCity(this.addedAd.address.city.name).then((exists) => {
      if (!exists) {
        this.adFormService.placeCity(this.addedAd.address.city).then((val) => {
          this.placeAd(val);
        })
      } else {
        this.adFormService.getCityId(this.addedAd.address.city.name).then((val) => {
          this.placeAd(val);
        })
      }
    })

  }

  //use in html to check validation
  get name() { return this.adFormGroup.get('ad.name'); }
  get country() { return this.adFormGroup.get('ad.country'); }
  get city() { return this.adFormGroup.get('ad.city'); }
  get streetName() { return this.adFormGroup.get('ad.streetName'); }
  get streetNumber() { return this.adFormGroup.get('ad.streetNumber'); }
  get postalCode() { return this.adFormGroup.get('ad.postalCode'); }
  get adType() { return this.adFormGroup.get('ad.adType'); }
  get plotSurface() { return this.adFormGroup.get('ad.plotSurface'); }
  get numberOfBedrooms() { return this.adFormGroup.get('ad.numberOfBedrooms'); }
  get numberOfBathrooms() { return this.adFormGroup.get('ad.numberOfBathrooms'); }
  get floor() { return this.adFormGroup.get('ad.floor'); }
  get price() { return this.adFormGroup.get('ad.price'); }
  get description() { return this.adFormGroup.get('ad.description'); }

  changeVal() {
    this.adFormGroup.patchValue({
      ad: {
        streetName: this.addedAd.address.streetName,
        streetNumber: this.addedAd.address.streetNumber,
        postalCode: this.addedAd.address.postalCode,
        city: this.addedAd.address.city.name

      }
    })
  }

  placeAd(cityId: number) {
    this.addedAd.address.city.id = cityId;

    this.adFormService.placeAd(this.addedAd).subscribe(
      {
        next: response => {
          alert(`You added an offer`);
        },
        error: err => {
          alert(err.message);
        }
      }
    )

    this.router.navigateByUrl('/');
  }

}
