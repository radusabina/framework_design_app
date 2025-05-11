import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

export interface IItineraryUpdate {
    id: number;
    itineraryName: string;
    dateStart: string;
    dateEnd: string;
    budget: number | undefined;
    numberOfPersons: number | undefined;
    whereToCountry: string;
    whereToCity: string;
    whereFromCountry: string;
    whereFromCity: string;
    transportType: string;
    transportPrice: number | undefined;
    accommodationName: string;
    accommodationAddress: string;
    accommodationPrice: number | undefined;
    idUser: number | undefined;
}
