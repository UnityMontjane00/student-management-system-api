package com.example.venussms.service;
import com.example.venussms.Student;
import com.example.venussms.StudentNotFoundException;
import com.example.venussms.repository.StudentRepository;
//import jakarta.validation.constraints.Email;
//import jakarta.transaction.Status;
import jakarta.persistence.Id;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.function.Predicate;
//Business Layer
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public Student saveStudent(Student student) {
        //email validation
        if(student.getEmail() == null || student.getEmail().isEmpty()){
            throw new RuntimeException("Email cannot be empty");
        }
        student.setStatus(student.getAverageMark()>= 65);

        return studentRepository.save(student);
    }
    //Delete Student
    public void deleteStudent(Long id){
        studentRepository.deleteById(id);
    }
    //Update Student
    public Student updateStudent(Long id, Student updatedStudent) {

        Student existingStudent = studentRepository.findById(id).orElseThrow();
        existingStudent.setFirstName(updatedStudent.getFirstName());
        existingStudent.setCourse(updatedStudent.getCourse());
        existingStudent.setAverageMark(updatedStudent.getAverageMark());
        existingStudent.setStatus(updatedStudent.getStatus());
        existingStudent.setContactNumber(updatedStudent.getContactNumber());
        return studentRepository.save(existingStudent);
    }
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with id" + id));
    }
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}

