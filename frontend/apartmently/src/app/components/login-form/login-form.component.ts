import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {first} from 'rxjs/operators';
import {UserInfo} from 'src/app/common/user-info';
import {AuthenticationService} from 'src/app/services/authentication.service';
import {UserService} from 'src/app/services/user.service';


@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  loginFormGroup: FormGroup;
  loading = false;
  submitted = false;
  error = '';
  userInfo: UserInfo;


  constructor(private formBuilder: FormBuilder, private router: Router,
              private authenticationService: AuthenticationService, private route: ActivatedRoute,
              private userService: UserService) {

    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit(): void {

    this.loginFormGroup = this.formBuilder.group({
        email: new FormControl('', [Validators.required]),
        password: new FormControl('', [Validators.required])

      }
    );
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.loginFormGroup.controls;
  }

  onSubmit() {
    this.submitted = true;
    if (this.loginFormGroup.invalid) {
      this.loginFormGroup.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.authenticationService.login(this.f.email.value, this.f.password.value)
      .pipe(first())
      .subscribe({
        next: () => {
          // get return url from route parameters or default to '/'
          this.userService.getUserInfo(this.authenticationService.currentUserValue.id).subscribe({
            next: response => {
              this.userInfo = response;
              this.userService.getImage.next(this.userInfo.imageUrl);


              if (this.authenticationService.currentUserValue.roles.includes('ADMIN')) {
                this.router.navigateByUrl('/admin-panel');
              }
            },
            error: err => {
              this.router.navigateByUrl('/full-info');
            }
          });

          const returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
          this.router.navigate([returnUrl]);
        },
        error: error => {
          this.error = error;
          this.loading = false;
        }
      });

  }
}
