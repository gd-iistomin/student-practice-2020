import { Component, OnInit } from '@angular/core';
import { Order } from 'src/app/common/order';
import { UserDetails } from 'src/app/common/user-details';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserOrdersService } from 'src/app/services/user-orders.service';

@Component({
  selector: 'app-user-orders-list',
  templateUrl: './user-orders-list.component.html',
  styleUrls: ['./user-orders-list.component.css']
})
export class UserOrdersListComponent implements OnInit {

  userDetails: UserDetails;

  orders: Order[];

  constructor(private authenticationService: AuthenticationService, private userOrdersService: UserOrdersService) { }

  ngOnInit(): void {

    // get user details on init
    this.authenticationService.userDetails.subscribe(data => {this.userDetails = data});

    // get user orders
    this.userOrdersService.getUserOrders(this.userDetails.customerId).subscribe(data => {this.orders = data});

    
  }



}
