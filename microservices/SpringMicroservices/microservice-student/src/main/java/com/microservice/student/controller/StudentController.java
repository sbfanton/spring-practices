package com.microservice.student.controller;

import org.springframework.web.bind.annotation.RestController;

import com.microservice.student.entities.Student;
import com.microservice.student.entities.dto.StatusDTO;
import com.microservice.student.service.StudentService;

import org.springframework.http.HttpStatus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<?> saveStudent(@RequestBody Student student) {
        studentService.save(student);
        return new ResponseEntity<>(new StatusDTO("Estudiante creado", "OK"), HttpStatus.CREATED);
    }

    @DeleteMapping("/{student-id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("student-id") Long id) {
        studentService.deleteById(id);
        return new ResponseEntity<>(new StatusDTO("Estudiante eliminado", "OK"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        List<Student> students = studentService.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    
    @GetMapping("/{student-id}")
    public ResponseEntity<?> getStudentById(@PathVariable("student-id") Long id) {
        Student student = studentService.findById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    //Endpoint que puede ser consumido por nuestro microservicio de courses por peticion http
    @GetMapping("/courses/{course-id}")
    public ResponseEntity<?> getAllStudentsByCourseId(@PathVariable("course-id") Long idCourse) {
        List<Student> students = studentService.findAllByCourseId(idCourse);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
}
