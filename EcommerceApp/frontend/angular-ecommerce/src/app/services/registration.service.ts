import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { User } from '../common/user';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  requestStatusCode: number;

  constructor(private httpClient: HttpClient) { }

  // post new User object to backend
  createUser(theUser: User): Observable<HttpResponse<any>> {

    const userCreationUrl = `http://localhost:8000/api/users`;

    return this.httpClient.post<User>(userCreationUrl, theUser, {observe: 'response'});
  }

}
