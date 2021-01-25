import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of} from 'rxjs';
import { UserDetails } from '../common/user-details';
import { LoginComponent } from '../components/login/login.component';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {


  authenticated: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  username: string;
  authority: BehaviorSubject<string> = new BehaviorSubject<string>('none');

  userDetails: Observable<UserDetails> = of(null);

  constructor(private http: HttpClient) {
  }

  authenticate(credentials, callback) {

        const authenticationLink = 'http://localhost:8000/user';

        const headers = new HttpHeaders(credentials ? {
            authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
        } : {});

        this.http.get(authenticationLink, {headers: headers}).subscribe(response => {
            if (response['name']) {
                this.authenticated.next(true);
                this.username = response['name'];
                // on success, get userDetails
                this.getUserDetails(credentials, this.username);

                const authorities: string[] = response['authorities']; 
                if (authorities.length > 0){
                    this.authority.next(authorities[0]['authority']);
                }

                return callback && callback();

            } else {
                this.authenticated.next(false); 
            } 
        });
  }


  getUserDetails(credentials, theUsername: string){
        const userDetailsLink = `http://localhost:8000/userdetails/${theUsername}`;

        const headers = new HttpHeaders(credentials ? {
            authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
        } : {});

        this.http.get<UserDetails>(userDetailsLink, {headers: headers}).subscribe(data => {
            this.userDetails = of(data);
        })

        
  }

  
}
