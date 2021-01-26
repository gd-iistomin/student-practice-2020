import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-item-creation',
  templateUrl: './item-creation.component.html',
  styleUrls: ['./item-creation.component.css']
})
export class ItemCreationComponent implements OnInit {

  constructor(private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
  }

  authenticated() { return this.authenticationService.authenticated.value; }

  getAuthority() {
     return this.authenticationService.authority.value;
  }

}
