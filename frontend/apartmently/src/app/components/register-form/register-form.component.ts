import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { MyValidators } from 'src/app/validators/my-validators';


@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css']
})
export class RegisterFormComponent implements OnInit {

  registerFormGroup: FormGroup;
  error: string;

  constructor(private formBuilder: FormBuilder, private router: Router, private authenticationService: AuthenticationService) {
  }

  ngOnInit(): void {
    this.registerFormGroup = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]),
      username: new FormControl('', [Validators.required, Validators.minLength(3)]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)]),
      passwordConfirm: new FormControl('', [Validators.required, Validators.minLength(6)])
    }, {
      validator: MyValidators.mustMatch('password', 'passwordConfirm')
    });
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.registerFormGroup.controls;
  }


  onSubmit() {
    if (this.registerFormGroup.invalid) {
      this.registerFormGroup.markAllAsTouched();
      return;
    }

    this.authenticationService.register(this.f.username.value, this.f.email.value, this.f.password.value)
      .subscribe(val => {
        alert(val.message);
        this.router.navigateByUrl('/login');
      },
        err => {
          this.error = err;
        });
  }

}
