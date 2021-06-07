import {Component, OnInit} from '@angular/core';
import {GeocoderAutocomplete} from '@geoapify/geocoder-autocomplete';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Country} from 'src/app/common/country';
import {AdFormService} from 'src/app/services/ad-form.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AdOffer} from 'src/app/common/ad-offer';
import {Observable} from 'rxjs';
import {AngularFireStorage} from '@angular/fire/storage';
import {finalize} from 'rxjs/operators';
import {v4 as uuidv4} from 'uuid';
import {AuthenticationService} from 'src/app/services/authentication.service';
import {AdService} from 'src/app/services/ad-service.service';


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
  imageSrc: string;
  selectedFile: File = null;
  downloadURL: Observable<string>;
  firebaseLink: string;
  adLinks: string[] = [];
  createdAdId: number = null;

  editMode = false;
  adId: string;


  constructor(private formBuilder: FormBuilder, private adFormService: AdFormService, private adService: AdService,
              private router: Router, private route: ActivatedRoute,
              private storage: AngularFireStorage, private authService: AuthenticationService) {
  }

  ngOnInit(): void {

    this.adId = this.route.snapshot.params.id;
    this.editMode = !!this.adId;


    const autocomplete = new GeocoderAutocomplete(
      document.getElementById('autocomplete'),
      '067d039a00d245019a1a8e4be86031f8',
      { /* Geocoder options */
        skipIcons: true,
        placeholder: 'Enter address for autocomplete fields and choose the correct one'
      });
    autocomplete.addFilterByCountry(['pl']);

    autocomplete.on('select', (location) => {
      if (location) {

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
    });

    this.adFormGroup = this.formBuilder.group({
      ad: this.formBuilder.group({
        name: new FormControl('', [Validators.required]),
        country: new FormControl('', [Validators.required]),
        city: new FormControl('', [Validators.required]),
        streetName: new FormControl('', [Validators.required]),
        streetNumber: new FormControl('', [Validators.required, Validators.pattern('^[0-9]*$')]),
        postalCode: new FormControl('', [Validators.required]),
        adType: new FormControl('', [Validators.required]),
        plotSurface: new FormControl('', [Validators.required, Validators.pattern('^[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)$')]),
        numberOfBedrooms: new FormControl('', [Validators.required, Validators.pattern('^[0-9]*$')]),
        numberOfBathrooms: new FormControl('', [Validators.required, Validators.pattern('^[0-9]*$')]),
        floor: new FormControl('', [Validators.required, Validators.pattern('^[0-9]*$')]),
        price: new FormControl('', [Validators.required, Validators.pattern('^[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)$')]),
        description: new FormControl('', [Validators.required, Validators.minLength(10)]),

      })
    });

    // populate countries
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

    if (this.editMode) {
      this.addedAd.address.country = new Country();
      this.adService.getAdForEdit(+this.adId).subscribe(
        data => {
          this.addedAd.adName = data.adName;
          this.addedAd.adType = data.adType;
          this.addedAd.numberOfBathrooms = data.numberOfBathrooms;
          this.addedAd.numberOfBedrooms = data.numberOfBedrooms;
          this.addedAd.plotSurface = data.plotSurface;
          this.addedAd.price = data.price;
          this.addedAd.description = data.description;
          this.addedAd.floorNumber = data.floorNumber;
          this.addedAd.address.city = data.city;
          this.addedAd.address.country = data.country;
          this.addedAd.address.postalCode = data.addressDto.postalCode;
          this.addedAd.address.streetName = data.addressDto.streetName;
          this.addedAd.address.streetNumber = data.addressDto.streetNumber;
          this.addedAd.address.latitude = data.addressDto.latitude;
          this.addedAd.address.longitude = data.addressDto.longitude;

          this.adFormGroup.patchValue({
              ad: {
                name: this.addedAd.adName,
                city: this.addedAd.address.city.name,
                adType: this.addedAd.adType,
                plotSurface: this.addedAd.plotSurface,
                numberOfBedrooms: this.addedAd.numberOfBedrooms,
                numberOfBathrooms: this.addedAd.numberOfBathrooms,
                floor: this.addedAd.floorNumber,
                price: this.addedAd.price,
                description: this.addedAd.description,
                streetName: this.addedAd.address.streetName,
                streetNumber: this.addedAd.address.streetNumber,
                postalCode: this.addedAd.address.postalCode

              }
            }
          );

        }
      );
    }

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
    this.addedAd.floorNumber = +this.adFormGroup.get('ad.floor').value;

    this.addedAd.user.id = this.authService.currentUserValue.id;
    this.addedAd.address.city.name = this.adFormGroup.get('ad.city').value;
    this.addedAd.address.country = this.adFormGroup.get('ad.country').value;
    this.addedAd.address.postalCode = this.adFormGroup.get('ad.postalCode').value;
    this.addedAd.address.streetName = this.adFormGroup.get('ad.streetName').value;
    this.addedAd.address.streetNumber = +this.adFormGroup.get('ad.streetNumber').value;


    this.adFormService.checkCity(this.addedAd.address.city.name).then((exists) => {
      !exists
        ? this.adFormService.placeCity(this.addedAd.address.city).then((val) => {
          this.placeAd(val);
        })
        : this.adFormService.getCityId(this.addedAd.address.city.name).then((val) => {
          this.placeAd(val);
        });
    });


  }

  // use in html to check validation
  get name() {
    return this.adFormGroup.get('ad.name');
  }

  get country() {
    return this.adFormGroup.get('ad.country');
  }

  get city() {
    return this.adFormGroup.get('ad.city');
  }

  get streetName() {
    return this.adFormGroup.get('ad.streetName');
  }

  get streetNumber() {
    return this.adFormGroup.get('ad.streetNumber');
  }

  get postalCode() {
    return this.adFormGroup.get('ad.postalCode');
  }

  get adType() {
    return this.adFormGroup.get('ad.adType');
  }

  get plotSurface() {
    return this.adFormGroup.get('ad.plotSurface');
  }

  get numberOfBedrooms() {
    return this.adFormGroup.get('ad.numberOfBedrooms');
  }

  get numberOfBathrooms() {
    return this.adFormGroup.get('ad.numberOfBathrooms');
  }

  get floor() {
    return this.adFormGroup.get('ad.floor');
  }

  get price() {
    return this.adFormGroup.get('ad.price');
  }

  get description() {
    return this.adFormGroup.get('ad.description');
  }

  changeVal() {
    this.adFormGroup.patchValue({
      ad: {
        streetName: this.addedAd.address.streetName,
        streetNumber: this.addedAd.address.streetNumber,
        postalCode: this.addedAd.address.postalCode,
        city: this.addedAd.address.city.name

      }
    });
  }

  onFileChange(event) {
    const reader = new FileReader();

    if (event.target.files && event.target.files.length) {
      const [file] = event.target.files;
      reader.readAsDataURL(file);

      reader.onload = () => {

        this.imageSrc = reader.result as string;

        this.adFormGroup.patchValue({
          fileSource: reader.result
        });

      };


      const storageFile = event.target.files[0];
      const uuid = uuidv4();
      const filePath = `user/${this.addedAd.user.id}/ads/${uuid}`;
      const fileRef = this.storage.ref(filePath);
      const task = this.storage.upload(filePath, storageFile);
      task.snapshotChanges()
        .pipe(
          finalize(() => {
            this.downloadURL = fileRef.getDownloadURL();
            this.downloadURL.subscribe(url => {
              if (url) {
                this.firebaseLink = url;
                this.adLinks.push(this.firebaseLink);
              }
            });

          })
        )
        .subscribe(url => {
          if (url) {

          }
        });
    }

  }

  deleteFirebaseImages() {
    this.adLinks.forEach((element) => {
      this.storage.refFromURL(element).delete();
    });
  }

  placeAd(cityId: number) {
    this.addedAd.address.city.id = cityId;
    if (!this.editMode) {

      this.adFormService.placeAd(this.addedAd).subscribe(
        {
          next: this.placeImages('You added an offer'),
          error: this.placeError()
        }
      );
    } else {
      this.adFormService.updateAd(this.addedAd, +this.adId).subscribe(
        {
          next: this.placeImages('You updated an offer'),
          error: this.placeError()
        }
      );

    }

  }

  placeError() {
    return err => {
      this.deleteFirebaseImages();
      alert(err.message);
    };
  }

  placeImages(text: string) {
    return response => {

      this.createdAdId = this.editMode ? +this.adId : response.id;
      this.adFormService.placeImages(this.createdAdId, this.adLinks).subscribe(
        {
          next: response => {
          },
          error: this.placeError()
        }
      );

      alert(text);
      this.router.navigateByUrl('/');

    };
  }

}
