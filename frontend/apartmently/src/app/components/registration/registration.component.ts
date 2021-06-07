import {Component, OnInit} from '@angular/core';
import {AngularFireStorage} from '@angular/fire/storage';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {finalize} from 'rxjs/operators';
import {UserInfo} from 'src/app/common/user-info';
import {AuthenticationService} from 'src/app/services/authentication.service';
import {UserService} from 'src/app/services/user.service';
import {v4 as uuidv4} from 'uuid';

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
  userInfo: UserInfo = new UserInfo();

  editMode = false;


  constructor(private formBuilder: FormBuilder, private router: Router, private storage: AngularFireStorage,
              private authService: AuthenticationService, private userService: UserService) {
  }

  get f() {
    return this.registrationFormGroup.controls;
  }

  ngOnInit(): void {

    this.registrationFormGroup = this.formBuilder.group({
      name: new FormControl('', [Validators.required, Validators.minLength(2)]),
      surname: new FormControl('', [Validators.required, Validators.minLength(2)]),
      phoneNumber: new FormControl('', [Validators.required, Validators.pattern('[0-9]{0,14}$')]),

    });

    if (this.router.url === '/update-info') {
      this.editMode = true;
      this.userInfo.userId = this.authService.currentUserValue.id;
      this.userService.getUserInfo(this.userInfo.userId).subscribe(
        data => {
          this.userInfo.name = data.name;
          this.userInfo.surname = data.surname;
          this.userInfo.phoneNumber = data.phoneNumber;

          this.registrationFormGroup.patchValue({
            name: this.userInfo.name,
            surname: this.userInfo.surname,
            phoneNumber: this.userInfo.phoneNumber

          });
        }
      );

    }

  }

  onSubmit() {
    if (this.registrationFormGroup.invalid) {
      this.registrationFormGroup.markAllAsTouched();
      return;
    }
    this.userInfo.userId = this.authService.currentUserValue.id;
    this.userInfo.name = this.f.name.value;
    this.userInfo.surname = this.f.surname.value;
    this.userInfo.phoneNumber = this.f.phoneNumber.value;
    if (this.userInfo.imageUrl) {
      this.userService.getImage.next(this.userInfo.imageUrl);
    }

    if (!this.editMode) {
      this.userService.saveUserInfo(this.userInfo).subscribe({
        next: this.getResponse(),
        error: this.getError()
      });
    } else {
      this.userService.updateUserInfo(this.userInfo).subscribe({
        next: this.getResponse(),
        error: this.getError()
      });
    }
  }

  getResponse() {
    return response => {
      alert(response.message);
      this.router.navigateByUrl('/');
    };
  }

  getError() {

    return err => {
      this.deleteFirebaseImage();
      alert(err.message);
    };
  }

  onFileChange(event): void {
    const reader = new FileReader();

    if (event.target.files && event.target.files.length) {
      const [file] = event.target.files;
      reader.readAsDataURL(file);

      reader.onload = () => {

        this.imageSrc = reader.result as string;

        this.registrationFormGroup.patchValue({
          fileSource: reader.result
        });

      };

      const storageFile = event.target.files[0];
      const uuid = uuidv4();
      const filePath = `user/${this.authService.currentUserValue.id}/${uuid}`;
      const fileRef = this.storage.ref(filePath);
      const task = this.storage.upload(filePath, storageFile);
      task.snapshotChanges()
        .pipe(
          finalize(() => {
            this.downloadURL = fileRef.getDownloadURL();
            this.downloadURL.subscribe(url => {
              if (url) {
                this.firebaseLink = url;
                this.userInfo.imageUrl = this.firebaseLink;
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

  deleteFirebaseImage(): void {
    this.storage.refFromURL(this.firebaseLink).delete();
  }
}
