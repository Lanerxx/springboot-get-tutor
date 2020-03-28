package com.example.springbootgettutor.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Student {
    @Id
    private int id;
    private String name;
    private String studentNumber;
    private String password;

    @ManyToOne
    Tutor tutor;
    @OneToMany(mappedBy = "student")
    private List<Elective> electives;
    @OneToMany(mappedBy = "student")
    private List<DirectionElective> dirctionElectives;

    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;
    @Column(columnDefinition = "timestamp default current_timestamp " +
            "on update current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime updateTime;

}
