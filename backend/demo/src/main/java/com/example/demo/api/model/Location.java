package com.example.demo.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "location", schema = "public")
public class Location{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private int id;

    @Column(name = "country", unique = true)
    @Length(max = 255, message = "Try again! Country name is too long")
    @NotEmpty(message = "Try again! Country name cannot be empty")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Try again! Country name must start with an uppercase letter")
    @Getter
    @Setter
    private String country;


    @Column(name = "city", unique = true)
    @Length(max = 255, message = "Try again! Country is too long")
    @NotEmpty(message = "Try again! Country name cannot be empty")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Try again! City name must start with an uppercase letter")
    @Getter
    @Setter
    private String city;

    @JsonIgnore
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    @Setter
    private List<Attraction> attractions;

    @JsonIgnore
    @OneToMany(mappedBy = "destination_location", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    @Setter
    private List<Itinerary> destination_itineraries;

    @JsonIgnore
    @OneToMany(mappedBy = "departure_location", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    @Setter
    private List<Itinerary> departure_itineraries;

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
