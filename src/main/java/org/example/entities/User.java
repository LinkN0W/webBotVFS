package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.dto.UserDTO;
import org.example.enums.Gender;
import org.example.enums.Nationality;
import org.example.enums.VisaCategory;
import org.example.enums.VisaCenter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue()
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "dial_code")
    private String dialCode;

    @Column(name = "passport_number")
    private String passportNumber;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "passport_expiry_date")
    private LocalDate passportExpirtyDate;

    @Column(name = "date_of_birthday")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfBirth;


    @Column(name = "nationality")
    private Nationality nationality;

    @Column(name = "city")
    private VisaCenter city;

    @Column(name = "visa_category")
    private VisaCategory visaCategory;


    @Column(name = "is_appointed")
    @JsonIgnore
    private boolean isAppointed;


    public User(String email, String firstName,
                String lastName, Gender gender,
                String contactNumber, String dialCode, String passportNumber,
                LocalDate passportExpirtyDate, LocalDate dateOfBirth,
                Nationality nationality,
                VisaCenter city, VisaCategory visaCategory) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.dialCode = dialCode;
        this.passportNumber = passportNumber;
        this.passportExpirtyDate = passportExpirtyDate;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.nationality = nationality;
        this.city = city;
        this.visaCategory = visaCategory;
    }

    public User(UserDTO userDTO){
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.gender = userDTO.getGender();
        this.contactNumber = userDTO.getContactNumber();
        this.dialCode = userDTO.getDialCode();
        this.passportNumber = userDTO.getPassportNumber();
        this.passportExpirtyDate = userDTO.getPassportExpirtyDate();
        this.dateOfBirth = userDTO.getDateOfBirth();
        this.email = userDTO.getEmail();
        this.nationality = userDTO.getNationality();
        this.city = userDTO.getCenter();
        this.visaCategory = userDTO.getVisaCategory();

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", contactNumber=" + contactNumber +
                ", dialCode=" + dialCode +
                ", passportNumber=" + passportNumber +
                ", passportExpirtyDate=" + passportExpirtyDate +
                ", dateOfBirth=" + dateOfBirth +
                ", nationality=" + nationality +
                ", city='" + city + '\'' +
                ", visaCategory=" + visaCategory +
                ", isAppointed=" + isAppointed +
                '}';
    }
}