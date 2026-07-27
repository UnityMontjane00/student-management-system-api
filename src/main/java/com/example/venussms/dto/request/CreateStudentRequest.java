package com.example.venussms.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateStudentRequest(

        @JsonProperty("name")
        @NotBlank(message = "First name must not be blank")
        String firstName,

        @NotBlank(message = "Surname must not be blank")
        String surname,

        @JsonProperty("email_address")
        @Email(message = "Email address must be valid")
        String emailAddress,

        @JsonProperty("contact_number")
        String contactNumber,

        @NotNull(message = "Average mark is required")
        @DecimalMin(value = "0.0", message = "Average mark must be at least 0")
        @DecimalMax(value = "100.0", message = "Average mark must not exceed 100")
        BigDecimal averageMark) {
}