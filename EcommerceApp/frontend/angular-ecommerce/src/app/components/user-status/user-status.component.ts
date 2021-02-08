import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-user-status',
  templateUrl: './user-status.component.html',
  styleUrls: ['./user-status.component.css']
})
export class UserStatusComponent implements OnInit {

  username: string = 'blah';

  constructor(private authenticationService: AuthenticationService,
              private http: HttpClient,
              private router: Router,
              private cartService: CartService) { 
      
  }

  ngOnInit(): void {
  }

  logout() {

    const logoutLink = 'http://localhost:8000/logout'
    this.http.post(logoutLink, {});

        this.authenticationService.authenticated.next(false);
        this.resetCart();
        this.router.navigateByUrl('/products');
  }

  showUserOrders(){
    this.router.navigateByUrl(`/show-user-orders`)
  }

  resetCart() {
    // resets cart data
    this.cartService.cartItems = [];
    this.cartService.totalPrice.next(0);
    this.cartService.totalQuantity.next(0);
  }

}
