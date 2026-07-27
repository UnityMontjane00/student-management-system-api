package com.example.venussms.controller;

import com.example.venussms.dto.request.CreateStudentRequest;
import com.example.venussms.dto.request.UpdateStudentRequest;
import com.example.venussms.dto.response.StudentResponse;
import com.example.venussms.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/students")
    public ResponseEntity<StudentResponse> createStudent(
            @Valid @RequestBody CreateStudentRequest request) {

        StudentResponse response = studentService.createStudent(request);

        return ResponseEntity
                .created(URI.create("/student/" + response.id()))
                .body(response);
    }

    @GetMapping("/students")
    public List<StudentResponse> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/student/{id}")
    public StudentResponse getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping("/student/{id}")
    public StudentResponse updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStudentRequest request) {

        return studentService.updateStudent(id, request);
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }
}