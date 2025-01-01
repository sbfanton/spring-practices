package com.microservice.course.http.response;

import java.util.List;

import com.microservice.course.entities.dto.StudentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentsByCourseIdResponse {

    private String courseName;
    private String teacher;
    private List<StudentDTO> students;
}
