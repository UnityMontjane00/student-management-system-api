package com.example.venussms;
import jakarta.persistence.*;
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;

    public Course() {
    }
    public Course(String courseName) {
        this.courseName = courseName;
    }
    public Long getId() {
        return id;
    }
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
