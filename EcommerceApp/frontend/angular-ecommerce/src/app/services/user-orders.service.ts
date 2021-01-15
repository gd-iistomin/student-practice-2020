import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Order } from '../common/order';

@Injectable({
  providedIn: 'root'
})
export class UserOrdersService {

  constructor(private httpClient: HttpClient) { }

  getUserOrders(theCustomerId: number): Observable<Order[]> {
    const userOrdersUrl = `http://localhost:8000/api/orders/${theCustomerId}`;

    return this.httpClient.get<Order[]>(userOrdersUrl);
  }
}
