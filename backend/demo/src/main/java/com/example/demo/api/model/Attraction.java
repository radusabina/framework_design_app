package com.example.demo.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "attraction", schema = "public")
public class Attraction{
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_location")
    @NotNull(message = "Location cannot be null")
    @Getter
    @Setter
    private Location location;

    @Column(name = "name")
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Try again! Name must start with an uppercase letter")
    @Getter
    @Setter
    private String name;

    @Column(name = "price")
    @Positive(message = "Price must be a positive number")
    @Getter
    @Setter
    private float price;

    @JsonIgnore
    @ManyToMany(mappedBy = "attractions")
    @Getter
    @Setter
    private List<Itinerary> itineraries;


    private Integer itineraryId;
    @Override
    public String toString() {
        return '{' +
                "id=" + id +
                ", location=" + location +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
