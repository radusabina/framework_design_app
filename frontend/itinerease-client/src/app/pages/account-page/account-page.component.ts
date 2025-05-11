import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgForm } from '@angular/forms';
import { IUser } from '../../dtos/IUser';
import { UserService } from '../../services/user-service/user.service';
import { FormsModule } from '@angular/forms';
import { IUserSignup } from '../../dtos/IUserSignup';
import { Router } from '@angular/router';

@Component({
    selector: 'app-account-page',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './account-page.component.html',
    styleUrl: './account-page.component.scss',
})
export class AccountPageComponent {
    //asta e entitatea ce o primesc din back
    loggedUser: IUser | undefined;
    //asta e entitatea ce o primesc ca raspuns din back
    user: IUser | undefined;

    //var pt butonu ala gen creion
    enableFirstNameEdit: boolean = false;
    enableLastNameEdit: boolean = false;
    enablePhoneNumberEdit: boolean = false;
    enableEmailEdit: boolean = false;
    enablePasswordEdit: boolean = false;
    passwordVisible: boolean = false;

    //user details
    firstName: string = '';
    lastName: string = '';
    phoneNumber: string = '';
    email: string = '';
    password: string = '';

    constructor(
        private router: Router,
        private userService: UserService,
    ) {}

    ngOnInit(): void {
        this.loggedUser = this.userService.getLoggedUser();
        this.firstName = this.loggedUser?.firstName ?? '';
        this.lastName = this.loggedUser?.lastName ?? '';
        this.phoneNumber = this.loggedUser?.phoneNumber ?? '';
        this.email = this.loggedUser?.email ?? '';
        this.password = this.loggedUser?.password ?? '';
    }

    //functie pentru vizibilitatea parolei
    //ar merge o functie sa isi schimbe culoarea cand se poate edita
    togglePasswordVisibility(): void {
        this.passwordVisible = !this.passwordVisible;
    }
    //functia de update pentru user
    modifyUser() {
        var userSignup: IUserSignup = {
            firstName: this.firstName,
            lastName: this.lastName,
            phoneNumber: this.phoneNumber,
            email: this.email,
            password: this.password,
        };
        this.userService.updateUser(userSignup).subscribe(
            (response: any) => {
                console.log(response);
                this.user = {
                    id: response.id,
                    firstName: response.first_name,
                    lastName: response.last_name,
                    email: response.email,
                    password: response.password,
                    phoneNumber: response.phone_number,
                };
                console.log(this.user);
                this.userService.setLoggedUser(this.user);
                this.router.navigate(['/homepage']);
            },
            (error: any) => {
                console.log(error);
            },
        );
    }
    logOf() {
        this.router.navigate(['/login']);
    }

    deleteUser() {
        this.userService.deleteUser(this.email).subscribe(
            (response: any) => {
                console.log('a mers!');
                this.router.navigate(['/login']);
            },
            (error: any) => {},
        );
    }
    backToHomePage() {
        this.router.navigate(['/homepage']);
    }
}
