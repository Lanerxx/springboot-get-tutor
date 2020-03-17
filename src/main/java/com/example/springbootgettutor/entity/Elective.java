package com.example.springbootgettutor.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Elective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String datail;
    private float grade;

    @ManyToOne
    private Student student;
    @ManyToOne
    private Course course;
}
