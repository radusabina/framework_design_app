import { IAttractionEditPage } from "./IAttractionEditPage"

export interface IItineraryEditPage {
    id: number,
    id_departure: number,
    departure_country: string,
    departure_city: string,
    id_destination: number,
    destination_country: string,
    destination_city: string,
    id_transport: number,
    transport_type: string,
    transport_price: number,
    id_user: number,
    id_accommodation: number,
    accommodation_name: string,
    accommodation_address: string,
    accommodation_price: number,
    attractions: IAttractionEditPage[],
    name: string,
    arrival_date: Date,
    departure_date: Date,
    budget: number,
    persons: number 
}