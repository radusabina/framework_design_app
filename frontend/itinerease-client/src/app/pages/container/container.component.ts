import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user-service/user.service';
import { ItineraryService } from '../../services/itinerary-service/itinerary.service';
import { IItineraryHomepage } from '../../dtos/IItineraryHomepage';
import { ConstantLinks } from '../../constants/images';
import { Router } from '@angular/router';
import { IUser } from '../../dtos/IUser';
import { ModalsComponent } from '../modals/modals.component';

@Component({
    selector: 'app-container',
    standalone: true,
    imports: [CommonModule, ModalsComponent],
    templateUrl: './container.component.html',
    styleUrl: './container.component.scss',
})
export class ContainerComponent {
    loggedUser: IUser | undefined;
    itineraries: IItineraryHomepage[] = [];
    cities: any[] = [];

    constructor(
        private userService: UserService,
        private itineraryService: ItineraryService,
        private router: Router,
    ) {}

    ngOnInit() {
        this.refreshTasks();
    }

    refreshTasks() {
        this.loggedUser = this.userService.getLoggedUser();
        if (this.loggedUser) {
            this.itineraryService.loadItineraries(this.loggedUser);
            this.itineraryService.itineraries$.subscribe(
                (itineraries) => {
                    this.itineraries = itineraries;
                },
                (error) => {},
            );
        }
    }

    calculateDaysBetweenDates(arrivalDate: Date, departureDate: Date): number {
        const oneDay = 24 * 60 * 60 * 1000; // hours * minutes * seconds * milliseconds

        const arrivalTime = arrivalDate.getTime();
        const departureTime = departureDate.getTime();

        const differenceInDays = Math.round(
            Math.abs((arrivalTime - departureTime) / oneDay),
        );

        return differenceInDays;
    }

    calculateDays(itinerary: IItineraryHomepage): number {
        const arrivalDate = new Date(
            itinerary.arrivalDate[0],
            itinerary.arrivalDate[1] - 1,
            itinerary.arrivalDate[2],
        );
        const departureDate = new Date(
            itinerary.departureDate[0],
            itinerary.departureDate[1] - 1,
            itinerary.departureDate[2],
        );

        return this.calculateDaysBetweenDates(arrivalDate, departureDate);
    }

    getDestinationImage(city: string) {
        switch (city) {
            case 'Cluj':
                return ConstantLinks.Cluj;
            case 'Constanta':
                return ConstantLinks.Constanta;
            case 'Iasi':
                return ConstantLinks.Iasi;
            case 'Timisoara':
                return ConstantLinks.Timisoara;
            case 'Chicago':
                return ConstantLinks.Chicago;
            case 'Houston':
                return ConstantLinks.Houston;
            case 'Los Angeles':
                return ConstantLinks.LosAngeles;
            case 'Miami':
                return ConstantLinks.Miami;
            case 'New York':
                return ConstantLinks.NewYork;
            case 'Berlin':
                return ConstantLinks.Berlin;
            case 'Cologne':
                return ConstantLinks.Cologne;
            case 'Frankfurt':
                return ConstantLinks.Frankfurt;
            case 'Hamburg':
                return ConstantLinks.Hamburg;
            case 'Munich':
                return ConstantLinks.Munich;
            case 'Barcelona':
                return ConstantLinks.Barcelona;
            case 'Bilbao':
                return ConstantLinks.Bilbao;
            case 'Madrid':
                return ConstantLinks.Madrid;
            case 'Sevilla':
                return ConstantLinks.Sevilla;
            case 'Valencia':
                return ConstantLinks.Valencia;
            case 'Lyon':
                return ConstantLinks.Lyon;
            case 'Marseille':
                return ConstantLinks.Marseille;
            case 'Nice':
                return ConstantLinks.Nice;
            case 'Paris':
                return ConstantLinks.Paris;
            case 'Toulouse':
                return ConstantLinks.Toulouse;
            case 'Florence':
                return ConstantLinks.Florence;
            case 'Milan':
                return ConstantLinks.Milan;
            case 'Naples':
                return ConstantLinks.Naples;
            case 'Roma':
                return ConstantLinks.Roma;
            case 'Venice':
                return ConstantLinks.Venice;
            case 'Hiroshima':
                return ConstantLinks.Hiroshima;
            case 'Kyoto':
                return ConstantLinks.Kyoto;
            case 'Nagoya':
                return ConstantLinks.Nagoya;
            case 'Osaka':
                return ConstantLinks.Osaka;
            case 'Tokyo':
                return ConstantLinks.Tokyo;
            case 'Bucuresti':
                return ConstantLinks.Bucuresti;
            default:
                return;
        }
    }

    openItinerary(id: number) {
        this.router.navigate([`/itinerary/${id}`]);
    }
}
