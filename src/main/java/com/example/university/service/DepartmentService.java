package com.example.university.service;

import com.example.university.domain.constant.Degree;
import com.example.university.domain.model.Lector;
import com.example.university.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor

public class DepartmentService {

    private static final String DEPARTMENT_NOT_FOUND_MESSAGE_TEMPLATE = "Department with name '%s' not found.";

    private final DepartmentRepository departmentRepository;

    public String getHeadOfDepartment(String departmentName) {
        return departmentRepository.findByName(departmentName)
                .map(department -> {
                    Lector head = department.getHeadOfDepartment();
                    return head != null
                            ? String.format("Head of %s department is %s", departmentName, head.getName())
                            : "Head of department not found.";
                })
                .orElse(String.format(DEPARTMENT_NOT_FOUND_MESSAGE_TEMPLATE, departmentName));
    }

    public String getStatistics(String departmentName) {
        return departmentRepository.findByName(departmentName)
                .map(department -> {
                    Map<Degree, Long> degreeCount = department.getLectors().stream()
                            .collect(Collectors.groupingBy(Lector::getDegree, Collectors.counting()));

                    long assistants = degreeCount.getOrDefault(Degree.ASSISTANT, 0L);
                    long associateProfessors = degreeCount.getOrDefault(Degree.ASSOCIATE_PROFESSOR, 0L);
                    long professors = degreeCount.getOrDefault(Degree.PROFESSOR, 0L);

                    return String.format("assistants - %d\nassociate professors - %d\nprofessors - %d",
                            assistants, associateProfessors, professors);
                })
                .orElse(String.format(DEPARTMENT_NOT_FOUND_MESSAGE_TEMPLATE, departmentName));
    }

    public String getAverageSalary(String departmentName) {
        return departmentRepository.findByName(departmentName)
                .map(department -> {
                    double averageSalary = department.getLectors().stream()
                            .mapToDouble(Lector::getSalary)
                            .average()
                            .orElse(0.0);

                    return String.format("The average salary of %s is %.2f", departmentName, averageSalary);
                })
                .orElse(String.format(DEPARTMENT_NOT_FOUND_MESSAGE_TEMPLATE, departmentName));
    }

    public String getEmployeeCount(String departmentName) {
        return departmentRepository.findByName(departmentName)
                .map(department -> String.format("Employee count: %d", department.getLectors().size()))
                .orElse(String.format(DEPARTMENT_NOT_FOUND_MESSAGE_TEMPLATE, departmentName));
    }
}