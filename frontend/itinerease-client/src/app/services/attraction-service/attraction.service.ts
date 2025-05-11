import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { endpointAPI } from '../../config/appconfig';
import { IAttraction } from '../../dtos/IAttraction';
import { IAttractionAdd } from '../../dtos/IAttractionAdd';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class AttractionService {
    constructor(private http: HttpClient) {}

    addAttraction(attraction: IAttractionAdd): Observable<IAttractionAdd> {
        return this.http.post<IAttractionAdd>(
            endpointAPI + 'attraction',
            attraction,
        );
    }

    deleteAttractionById(id: number) {
        return this.http.delete(endpointAPI + `attraction/${id}`);
    }

    updateAttraction(updatedAttraction: IAttraction) {
        return this.http.put<IAttraction>(
            endpointAPI + 'attraction',
            updatedAttraction,
        );
    }
}
