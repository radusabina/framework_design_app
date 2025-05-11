import { ILocation } from './ILocation';

export interface IAttraction {
    id: number;
    location: ILocation;
    name: string;
    price: number;
}
