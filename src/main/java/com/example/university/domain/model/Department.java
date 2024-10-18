package com.example.university.domain.model;

import com.example.university.domain.model.base.AbstractIdentifiable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "departments")
public class Department extends AbstractIdentifiable {

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "head_of_department_id")
    private Lector headOfDepartment;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "lector_department",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "lector_id")
    )
    private Set<Lector> lectors;
}
