import { Component, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterOutlet } from '@angular/router';
import {
    NgbAlertModule,
    NgbDatepickerModule,
    NgbModalModule,
} from '@ng-bootstrap/ng-bootstrap';
import { FormControl, FormsModule, NgForm, Validators } from '@angular/forms';
import { JsonPipe } from '@angular/common';
import { ItineraryService } from '../../services/itinerary-service/itinerary.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-delete-confirmation-modal',
    styleUrls: ['./delete-confirmation-modal.component.scss'],
    standalone: true,
    imports: [
        CommonModule,
        RouterOutlet,
        NgbDatepickerModule,
        NgbAlertModule,
        FormsModule,
        NgbModalModule,
        JsonPipe,
    ],
    templateUrl: './delete-confirmation-modal.component.html',
})
export class DeleteConfirmationModalComponent {
    itineraryId: number = 0;

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private itineraryService: ItineraryService,
    ) {
        this.route.params.subscribe((params) => {
            this.itineraryId = params['id'];
        });
    }

    onDeleteItinerary(id: number): void {
        this.itineraryService.deleteItineraryById(id).subscribe(
            () => {
                console.log('Itinerary deleted successfully.');
                this.router.navigate(['/homepage']);
            },
            (error) => {
                console.error(error);
            },
        );
    }
}
