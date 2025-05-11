import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../navbar/navbar.component';
import { ContainerComponent } from '../container/container.component';

@Component({
    selector: 'app-homepage',
    standalone: true,
    imports: [CommonModule, NavbarComponent, ContainerComponent],
    templateUrl: './homepage.component.html',
    styleUrl: './homepage.component.scss',
})
export class HomepageComponent {}
