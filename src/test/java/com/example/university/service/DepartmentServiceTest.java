package com.example.university.service;

import com.example.university.domain.model.Department;
import com.example.university.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.example.university.util.ObjectMapperUtils.toObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest(classes = DepartmentService.class)
class DepartmentServiceTest {

    private static final String DEPARTMENT = "department";
    private static final String DEPARTMENT_WITHOUT_HEAD = "department-without-head";
    private static final String DEPARTMENT_NAME_NOT_EXIST = "Nonexistent";
    private static final String DEPARTMENT_NOT_FOUND_MESSAGE_TEMPLATE = "Department with name '%s' not found.";

    @MockBean
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentService departmentService;

    @Test
    void testGetHeadOfDepartment_WhenDepartmentExists() {
        Department department = toObject(DEPARTMENT, Department.class);
        given(departmentRepository.findByName(department.getName())).willReturn(Optional.of(department));

        String result = departmentService.getHeadOfDepartment(department.getName());

        then(departmentRepository).should().findByName(department.getName());
        assertEquals(String.format("Head of %s department is Head Lector", department.getName()), result);
    }

    @Test
    void testGetHeadOfDepartment_WhenHeadNotFound() {
        Department department = toObject(DEPARTMENT_WITHOUT_HEAD, Department.class);

        given(departmentRepository.findByName(department.getName())).willReturn(Optional.of(department));

        String result = departmentService.getHeadOfDepartment(department.getName());

        then(departmentRepository).should().findByName(department.getName());
        assertEquals("Head of department not found.", result);
    }

    @Test
    void testGetStatistics_WhenDepartmentExists() {
        Department department = toObject(DEPARTMENT, Department.class);
        given(departmentRepository.findByName(department.getName())).willReturn(Optional.of(department));

        String result = departmentService.getStatistics(department.getName());

        then(departmentRepository).should().findByName(department.getName());
        assertEquals("assistants - 1\nassociate professors - 1\nprofessors - 1", result);
    }

    @Test
    void testGetAverageSalary_WhenDepartmentExists() {
        Department department = toObject(DEPARTMENT, Department.class);
        given(departmentRepository.findByName(department.getName())).willReturn(Optional.of(department));

        String result = departmentService.getAverageSalary(department.getName());

        then(departmentRepository).should().findByName(department.getName());
        assertEquals(String.format("The average salary of %s is 66666.67", department.getName()), result);
    }

    @Test
    void testGetEmployeeCount_WhenDepartmentExists() {
        Department department = toObject(DEPARTMENT, Department.class);
        given(departmentRepository.findByName(department.getName())).willReturn(Optional.of(department));

        String result = departmentService.getEmployeeCount(department.getName());

        then(departmentRepository).should().findByName(department.getName());
        assertEquals("Employee count: 3", result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "testGetHeadOfDepartment_WhenDepartmentNotFound",
            "testGetStatistics_WhenDepartmentNotFound",
            "testGetAverageSalary_WhenDepartmentNotFound",
            "testGetEmployeeCount_WhenDepartmentNotFound"
    })
    void testDepartmentNotFoundScenarios(String methodName) {
        given(departmentRepository.findByName(DEPARTMENT_NAME_NOT_EXIST)).willReturn(Optional.empty());

        String result = switch (methodName) {
            case "testGetHeadOfDepartment_WhenDepartmentNotFound" ->
                    departmentService.getHeadOfDepartment(DEPARTMENT_NAME_NOT_EXIST);
            case "testGetStatistics_WhenDepartmentNotFound" ->
                    departmentService.getStatistics(DEPARTMENT_NAME_NOT_EXIST);
            case "testGetAverageSalary_WhenDepartmentNotFound" ->
                    departmentService.getAverageSalary(DEPARTMENT_NAME_NOT_EXIST);
            case "testGetEmployeeCount_WhenDepartmentNotFound" ->
                    departmentService.getEmployeeCount(DEPARTMENT_NAME_NOT_EXIST);
            default -> "";
        };

        then(departmentRepository).should().findByName(DEPARTMENT_NAME_NOT_EXIST);
        assertEquals(String.format(DEPARTMENT_NOT_FOUND_MESSAGE_TEMPLATE, DEPARTMENT_NAME_NOT_EXIST), result);
    }
}