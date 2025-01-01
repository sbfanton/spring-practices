package com.microservice.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.student.entities.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    //@Query("SELECT s from Student s WHERE s.courseId = :idCourse")
    List<Student> findAllByCourseId(Long idCourse);
} 
