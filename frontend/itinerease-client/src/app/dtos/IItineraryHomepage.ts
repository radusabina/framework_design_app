import { IAcommodation } from './IAcommodation';
import { IAttraction } from './IAttraction';
import { ILocation } from './ILocation';
import { ITransport } from './ITransport';
import { IUser } from './IUser';
export interface IItineraryHomepage {
    id: number;
    name: string;
    persons: number;
    budget: number;
    arrivalDate: number[];
    departureDate: number[];
    destination: ILocation;
}
