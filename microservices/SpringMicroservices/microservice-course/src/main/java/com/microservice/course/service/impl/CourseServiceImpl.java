package com.microservice.course.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.course.client.StudentClient;
import com.microservice.course.entities.Course;
import com.microservice.course.entities.dto.StudentDTO;
import com.microservice.course.http.response.StudentsByCourseIdResponse;
import com.microservice.course.repository.CourseRepository;
import com.microservice.course.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentClient studentClient;

    @Override
    public List<Course> findAll() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public StudentsByCourseIdResponse findStudentsByCourseId(Long id) {
        
        //Consultamos el curso
        Course course = courseRepository.findById(id).orElseThrow();

        //Obtener los estudiantes
        List<StudentDTO> studentDTOs = studentClient.findAllStudentsByCourse(id);

        return StudentsByCourseIdResponse.builder()
                                         .courseName(course.getName())
                                         .teacher(course.getTeacher())
                                         .students(studentDTOs)
                                         .build();
    }
}
