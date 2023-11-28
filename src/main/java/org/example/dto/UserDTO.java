package org.example.dto;

import org.example.enums.Gender;
import org.example.enums.Nationality;
import org.example.enums.VisaCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.VisaCenter;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
@Setter
public class UserDTO {

    private String email;

    private String firstName;

    private String lastName;
    private Gender gender;

    private String contactNumber;

    private String dialCode;
    private String passportNumber;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate passportExpirtyDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfBirth;

    private Nationality nationality;

    private VisaCenter center;

    private VisaCategory visaCategory;



}
