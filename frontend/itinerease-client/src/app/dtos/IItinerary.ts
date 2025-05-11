import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

export interface IItinerary {
    id: number;
    itineraryName: string;
    dateStartModal: NgbDateStruct;
    dateEndModal: NgbDateStruct;
    budget: number | undefined;
    selectedPersonsOption: number | undefined;
    selectedCountryDestination: string;
    selectedCityDestination: string;
    selectedCountryDeparting: string;
    selectedCityDeparting: string;
    transportType: string;
    transportPrice: number | undefined;
    accomodationName: string;
    addressArea: string;
    priceAccomodation: number | undefined;
    idUser: number | undefined;
}
