package com.example.venussms.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(Long studentId) {
        super("Student not found with id: " + studentId);
    }
}