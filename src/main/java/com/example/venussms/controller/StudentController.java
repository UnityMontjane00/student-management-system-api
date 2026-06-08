package com.example.venussms.controller;
import com.example.venussms.Student;
import com.example.venussms.service.StudentService;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    //Get Student by id
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }
    //Create Students
    @PostMapping
    public Student saveStudent(@Valid @RequestBody Student student){
        return studentService.saveStudent(student);
    }
    //delete student
    @DeleteMapping("/{id}")
    public void deleteStudent(Long id){
        studentService.deleteStudent(id);
    }
    //update student
    @PutMapping("/{id}")
    public Student updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }
    //Get All Students
    @GetMapping
    public  List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
}
