package com.microservice.course.service;

import java.util.List;

import com.microservice.course.entities.Course;
import com.microservice.course.http.response.StudentsByCourseIdResponse;

public interface CourseService {
    
    List<Course> findAll();
    Course findById(Long id);
    void save(Course course);
    void deleteById(Long id);
    StudentsByCourseIdResponse findStudentsByCourseId(Long id);
}
