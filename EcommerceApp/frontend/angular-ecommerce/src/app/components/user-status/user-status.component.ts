import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-user-status',
  templateUrl: './user-status.component.html',
  styleUrls: ['./user-status.component.css']
})
export class UserStatusComponent implements OnInit {

  username: string = 'blah';

  constructor(private authenticationService: AuthenticationService, private http: HttpClient, private router: Router) { 
      
  }

  ngOnInit(): void {
  }

  logout() {

    const logoutLink = 'http://localhost:8000/logout'
    this.http.post(logoutLink, {});

        this.authenticationService.authenticated.next(false);
        this.router.navigateByUrl('/products');
  }

  showUserOrders(){
    this.router.navigateByUrl(`/show-user-orders`)
  }

}
