package com.example.springbootgettutor.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    private float lowestMark;
    private float gradeWeight;
    private float intentionWeight;

    @ManyToOne
    private Tutor tutor;
    @OneToMany(mappedBy = "course")
    private List<Elective> electives;

}
