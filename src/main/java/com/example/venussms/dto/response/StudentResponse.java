package com.example.venussms.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public record StudentResponse(
        Long id,

        @JsonProperty("name")
        String firstName,

        String surname,

        @JsonProperty("email_address")
        String emailAddress,

        @JsonProperty("contact_number")
        String contactNumber,

        BigDecimal averageMark,

        @JsonProperty("date_of_enrollment")
        LocalDate dateOfEnrollment,

        boolean status) {
}