import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
    NgbCalendar,
    NgbDateStruct,
    NgbModule,
} from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute, Router } from '@angular/router';
import { IAttractionEditPage } from '../../dtos/IAttractionEditPage';
import { AttractionService } from '../../services/attraction-service/attraction.service';
import { IItineraryEditPage } from '../../dtos/IItineraryEditPage';
import { IItineraryUpdate } from '../../dtos/IItineraryUpdate';
import { ItineraryService } from '../../services/itinerary-service/itinerary.service';
import { ILocation } from '../../dtos/ILocation';
import { IAttractionAdd } from '../../dtos/IAttractionAdd';
import { DeleteConfirmationModalComponent } from '../delete-confirmation-modal/delete-confirmation-modal.component';

@Component({
    selector: 'app-itinerary-details-page',
    standalone: true,
    imports: [
        CommonModule,
        FormsModule,
        NgbModule,
        DeleteConfirmationModalComponent,
    ],
    templateUrl: './itinerary-details-page.component.html',
    styleUrl: './itinerary-details-page.component.scss',
})
export class ItineraryDetailsPageComponent {
    itinerary: IItineraryEditPage | undefined = undefined;

    itineraryId: number = 0;

    // Itinerary details
    itineraryName: string = '';
    startDate: NgbDateStruct = this.calendar.getToday();
    endDate: NgbDateStruct = this.calendar.getToday();
    budget: number | undefined = undefined;
    numberOfPersons: number | undefined = undefined;

    // Where from details
    whereFromCountry: string = '';
    whereFromCity: string = '';

    // Where to details
    whereToCountry: string = '';
    whereToCity: string = '';

    // Transport details
    transportType: string = '';
    transportPrice: number | undefined = undefined;

    // Accommodation details
    accommodationName: string = '';
    accommodationAddress: string = '';
    accommodationPrice: number | undefined = undefined;

    // Attraction details
    attractionName: string = '';
    attractionPrice: number | undefined = undefined;
    attractions: IAttractionEditPage[] | undefined = undefined;
    addAttractionFailed: boolean = false;

    minDate: NgbDateStruct;

    countryCities: { [key: string]: string[] } = {
        Romania: ['Cluj', 'Bucuresti', 'Timisoara', 'Iasi', 'Constanta'],
        Germany: ['Berlin', 'Hamburg', 'Munich', 'Cologne', 'Frankfurt'],
        USA: ['New York', 'Los Angeles', 'Chicago', 'Houston', 'Miami'],
        France: ['Paris', 'Marseille', 'Lyon', 'Toulouse', 'Nice'],
        Spain: ['Madrid', 'Barcelona', 'Valencia', 'Seville', 'Bilbao'],
        Italy: ['Rome', 'Milan', 'Venice', 'Florence', 'Naples'],
        Japan: ['Tokyo', 'Osaka', 'Kyoto', 'Hiroshima', 'Nagoya'],
    };

    constructor(
        private router: Router,
        private itineraryService: ItineraryService,
        private attractionService: AttractionService,
        private route: ActivatedRoute,
        private calendar: NgbCalendar,
    ) {
        this.attractions = [];

        const currentDate = new Date();
        this.minDate = {
            year: currentDate.getFullYear(),
            month: currentDate.getMonth() + 1,
            day: currentDate.getDate(),
        };
        this.attractions = [];
        this.route.params.subscribe((params) => {
            this.itineraryId = params['id'];
            this.loadItineraryDetails(this.itineraryId);
        });
    }

    ngOnInit() {}

    ngAfterViewInit() {
        this.populatePage();
    }

    onStartDateSelect(date: NgbDateStruct) {
        this.startDate = date;
    }

    onEndDateSelect(date: NgbDateStruct) {
        this.endDate = date;
    }

    onCountryChangeLeft() {
        const selectedCitiesLeft = this.countryCities[this.whereFromCountry];
        if (selectedCitiesLeft) {
            return selectedCitiesLeft;
        }
        return [];
    }

    onCountryChangeRight() {
        const selectedCitiesRight = this.countryCities[this.whereToCountry];
        if (selectedCitiesRight) {
            return selectedCitiesRight;
        }
        return [];
    }

    loadItineraryDetails(id: number) {
        this.itineraryService.getItineraryById(id).subscribe(
            (response: any) => {
                this.itinerary = {
                    id: response.id,
                    id_departure: response.departure_location.id,
                    departure_country: response.departure_location.country,
                    departure_city: response.departure_location.city,
                    id_destination: response.destination_location.id,
                    destination_country: response.destination_location.country,
                    destination_city: response.destination_location.city,
                    id_transport: response.transport.id,
                    transport_type: response.transport.type,
                    transport_price: response.transport.price,
                    id_user: response.user.id,
                    id_accommodation: response.accommodation.id,
                    accommodation_name: response.accommodation.name,
                    accommodation_address: response.accommodation.address,
                    accommodation_price: response.accommodation.price,
                    attractions: response.attractions,
                    name: response.name,
                    arrival_date: new Date(
                        response.arrival_date[0],
                        response.arrival_date[1],
                        response.arrival_date[2],
                    ),
                    departure_date: new Date(
                        response.departure_date[0],
                        response.departure_date[1],
                        response.departure_date[2],
                    ),
                    budget: response.budget,
                    persons: response.persons,
                };
                console.log(this.itinerary);
                this.populatePage();
            },
            (error) => {
                console.log(error);
            },
        );
    }

