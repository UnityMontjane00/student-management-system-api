package com.example.venussms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String surname;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "average_mark", nullable = false, precision = 5, scale = 2)
    private BigDecimal averageMark;

    @Column(name = "date_of_enrollment", nullable = false, updatable = false)
    private LocalDate dateOfEnrollment;

    @Column(nullable = false)
    private boolean status;

    protected Student() {
        // Required by JPA.
    }

    public Student(
            String firstName,
            String surname,
            String emailAddress,
            String contactNumber,
            BigDecimal averageMark) {

        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.contactNumber = contactNumber;
        this.averageMark = averageMark;
    }

    @PrePersist
    void assignEnrollmentDate() {
        if (dateOfEnrollment == null) {
            dateOfEnrollment = LocalDate.now();
        }
    }

    public void updateDetails(
            String firstName,
            String surname,
            String emailAddress,
            String contactNumber,
            BigDecimal averageMark) {

        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.contactNumber = contactNumber;
        this.averageMark = averageMark;
    }

    public void updateStatus(boolean status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public BigDecimal getAverageMark() {
        return averageMark;
    }

    public LocalDate getDateOfEnrollment() {
        return dateOfEnrollment;
    }

    public boolean isStatus() {
        return status;
    }
}
