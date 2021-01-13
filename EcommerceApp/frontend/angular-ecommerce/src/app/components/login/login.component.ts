import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';



import { from } from 'rxjs';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginFormGroup: FormGroup;

  username: string;

  authSuccess: boolean = true;

  constructor(private formBuilder: FormBuilder,
              private authenticationService: AuthenticationService,
              private router: Router) { 
  }  

  ngOnInit(): void {

    // creating login form
    this.loginFormGroup = this.formBuilder.group({
        // all fields in the form are represented by FormControl, we specify initial value: '' and validators for the given field. 
        username: new FormControl(''),
        password: new FormControl('')
        })
    
  }

  login(){

    // save username to component field(to populate greeting)
    this.username = this.loginFormGroup.controls['username'].value;

    //get credentials from form
    const credentials = {username: this.username, password: this.loginFormGroup.controls['password'].value};
    
    this.authenticationService.authenticate(credentials,  () => {this.router.navigateByUrl('/');});

    // if auth unsuccessful, display error message
    this.authSuccess = this.authenticationService.authenticated;

    
  }

}
