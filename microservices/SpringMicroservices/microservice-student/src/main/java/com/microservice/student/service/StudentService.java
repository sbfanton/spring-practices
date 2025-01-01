package com.microservice.student.service;

import java.util.List;

import com.microservice.student.entities.Student;

public interface StudentService {
    
    List<Student> findAll();
    List<Student> findAllByCourseId(Long courseId);
    Student findById(Long id);
    void save(Student student);
    void deleteById(Long id);
}
