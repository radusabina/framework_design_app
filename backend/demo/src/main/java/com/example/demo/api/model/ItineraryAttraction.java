package com.example.demo.api.model;

import com.example.demo.api.model.Attraction;
import com.example.demo.api.model.Itinerary;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "itinerary_attraction", schema = "public")
public class ItineraryAttraction {

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItineraryAttractionId implements Serializable {
        @Column(name = "id_itinerary")
        @Getter
        @Setter
        private int itinerary_Id;

        @Column(name = "id_attraction")
        @Getter
        @Setter
        private int attractionId;

        // Constructori, hashCode, equals, getteri și setteri
    }

    @EmbeddedId
    private ItineraryAttractionId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("itineraryId")
    @JoinColumn(name = "id_itinerary")
    private Itinerary itinerary;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("attractionId")
    @JoinColumn(name = "id_attraction")
    private Attraction attraction;

    // Constructori, getteri și setteri
}
