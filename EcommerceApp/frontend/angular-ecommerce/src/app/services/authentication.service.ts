import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {


  authenticated = false;
  username: string;
  authority: string = '';

  constructor(private http: HttpClient) {
  }

  authenticate(credentials, callback) {

        const authenticationLink = 'http://localhost:8000/user';

        const headers = new HttpHeaders(credentials ? {
            authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
        } : {});

        this.http.get(authenticationLink, {headers: headers}).subscribe(response => {
            if (response['name']) {
                this.authenticated = true;
                this.username = response['name'];

                const authorities: string[] = response['authorities']; 
                if (authorities.length > 0){
                    this.authority = authorities[0];
                }

                return callback && callback();

            } else {
                this.authenticated = false;
                
            }
            
        });


    
  }

  
}
