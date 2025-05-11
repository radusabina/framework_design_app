package com.example.demo.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryInsert {
    public String itineraryName;
    public DateStruct dateStartModal;
    public DateStruct dateEndModal;
    public Integer budget;
    public Integer selectedPersonsOption;
    public String selectedCountryDestination;
    public String selectedCityDestination;
    public String selectedCountryDeparting;
    public String selectedCityDeparting;
    public String transportType;
    public Float transportPrice;
    public String accommodationName;
    public String addressArea;
    public Float priceAccommodation;
    public Integer idUser;
    public static class DateStruct {
        public Integer year;
        public Integer month;
        public Integer day;
    }
}

