import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';



import { from } from 'rxjs';
import { UserDetails } from 'src/app/common/user-details';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginFormGroup: FormGroup;

  username: string;

  authSuccess: boolean = null;

  requestStatusCode: number;



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
        });

        // subscribe to auth value
        
    
  }

  login(){

    // save username to component field(to populate greeting)
    this.username = this.loginFormGroup.controls['username'].value;

    //get credentials from form
    const credentials = {username: this.username, password: this.loginFormGroup.controls['password'].value};
    
    this.authenticationService.authenticate(credentials,  () => {this.router.navigateByUrl('/');});
    
    this.authenticationService.userDetails.subscribe(data => {console.log(data)});

    this.authenticationService.authenticated.subscribe(data => {this.authSuccess = data})
    
    
  }

  authenticated() { return this.authenticationService.authenticated.value; }

}
