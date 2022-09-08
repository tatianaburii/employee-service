package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.api.dto.EmployeeRequestFixture;
import com.tatianaburii.employeeservice.controller.dto.EmployeeDto;
import com.tatianaburii.employeeservice.domain.*;
import com.tatianaburii.employeeservice.repository.EmployeeRepository;
import com.tatianaburii.employeeservice.service.imlp.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeServiceImpl employeeService;
    @Captor
    ArgumentCaptor<Employee> employeeArgumentCaptor;
    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;
    List<Employee> employees;
    Employee employee;
    EmployeeDto dto;
    Long id;

    @BeforeEach
    public void init() {
        employee = EmployeeFixture.createEmployee();
        employees = List.of(employee);
        dto = EmployeeRequestFixture.createEmployeeRequest();
        id = 3L;
    }
    @Test
    void findAll() {
        when(employeeRepository.findByActiveTrueAndDepartmentActiveTrue()).thenReturn(employees);
        List<Employee> actualEmployees = employeeService.findAll();
        assertThat(employees).isEqualTo(actualEmployees);
    }

    @Test
    void create() {
        employeeService.create(dto, DepartmentFixture.createDepartment());
        verify(employeeRepository).save(employeeArgumentCaptor.capture());
        Employee actualEmployee = employeeArgumentCaptor.getValue();
        assertThat(actualEmployee.getName()).isEqualTo(dto.getName());
        assertThat(actualEmployee.getPhone()).isEqualTo(dto.getPhone());
        assertThat(actualEmployee.getEmail()).isEqualTo(dto.getEmail());
        assertThat(actualEmployee.getDateOfBirth()).isEqualTo(dto.getDateOfBirth());
        assertThat(actualEmployee.getDepartment().getId()).isEqualTo(dto.getDepartmentId());
    }

    @Test
    void findById() {
        when(employeeRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.ofNullable(employee));
        employeeService.findById(employee.getId());
        verify(employeeRepository).findByIdAndActiveTrue(longArgumentCaptor.capture());
        id = longArgumentCaptor.getValue();
        assertThat(id).isEqualTo(employee.getId());
    }

    @Test
    void update() {
        employeeService.update(employee, dto, DepartmentFixture.createDepartment());
        verify(employeeRepository).save(employeeArgumentCaptor.capture());
        Employee actualEmployee = employeeArgumentCaptor.getValue();
        assertThat(actualEmployee.getName()).isEqualTo(dto.getName());
        assertThat(actualEmployee.getPhone()).isEqualTo(dto.getPhone());
        assertThat(actualEmployee.getEmail()).isEqualTo(dto.getEmail());
        assertThat(actualEmployee.getDateOfBirth()).isEqualTo(dto.getDateOfBirth());
        assertThat(actualEmployee.getDepartment().getId()).isEqualTo(dto.getDepartmentId());
    }

    @Test
    void delete() {
        employeeService.delete(employee);
        verify(employeeRepository).save(employeeArgumentCaptor.capture());
        Employee actualEmployee = employeeArgumentCaptor.getValue();
        assertThat(actualEmployee.getId()).isEqualTo(dto.getId());
        assertThat(actualEmployee.getActive()).isFalse();
    }
}