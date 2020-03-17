package com.example.springbootgettutor.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Student {
    @Id
    private int id;
    private String name;

    @ManyToOne
    Tutor tutor;
    @OneToMany(mappedBy = "student")
    private List<Elective> electives;

}
