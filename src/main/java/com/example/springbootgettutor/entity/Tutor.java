package com.example.springbootgettutor.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"courses", "students"})
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @MapsId
    private User user;

    //The number of students who have been instructed has been determined
    @PositiveOrZero
    private Integer quantity;
    //The total number of students that can be instructed
    @PositiveOrZero
    private Integer ranges;
    //Limit the number of students to be screened for rank
    @PositiveOrZero
    private Integer reservedRange;

    @OneToMany(mappedBy = "tutor")
    private List<Course> courses;
    @OneToMany(mappedBy = "tutor")
    private List<Student> students;


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
