import { Component, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterOutlet } from '@angular/router';
import {
    NgbAlertModule,
    NgbDatepickerModule,
    NgbDateStruct,
} from '@ng-bootstrap/ng-bootstrap';
import { FormControl, FormsModule, NgForm, Validators } from '@angular/forms';
import { JsonPipe } from '@angular/common';
import { IItineraryInsert } from '../../dtos/IItineraryInsert';
import { ItineraryService } from '../../services/itinerary-service/itinerary.service';
import { Router } from '@angular/router';
import { UserService } from '../../services/user-service/user.service';
import { ContainerComponent } from '../container/container.component';

@Component({
    selector: 'app-modals',
    standalone: true,
    imports: [
        CommonModule,
        RouterOutlet,
        NgbDatepickerModule,
        NgbAlertModule,
        FormsModule,
        JsonPipe,
        ContainerComponent,
    ],
    templateUrl: './modals.component.html',
    styleUrls: ['./modals.component.scss'],
})
export class ModalsComponent {
    title = 'itinerease-client';

    //Itinerary details
    itinerary: IItineraryInsert | undefined;
    itineraryName: string = '';

    //Date details
    dateStartModal!: NgbDateStruct;
    dateEndModal!: NgbDateStruct;
    minDateStart: NgbDateStruct;
    minDateEnd: NgbDateStruct;

    //Budget details
    budget: number | undefined;

    //Number of person details
    selectedPersonsOption: number | undefined;

    //Destination and departing details
    selectedCountryDestination: string = '';
    selectedCityDestination: string = '';
    selectedCountryDeparting: string = '';
    selectedCityDeparting: string = '';

    // Transport details
    transportType: string = '';
    transportPrice: number | undefined;

    //Accomodation details
    accomodationName: string = '';

    //Addres accomodation details
    addressArea: string = '';

    //Price per night details
    priceAccomodation: number | undefined;

    //constructor
    constructor(
        private router: Router,
        private itineraryService: ItineraryService,
        private route: ActivatedRoute,
        private userService: UserService,
        private containerComponent: ContainerComponent,
    ) {
        const currentDate = new Date();
        this.minDateStart = {
            year: currentDate.getFullYear(),
            month: currentDate.getMonth() + 1,
            day: currentDate.getDate(),
        };
        this.minDateEnd = {
            year: currentDate.getFullYear(),
            month: currentDate.getMonth() + 1,
            day: currentDate.getDate(),
        };
    }

    insertItinerary(form: NgForm) {
        var itinerary: IItineraryInsert = {
            itineraryName: this.itineraryName,
            dateStartModal: this.dateStartModal,
            dateEndModal: this.dateEndModal,
            budget: this.budget,
            selectedPersonsOption: this.selectedPersonsOption,
            selectedCountryDestination: this.selectedCountryDestination,
            selectedCityDestination: this.selectedCityDestination,
            selectedCountryDeparting: this.selectedCountryDeparting,
            selectedCityDeparting: this.selectedCityDeparting,
            transportType: this.transportType,
            transportPrice: this.transportPrice,
            accommodationName: this.accomodationName,
            addressArea: this.addressArea,
            priceAccommodation: this.priceAccomodation,
            idUser: this.userService.getLoggedUserId(),
        };

        this.itineraryService.insertItinerary(itinerary).subscribe(
            (response: any) => {
                //ce vr sa fac dupa ce o raspuns topy la telefon in cazul pozitiv , da?
                // in cazul pozitiv in care itinerarul e valid
                //to do de la tudor

                //reset input fields
                //modal1
                this.budget = undefined;
                this.itineraryName = '';
                this.selectedPersonsOption = undefined;

                //modal2
                this.selectedCountryDestination = '';
                this.selectedCityDestination = '';
                this.selectedCountryDeparting = '';
                this.selectedCityDeparting = '';
                this.transportType = '';
                this.transportPrice = undefined;

                //modal3
                this.accomodationName = '';
                this.addressArea = '';
                this.priceAccomodation = undefined;

                form.resetForm();
                this.containerComponent.refreshTasks();
            },
            (error: any) => {
                //ce se intampla in caz de eroareeeeeee :(
                //daca userul nu baga bine datele sau e ceva eroare
                form.resetForm();
            },
        );
    }
    // Obiect care mapează țările la listele lor de orașe
    countryCities: { [key: string]: string[] } = {
        Romania: ['Cluj', 'Bucuresti', 'Timisoara', 'Iasi', 'Constanta'],
        Germany: ['Berlin', 'Hamburg', 'Munich', 'Cologne', 'Frankfurt'],
        USA: ['New York', 'Los Angeles', 'Chicago', 'Houston', 'Miami'],
        France: ['Paris', 'Marseille', 'Lyon', 'Toulouse', 'Nice'],
        Spain: ['Madrid', 'Barcelona', 'Valencia', 'Seville', 'Bilbao'],
        Italy: ['Rome', 'Milan', 'Venice', 'Florence', 'Naples'],
        Japan: ['Tokyo', 'Osaka', 'Kyoto', 'Hiroshima', 'Nagoya'],
        // Adăugați alte țări și orașe aici conform necesităților
    };
    // Functie care returneaza doar orasele din tara selectata
    onCountryDestinationChange() {
        const selectedCities =
            this.countryCities[this.selectedCountryDestination];
        if (selectedCities) {
            // Actualizați lista de orașe pentru țara selectată
            // Această actualizare poate fi făcută direct în HTML fără a adăuga manual opțiunile
            return selectedCities;
        }
        return [];
    }

    // Functie care returneaza doar orasele din tara selectata
    onCountryChangeDeparting() {
        const selectedCities =
            this.countryCities[this.selectedCountryDeparting];
        if (selectedCities) {
            // Actualizați lista de orașe pentru țara selectată
            // Această actualizare poate fi făcută direct în HTML fără a adăuga manual opțiunile
            return selectedCities;
        }
        return [];
    }
}
