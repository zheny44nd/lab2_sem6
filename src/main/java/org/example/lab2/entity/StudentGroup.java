package org.example.lab2.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "student_groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Student> students;
}
