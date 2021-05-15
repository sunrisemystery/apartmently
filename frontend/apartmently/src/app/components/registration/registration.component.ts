import { Component, OnInit } from '@angular/core';
import { AngularFireStorage } from '@angular/fire/storage';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  registrationFormGroup: FormGroup;
  imageSrc: string;
  selectedFile: File = null;
  downloadURL: Observable<string>;
  firebaseLink: string;


  constructor(private formBuilder: FormBuilder, private router: Router, private storage: AngularFireStorage) { }

  ngOnInit(): void {

    this.registrationFormGroup = this.formBuilder.group({
      name: new FormControl('', [Validators.required]),
      surname: new FormControl('', [Validators.required]),
      phoneNumber: new FormControl('', [Validators.required]),

    })
  }

  onSubmit() {
    if (this.registrationFormGroup.invalid) {
      this.registrationFormGroup.markAllAsTouched();
      return;
    }

  }
}
