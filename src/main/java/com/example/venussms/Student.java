package com.example.venussms;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "Student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Declarative Validations
    @NotBlank(message = "Name is required")
    private String firstName;

    @NotBlank(message = "Surname is required")
    private String Surname;

    @NotNull(message = "Contact number is required")
    private String contactNumber;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Course is required")
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
 
    @NotNull(message = "Enrollment date is required")
    @PastOrPresent(message = "Enrollment date cannot be in the future")
    private LocalDate dateOfEnrollment;

    @NotNull(message = "Status is required")
    private Boolean Status;

    @Min(value = 0, message = "Mark cannot be negative")
    @Max(value = 100, message = "Mark cannot exceed 100")
    private Double averageMark;

    //Default Constructor for spring to create empty objects
    public Student() {

    }
    //Parameterized Constructor
    public Student(Long id, String firstName, String Surname, String contactNumber,
                   String email, String course, Boolean Status, Double averageMark, LocalDate dateOfEnrollment) {
        this.id = id;
        this.firstName = firstName;
        this.Surname = Surname;
        this.contactNumber = contactNumber;
        this.email = email;
        this.dateOfEnrollment = dateOfEnrollment;
        this.Status = Status;
        this.averageMark = averageMark;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String id) {
        this.Surname = Surname;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getDate() {
        return dateOfEnrollment;
    }
    public void setDate(LocalDate dateOfEnrollment) {
    //   this.dateOfEnrollment = dateOfEnrollment;

    }
    public Boolean getStatus() {
        return Status;
    }
    public void setStatus(Boolean Status) {
        this.Status = Status;
    }
    public Double getAverageMark() {
        return averageMark;
    }
    public void setAverageMark(Double averageMark) {
        this.averageMark = averageMark;
    }
}
