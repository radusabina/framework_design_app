package com.example.demo.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transport", schema = "public")
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private int id;

    @Column(name = "type")
    @NotBlank(message = "Type cannot be blank")
    @Length(max = 255, message = "Type is too long")
    @Pattern(regexp = "Bus|Train|Car|Airplane|Boat", message = "Type must be: Bus, Train, Car, Airplane or Boat")
    @Getter
    @Setter
    private String type;

    @Positive(message = "Price must be a positive number")
    @Column(name = "price")
    @Getter
    @Setter
    private float price;

    @JsonIgnore
    @OneToOne(mappedBy = "transport", cascade = CascadeType.MERGE)
    @Getter
    @Setter
    private Itinerary itinerary;

    @Override
    public String toString() {
        return '{' +
                "id=" + id +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';}

    public Transport(String transportType, float transportPrice) {
        this.type = transportType;
        this.price = transportPrice;

    }
}
