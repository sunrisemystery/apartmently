import {FormControl, FormGroup, ValidationErrors} from "@angular/forms";

export class MyValidators {

  static checkPasswords(control: FormControl): ValidationErrors {

    const password = control.get('password').value;
    const confirmPassword = control.get('passwordConfirm').value;

    return password === confirmPassword ? null : {'notSame': true};
  }


  static mustMatch(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
      const control = formGroup.controls[controlName];
      const matchingControl = formGroup.controls[matchingControlName];

      if (matchingControl.errors && !matchingControl.errors.mustMatch) {
        // return if another validator has already found an error on the matchingControl
        return;
      }
      control.value === matchingControl.value ? matchingControl.setErrors(null) : matchingControl.setErrors({mustMatch: true});

    };
  }
}
