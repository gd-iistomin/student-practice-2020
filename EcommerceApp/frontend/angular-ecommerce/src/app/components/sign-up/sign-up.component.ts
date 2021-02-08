import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/common/user';
import { RegistrationService } from 'src/app/services/registration.service';
import { SignUpFormValidators } from 'src/app/validators/sign-up-form-validators';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  signUpFormGroup: FormGroup;

  requestStatusCode: number = 0;

  responseMessage: string = '';



  constructor(private formBuilder: FormBuilder, private registrationService: RegistrationService, private router: Router) { }

  ngOnInit(): void {

    // creating signup form
    this.signUpFormGroup = this.formBuilder.group({
      // all fields in the form are represented by FormControl, we specify initial value: '' and validators for the given field. 
      firstName: new FormControl('', [Validators.required, Validators.minLength(2), SignUpFormValidators.notOnlyWhitespace]),
      lastName: new FormControl('', [Validators.required, Validators.minLength(2), SignUpFormValidators.notOnlyWhitespace]),
      email: new FormControl('', [Validators.required, Validators.pattern('[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]),
      username: new FormControl('', [Validators.required, Validators.minLength(4), SignUpFormValidators.notOnlyWhitespace]),
      password: new FormControl('', [Validators.required, 
                                     Validators.pattern("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")]),

      })

  }

  signUp(){
    // on sign up click, check if some fields are invalid, if so, mark all as touched, it will invoke our error messages 
    if(this.signUpFormGroup.invalid){
      this.signUpFormGroup.markAllAsTouched();
      return;
    }

    // create new User object and populate with data from form
    let user = new User();
    user.firstName = this.signUpFormGroup.controls['firstName'].value;
    user.lastName = this.signUpFormGroup.controls['lastName'].value;
    user.email = this.signUpFormGroup.controls['email'].value;
    user.username = this.signUpFormGroup.controls['username'].value;
    user.password = this.signUpFormGroup.controls['password'].value;

    //set default role of "USER" and discount rate of "starter"
    user.authority = "USER";
    user.discountRate = "STARTER";

    // post User via registration service
    // get the status code:
    this.registrationService.createUser(user).subscribe( Response => 
      {
        this.requestStatusCode = Response.status;
        this.responseMessage = Response.body.message;
        

    });


    // if it's 201(Status code == CREATED), then show user message that user successfully created,
    //  redirect user to main pade
    if(this.requestStatusCode == 201){
      this.router.navigateByUrl("/products");
      alert('User has been created!');
    }


    // if 226(Status code == IM_USED), then show alert that user with matchinh username/email already exists (ngIf in html)

    
  }

  get firstName() { return this.signUpFormGroup.get('firstName'); }
  get lastName() { return this.signUpFormGroup.get('lastName'); }
  get email() { return this.signUpFormGroup.get('email'); }
  get username() { return this.signUpFormGroup.get('username'); }
  get password() { return this.signUpFormGroup.get('password'); }
  

}
