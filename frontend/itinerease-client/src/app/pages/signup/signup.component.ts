import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IUserSignup } from '../../dtos/IUserSignup';
import { Router } from '@angular/router';
import { UserService } from '../../services/user-service/user.service';
import { error } from 'console';
import { IUser } from '../../dtos/IUser';

@Component({
    selector: 'app-signup',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './signup.component.html',
    styleUrl: './signup.component.scss',
})
export class SignupComponent {
    hidePassword: boolean = true;
    firstName: string = '';
    lastName: string = '';
    phoneNumber: string = '';
    email: string = '';
    password: string = '';
    confirmPassword: string = '';
    errorMessage: string = '';
    user: IUser | undefined;
    constructor(
        private router: Router,
        private userService: UserService,
    ) {}

    togglePasswordVisibility(): void {
        this.hidePassword = !this.hidePassword;
    }

    signup() {
        if (this.confirmPassword === this.password) {
            var userSignUpdata: IUserSignup = {
                firstName: this.firstName,
                lastName: this.lastName,
                phoneNumber: this.phoneNumber,
                email: this.email,
                password: this.password,
            };
            this.userService.signUpUser(userSignUpdata).subscribe(
                (response: any) => {
                    this.user = {
                        id: response.id,
                        firstName: response.first_name,
                        lastName: response.last_name,
                        email: response.email,
                        password: response.password,
                        phoneNumber: response.phone_number,
                    };
                    this.userService.setLoggedUser(this.user);
                    this.router.navigate(['/homepage']);
                },
                (error: any) => {
                    this.errorMessage = error.error.message;
                },
            );
        }
    }
}
