package com.microservice.course.controller;

import org.springframework.web.bind.annotation.RestController;

import com.microservice.course.entities.Course;
import com.microservice.course.entities.dto.StatusDTO;
import com.microservice.course.http.response.StudentsByCourseIdResponse;
import com.microservice.course.service.CourseService;

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
@RequestMapping("/api/courses")
public class CourseController {
    
    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<?> saveCourse(@RequestBody Course course) {
        courseService.save(course);
        return new ResponseEntity<>(new StatusDTO("Curso creado", "OK"), HttpStatus.CREATED);
    }

    @DeleteMapping("/{course-id}")
    public ResponseEntity<?> deleteCourse(@PathVariable("course-id") Long id) {
        courseService.deleteById(id);
        return new ResponseEntity<>(new StatusDTO("Curso eliminado", "OK"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        List<Course> courses = courseService.findAll();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
    
    @GetMapping("/{course-id}")
    public ResponseEntity<?> getCourseById(@PathVariable("course-id") Long id) {
        Course course = courseService.findById(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @GetMapping("/{course-id}/students")
    public ResponseEntity<?> getStudentsByCourseId(@PathVariable("course-id") Long id) {
        StudentsByCourseIdResponse studentsByCourseIdResponse = courseService.findStudentsByCourseId(id);
        return new ResponseEntity<>(studentsByCourseIdResponse, HttpStatus.OK);
    }
}
