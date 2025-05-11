import { Injectable } from '@angular/core';
import { IUserLogin } from '../../dtos/IUserLogin';
import { HttpClient } from '@angular/common/http';
import { IUser } from '../../dtos/IUser';
import { endpointAPI } from '../../config/appconfig';
import { Observable } from 'rxjs';
import { IUserSignup } from '../../dtos/IUserSignup';

@Injectable({
    providedIn: 'root',
})
export class UserService {
    loggedUser: IUser | undefined = undefined;
    constructor(private http: HttpClient) {}

    getLoggedUserDetails(credentials: IUserLogin): Observable<IUser> {
        return this.http.post<IUser>(endpointAPI + 'user/login', credentials);
    }

    setLoggedUser(loggedUser: IUser) {
        this.loggedUser = loggedUser;
    }

    getLoggedUser() {
        return this.loggedUser;
    }

    signUpUser(credentials: IUserSignup): Observable<IUser> {
        return this.http.post<IUser>(endpointAPI + 'user/signup', credentials);
    }

    updateUser(credentials: IUserSignup): Observable<IUser> {
        return this.http.put<IUser>(endpointAPI + 'user', credentials);
    }

    deleteUser(email: string): Observable<IUser> {
        return this.http.delete<IUser>(endpointAPI + 'user/' + email);
    }

    getLoggedUserId() {
        return this.loggedUser?.id;
    }
}
