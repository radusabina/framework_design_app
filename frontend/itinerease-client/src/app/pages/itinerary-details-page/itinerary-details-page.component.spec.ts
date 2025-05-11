import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItineraryDetailsPageComponent } from './itinerary-details-page.component';

describe('ItineraryDetailsPageComponent', () => {
  let component: ItineraryDetailsPageComponent;
  let fixture: ComponentFixture<ItineraryDetailsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ItineraryDetailsPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ItineraryDetailsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
