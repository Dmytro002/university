package domain.model;

import domain.constant.Degree;
import domain.model.base.AbstractIdentifiable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
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
