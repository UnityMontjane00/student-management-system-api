package com.example.venussms.service;

import com.example.venussms.config.StudentStatusPredicate;
import com.example.venussms.dto.request.CreateStudentRequest;
import com.example.venussms.dto.request.UpdateStudentRequest;
import com.example.venussms.dto.response.StudentResponse;
import com.example.venussms.entity.Student;
import com.example.venussms.exception.StudentNotFoundException;
import com.example.venussms.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentStatusPredicate studentStatusPredicate;

    public StudentService(
            StudentRepository studentRepository,
            StudentStatusPredicate studentStatusPredicate) {

        this.studentRepository = studentRepository;
        this.studentStatusPredicate = studentStatusPredicate;
    }

    @Transactional
    public StudentResponse createStudent(CreateStudentRequest request) {
        Student student = new Student(
                request.firstName(),
                request.surname(),
                request.emailAddress(),
                request.contactNumber(),
                request.averageMark());

        student.updateStatus(studentStatusPredicate.test(student.getAverageMark()));

        return toResponse(studentRepository.save(student));
    }

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public StudentResponse getStudentById(Long studentId) {
        return toResponse(findStudentById(studentId));
    }

    @Transactional
    public StudentResponse updateStudent(Long studentId, UpdateStudentRequest request) {
        Student student = findStudentById(studentId);

        student.updateDetails(
                request.firstName(),
                request.surname(),
                request.emailAddress(),
                request.contactNumber(),
                request.averageMark());

        student.updateStatus(studentStatusPredicate.test(student.getAverageMark()));

        return toResponse(studentRepository.save(student));
    }

    @Transactional
    public void deleteStudent(Long studentId) {
        studentRepository.delete(findStudentById(studentId));
    }

    private Student findStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
    }

    private StudentResponse toResponse(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getFirstName(),
                student.getSurname(),
                student.getEmailAddress(),
                student.getContactNumber(),
                student.getAverageMark(),
                student.getDateOfEnrollment(),
                student.isStatus());
    }
}
