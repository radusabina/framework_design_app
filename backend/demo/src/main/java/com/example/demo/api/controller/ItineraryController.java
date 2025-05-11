package com.example.demo.api.controller;

import com.example.demo.api.model.*;
import com.example.demo.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ItineraryController {
    @Autowired
    ItineraryService itineraryService;

    @Autowired
    LocationService locationService;

    @Autowired
    UserService userService;

    @Autowired
    AccommodationService accommodationService;

    @Autowired
    TransportService transportService;

    @PostMapping("/itinerary")
    public ResponseEntity<Object> create(@RequestBody ItineraryInsert itinerary) {
        try {
            LocalDate dateStartModal = LocalDate.of(
                    itinerary.dateStartModal.year,
                    itinerary.dateStartModal.month,
                    itinerary.dateStartModal.day
            );

            LocalDate dateEndModal = LocalDate.of(
                    itinerary.dateEndModal.year,
                    itinerary.dateEndModal.month,
                    itinerary.dateEndModal.day
            );

            String itineraryName = itinerary.itineraryName;
            int budget = itinerary.budget ;
            int selectedPersonsOption = itinerary.selectedPersonsOption;
            String selectedCountryDestination = itinerary.selectedCountryDestination;
            String selectedCityDestination = itinerary.selectedCityDestination;
            String selectedCountryDeparting = itinerary.selectedCountryDeparting;
            String selectedCityDeparting = itinerary.selectedCityDeparting;
            String transportType = itinerary.transportType;
            float transportPrice = itinerary.transportPrice;
            String accommodationName = itinerary.accommodationName;
            String addressArea = itinerary.addressArea;
            float priceAccommodation = itinerary.priceAccommodation;
            int idUser = itinerary.idUser;

            Location destinationLocation = locationService.getByCountryAndCity(selectedCountryDestination, selectedCityDestination)
                    .orElseThrow(() -> new RuntimeException("Destination location not found"));

            Location departureLocation = locationService.getByCountryAndCity(selectedCountryDeparting, selectedCityDeparting)
                    .orElseThrow(() -> new RuntimeException("Departure location not found"));

            Accommodation accommodation = new Accommodation(accommodationName, addressArea, priceAccommodation);
            accommodationService.create(accommodation);

            Transport transport = new Transport(transportType, transportPrice);
            transportService.create(transport);

            Itinerary itinerary1 = new Itinerary(destinationLocation, transport, userService.getById(idUser).orElseThrow(() -> new RuntimeException("User not found")), accommodation, departureLocation,
                    itineraryName, dateStartModal, dateEndModal, budget, selectedPersonsOption);

            itineraryService.create(itinerary1);
            Itinerary itineraryFounded = itineraryService.getItineraryByDetails(itinerary1);

            if (itineraryFounded == null) {
                throw new RuntimeException("Itinerary not found");
            }

            return ResponseEntity.ok(itineraryFounded);
        } catch (ConstraintViolationException cve) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Exception(cve.getConstraintViolations()
                    .stream()
                    .findFirst()
                    .map(ConstraintViolation::getMessage)
                    .orElse("Validation failed.")));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EntityNotFoundException(exception.getMessage()));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Exception(exception.getMessage()));
        }
    }


    @PutMapping("/itinerary/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Map<String, String> itinerary) {
        try {
            int id_it = Integer.parseInt(itinerary.get("id"));
            String itinerary_name = itinerary.get("itineraryName");
            String date_start = itinerary.get("dateStart");
            String date_end = itinerary.get("dateEnd");
            int budget = Integer.parseInt(itinerary.get("budget"));
            int numberOfPersons = Integer.parseInt(itinerary.get("numberOfPersons"));
            String whereToCountry = itinerary.get("whereToCountry");
            String whereToCity = itinerary.get("whereToCity");
            String whereFromCountry = itinerary.get("whereFromCountry");
            String whereFromCity = itinerary.get("whereFromCity");
            String transportType = itinerary.get("transportType");
            int transportPrice = Integer.parseInt(itinerary.get("transportPrice"));
            String accommodationName = itinerary.get("accommodationName");
            String accommodationAddress = itinerary.get("accommodationAddress");
            int accommodationPrice = Integer.parseInt(itinerary.get("accommodationPrice"));
            int idUser = Integer.parseInt(itinerary.get("idUser"));

            LocalDate start_date = LocalDate.parse(date_start);
            LocalDate end_date = LocalDate.parse(date_end);

            Location destinationLocation = locationService.getByCountryAndCity(whereToCountry, whereToCity)
                    .orElseThrow(() -> new RuntimeException("Destination location not found"));
            Location departureLocation = locationService.getByCountryAndCity(whereFromCountry, whereFromCity)
                    .orElseThrow(() -> new RuntimeException("Departure location not found"));

            Itinerary itinerary1 = itineraryService.findById(id_it);

            Accommodation accm_updated = new Accommodation(accommodationName,accommodationAddress, accommodationPrice);
            int id_accm = itinerary1.getAccommodation().getId();
            accommodationService.update(id_accm, accm_updated);

            Transport transp = new Transport(transportType,transportPrice);
            int id_tranp = itinerary1.getTransport().getId();
            transportService.update(id_tranp, transp);



            Itinerary itinerary_upd = new Itinerary(destinationLocation,
                    transportService.getById(id_tranp).orElseThrow(() -> new RuntimeException("Transport not found")),
                    userService.getById(idUser).orElseThrow(() -> new RuntimeException("User not found")),
                    accommodationService.getById(id_accm).orElseThrow(() -> new RuntimeException("Accomodation not found")),
                    departureLocation, itinerary_name, start_date, end_date, budget, numberOfPersons);

            itineraryService.update(id_it, itinerary_upd);

            return ResponseEntity.ok(itinerary_upd);
        }
        catch (Exception exception) {
            System.out.println(exception);
            return ResponseEntity.ok(exception);
        }
    }

    @DeleteMapping("/itinerary/{id}")
    public void delete(@PathVariable Long id) {
        itineraryService.delete(Math.toIntExact(id));
    }

    @GetMapping("/itinerary")
    public List<Itinerary> getAll() {
        return itineraryService.getAll();
    }

    @GetMapping("/itinerary/{id}")
    public ResponseEntity<Object> getItineraryById(@PathVariable int id) {
        try {
            Optional<Itinerary> itineraryOptional = itineraryService.getById(id);

            if (itineraryOptional.isPresent()) {
                Itinerary itinerary = itineraryOptional.get();
                return ResponseEntity.ok(itinerary);
            } else {
                throw new EntityNotFoundException("Itinerary not found");
            }
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/itineraries-by-user/{userId}")
    public ResponseEntity<List<Itinerary>> getItinerariesByUserId(@PathVariable int userId) {
        List<Itinerary> itineraries = itineraryService.getItinerariesByUserId(userId);
        return new ResponseEntity<>(itineraries, HttpStatus.OK);
    }
}
