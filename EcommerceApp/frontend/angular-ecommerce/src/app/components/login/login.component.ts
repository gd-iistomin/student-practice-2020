import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';



import { from } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginFormGroup: FormGroup;

  constructor(private formBuilder: FormBuilder) { 
  }

  ngOnInit(): void {

    // creating login form
    this.loginFormGroup = this.formBuilder.group({
        // all fields in the form are represented by FormControl, we specify initial value: '' and validators for the given field. 
        firstName: new FormControl(''),
        lastName: new FormControl(''),
        email: new FormControl('')
        })
    
  }

}
