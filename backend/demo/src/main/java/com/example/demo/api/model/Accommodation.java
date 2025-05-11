package com.example.demo.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accommodation", schema = "public")
public class Accommodation{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Name cannot be blank")
    @Length(max = 255, message = "Name is too long")
    @Getter
    @Setter
    private String name;

    @Column(name = "address")
    @NotBlank(message = "Address cannot be blank")
    @Length(max = 255, message = "Address is too long")
    @Getter
    @Setter
    private String address;

    @Positive(message = "Price must be a positive number")
    @Column(name = "price")
    @Getter
    @Setter
    private float price;

    @JsonIgnore
    @OneToOne(mappedBy = "accommodation", cascade = CascadeType.MERGE)
    @Getter
    @Setter
    private Itinerary itinerary;


    @Override
    public String toString() {
        return '{' +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                '}';}

    public Accommodation(String accomodationName, String addressArea, float priceAccommodation) {
        this.address = addressArea;
        this.name = accomodationName;
        this.price = priceAccommodation;

    }
}
