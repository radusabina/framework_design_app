package com.example.demo.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user", schema = "public")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private int id;

    @Length(max = 255, message = "Try again! Last name is too long")
    @NotEmpty(message = "Try again! Last name cannot be empty")
    @Pattern(regexp = "\\b[A-Z].*?\\b", message = "Try again! Last name must start with an uppercase letter")
    @Column(name = "last_name")
    @Getter
    @Setter
    private String last_name;

    @Length(max = 255, message = "Try again! First name is too long")
    @NotEmpty(message = "Try again! First name cannot be empty")
    @Pattern(regexp = "\\b[A-Z].*?\\b", message = "Try again! First name must start with an uppercase letter")
    @Column(name = "first_name")
    @Getter
    @Setter
    private String first_name;

    @Length(min = 10, max = 10, message = "Please enter a valid phone number")
    @NotEmpty(message = "Try again! Phone number cannot be empty")
    @Pattern(regexp = "^07[0-9]{8}$", message = "Enter a valid Romanian phone number")
    @Column(name = "phone_number")
    @Getter
    @Setter
    private String phone_number;

    @Email(message = "This is not a valid email")
    @NotEmpty(message = "Try again! Email cannot be empty")
    @Column(name = "email", unique = true)
    @Getter
    @Setter
    private String email;

    @Length(max = 255, message = "Password is too long")
    @NotEmpty(message = "Try again! Password cannot be empty")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$", message = "Try again! Password must meet the requirements")
    @Column(name = "password")
    @Getter
    @Setter
    private String password;
}
