import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HomepageComponent } from '../homepage/homepage.component';
import { Router } from '@angular/router';
import { UserService } from '../../services/user-service/user.service';
import { IUserLogin } from '../../dtos/IUserLogin';
import { IUser } from '../../dtos/IUser';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [CommonModule, FormsModule, HomepageComponent],
    templateUrl: './login.component.html',
    styleUrl: './login.component.scss',
})
export class LoginComponent {
    hidePassword: boolean = true;
    password: string = '';
    email: string = '';
    errorMessage: string = '';
    user: IUser | undefined;
    constructor(
        private router: Router,
        private userService: UserService,
    ) {}
    ngOnInit(): void {}

    togglePasswordVisibility(): void {
        this.hidePassword = !this.hidePassword;
    }

    login() {
        var userLoginData: IUserLogin = {
            email: this.email,
            password: this.password,
        };
        this.userService.getLoggedUserDetails(userLoginData).subscribe(
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