    populatePage() {
        if (this.itinerary) {
            // Itinerary details
            this.itineraryName = this.itinerary?.name;
            this.startDate = {
                year: this.itinerary?.departure_date.getFullYear(),
                month: this.itinerary?.departure_date.getMonth(),
                day: this.itinerary?.departure_date.getDay(),
            };
            this.endDate = {
                year: this.itinerary?.arrival_date.getFullYear(),
                month: this.itinerary?.arrival_date.getMonth(),
                day: this.itinerary?.arrival_date.getDay(),
            };
            this.budget = this.itinerary?.budget;
            this.numberOfPersons = this.itinerary?.persons;

            // Where from details
            this.whereFromCountry = this.itinerary?.departure_country;
            this.whereFromCity = this.itinerary?.departure_city;

            // Where to details
            this.whereToCountry = this.itinerary?.destination_country;
            this.whereToCity = this.itinerary?.destination_city;

            // Transport details
            this.transportType = this.itinerary?.transport_type;
            this.transportPrice = this.itinerary?.transport_price;

            // Accommodation details
            this.accommodationName = this.itinerary?.accommodation_name;
            this.accommodationAddress = this.itinerary?.accommodation_address;
            this.accommodationPrice = this.itinerary?.accommodation_price;

            // Attractions
            this.attractions = this.itinerary?.attractions;

            this.onCountryChangeLeft();
            this.onCountryChangeRight();
        }
    }

    onAddAttraction() {
        if (this.accommodationName != '' && this.attractionPrice != undefined) {
            var localLocation: ILocation = {
                id: this.itinerary?.id_destination ?? 0,
                country: this.itinerary?.destination_country ?? '',
                city: this.itinerary?.destination_city ?? '',
            };
            var attractionToAdd: IAttractionAdd = {
                location: localLocation,
                name: this.attractionName,
                price: this.attractionPrice ?? 0,
                itineraryId: this.itineraryId,
            };
            this.attractionService.addAttraction(attractionToAdd).subscribe(
                (response) => {
                    this.attractionName = '';
                    this.attractionPrice = undefined;
                    this.addAttractionFailed = true;
                    console.log('succes');
                    this.router.routeReuseStrategy.shouldReuseRoute = () =>
                        false;
                    this.router.onSameUrlNavigation = 'reload';
                    this.router.navigate([this.router.url]);
                },
                (error) => {
                    this.attractionName = '';
                    this.attractionPrice = undefined;
                    this.addAttractionFailed = false;
                    console.log('fail');
                },
            );
        }
    }

    onEditAttraction() {
        // TODO open modal
    }

    onDeleteAttraction(id: number) {
        this.attractionService.deleteAttractionById(id).subscribe(
            (response) => {
                const index = this.attractions?.findIndex(
                    (attraction) => attraction.id === id,
                );

                if (index !== undefined && index !== -1 && this.attractions) {
                    this.attractions.splice(index, 1);
                }
            },
            (error) => {
                console.log(error);
            },
        );
    }

    onSaveItinerary() {
        var itinerary: IItineraryUpdate = {
            id: this.itineraryId,
            itineraryName: this.itineraryName,
            dateStart: new Date(
                this.startDate?.year,
                this.startDate?.month - 1,
                this.startDate?.day,
            ).toLocaleDateString('sv'),
            dateEnd: new Date(
                this.endDate?.year,
                this.endDate?.month - 1,
                this.endDate?.day,
            ).toLocaleDateString('sv'),
            budget: this.budget,
            numberOfPersons: this.numberOfPersons,
            whereToCountry: this.whereToCountry,
            whereToCity: this.whereToCity,
            whereFromCountry: this.whereFromCountry,
            whereFromCity: this.whereFromCity,
            transportType: this.transportType,
            transportPrice: this.transportPrice,
            accommodationName: this.accommodationName,
            accommodationAddress: this.accommodationAddress,
            accommodationPrice: this.accommodationPrice,
            idUser: this.itinerary?.id_user,
        };

        console.log(itinerary);

        this.itineraryService.updateItinerary(itinerary).subscribe(
            (response: any) => {
                console.log('Itinerary updated successfully.');
                this.router.navigate(['/homepage']);
            },
            (error: any) => {
                console.error(error);
            },
        );
    }
}
