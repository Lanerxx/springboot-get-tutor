package com.example.springbootgettutor.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"electives"})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String name;

    @Max(value = 1, message = "The weight should be between 0 and 1")
    @Min(value = 0, message = "The weight should be between 0 and 1")
    private double weight;
    @Max(value = 100, message = "The lowest mark should be between 0 and 100")
    @Min(value = 0, message = "The lowest mark should be between 0 and 100")
    private double lowestMark;

    @ManyToOne
    private Tutor tutor;
    @OneToMany(mappedBy = "course")
    private List<Elective> electives;

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
