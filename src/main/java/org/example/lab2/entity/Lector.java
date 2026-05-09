package org.example.lab2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lectors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String fullName;
}

