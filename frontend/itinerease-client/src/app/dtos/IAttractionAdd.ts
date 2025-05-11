import { ILocation } from './ILocation';

export interface IAttractionAdd {
    location: ILocation;
    name: string;
    price: number;
    itineraryId: number;
}
