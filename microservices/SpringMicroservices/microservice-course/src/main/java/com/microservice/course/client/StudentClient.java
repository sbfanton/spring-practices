package com.microservice.course.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservice.course.entities.dto.StudentDTO;

//nombre y url del microservicio al que haremos peticiones, se obtiene del .yml del microservicio en cuestion
@FeignClient(name = "msvc-student", url = "localhost:8090/api/students") 
public interface StudentClient {

    @GetMapping("/courses/{course-id}")
    List<StudentDTO> findAllStudentsByCourse(@PathVariable("course-id") Long idCourse);
}
