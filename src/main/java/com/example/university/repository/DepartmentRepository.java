package com.example.university.repository;

import com.example.university.domain.model.Department;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @EntityGraph(attributePaths = "lectors")
    Optional<Department> findByName(String departmentName);
}