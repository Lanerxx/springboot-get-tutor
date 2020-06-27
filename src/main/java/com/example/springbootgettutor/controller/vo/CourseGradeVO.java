package com.example.springbootgettutor.controller.vo;

import com.example.springbootgettutor.entity.Course;
import com.example.springbootgettutor.entity.Student;
import lombok.Data;

@Data
public class CourseGradeVO {
    private Course course;
    private float grade;
    private int sid;
}
