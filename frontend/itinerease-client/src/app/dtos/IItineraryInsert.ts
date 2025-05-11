import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

export interface IItineraryInsert {
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
    accommodationName: string;
    addressArea: string;
    priceAccommodation: number | undefined;
    idUser: number | undefined;
}
