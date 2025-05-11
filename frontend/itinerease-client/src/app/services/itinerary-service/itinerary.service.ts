import { Injectable } from '@angular/core';
import { IItineraryHomepage } from '../../dtos/IItineraryHomepage';
import { HttpClient } from '@angular/common/http';
import { endpointAPI } from '../../config/appconfig';
import { Observable, BehaviorSubject } from 'rxjs';
import { UserService } from '../user-service/user.service';
import { IUser } from '../../dtos/IUser';
import { map } from 'rxjs/operators';
import { IItinerary } from '../../dtos/IItinerary';
import { IItineraryInsert } from '../../dtos/IItineraryInsert';
import { IItineraryEditPage } from '../../dtos/IItineraryEditPage';
import { IItineraryUpdate } from '../../dtos/IItineraryUpdate';

@Injectable({
    providedIn: 'root',
})
export class ItineraryService {
    loggedUser: IUser | undefined = undefined;
    private itinerariesSubject = new BehaviorSubject<IItineraryHomepage[]>([]);
    itineraries$ = this.itinerariesSubject.asObservable();

    constructor(
        private http: HttpClient,
        private userService: UserService,
    ) {}

    // cu asta ii trimit la topy o cerere prin care vreau sa adaug un itinerar in baza de date
    insertItinerary(itinerary: IItineraryInsert): Observable<IItineraryInsert> {
        return this.http.post<IItineraryInsert>(
            endpointAPI + 'itinerary',
            itinerary,
        );
    }

    loadItineraries(loggedUser: IUser): void {
        const userId = loggedUser.id;

        if (userId) {
            this.getItinerariesByUser(userId)
                .pipe(
                    map((itineraries: IItineraryHomepage[]) => {
                        // Map each itinerary to a simplified version with only the required fields
                        return itineraries.map((itinerary: any) => ({
                            id: itinerary.id,
                            name: itinerary.name,
                            persons: itinerary.persons,
                            budget: itinerary.budget,
                            arrivalDate: itinerary.arrival_date,
                            departureDate: itinerary.departure_date,
                            destination: itinerary.destination_location,
                        }));
                    }),
                )
                .subscribe(
                    (simplifiedItineraries: IItineraryHomepage[]) => {
                        // Assign the simplified itineraries to the subject
                        this.itinerariesSubject.next(simplifiedItineraries);
                    },
                    (error) => {
                        console.error('Error loading itineraries:', error);
                    },
                );
        }
    }

    getItinerariesByUser(userId: number): Observable<IItineraryHomepage[]> {
        return this.http.get<IItineraryHomepage[]>(
            `${endpointAPI}itineraries-by-user/${userId}`,
        );
    }

    getItineraryById(id: number) {
        return this.http.get<IItinerary>(endpointAPI + `itinerary/${id}`);
    }

    deleteItineraryById(id: number): Observable<void> {
        return this.http.delete<void>(`${endpointAPI}itinerary/${id}`);
    }

    updateItinerary(itinerary: IItineraryUpdate) {
        return this.http.put<void>(
            endpointAPI + 'itinerary/' + itinerary.id,
            itinerary,
        );
    }
}
