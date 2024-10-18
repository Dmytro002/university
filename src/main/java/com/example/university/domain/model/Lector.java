package com.example.university.domain.model;

import com.example.university.domain.constant.Degree;
import com.example.university.domain.model.base.AbstractIdentifiable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lectors")
public class Lector extends AbstractIdentifiable {

    @Column(name = "name")
    private String name;

    @Column(name = "salary")
    private double salary;

    @Column(name = "degree")
    @Enumerated(EnumType.STRING)
    private Degree degree;
}
