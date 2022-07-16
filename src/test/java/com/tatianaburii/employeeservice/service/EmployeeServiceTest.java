package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.EmployeeRequest;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeServiceImpl employeeService;
    @Captor
    ArgumentCaptor<Employee> employeeArgumentCaptor;
    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;
    @Captor
    ArgumentCaptor<Integer> integerArgumentCaptor;
    EmployeeRequest employeeRequest;
    List<Employee> employees;
    Employee employee;
    String email;
    Department department;
    int id;

    @BeforeEach
    public void init() {
        department = DepartmentFixture.createDepartment();
        employee = EmployeeFixture.createEmployee();
        employees = List.of(employee, new Employee());
        employeeRequest = EmployeeRequestFixture.createEmployeeRequest();
        email = "employee_test@gmail.com";
    }

    @Test
    void save() {
        employeeService.save(employeeRequest);
        verify(employeeRepository).save(employeeArgumentCaptor.capture());
        Employee employee = employeeArgumentCaptor.getValue();
        assertThat(employee.getName()).isEqualTo(employeeRequest.getName());
        assertThat(employee.getEmail()).isEqualTo(employeeRequest.getEmail());
        assertThat(employee.getPhone()).isEqualTo(employeeRequest.getPhone());
        assertThat(employee.getDateOfBirth()).isEqualTo(employeeRequest.getDateOfBirth());
        assertThat(employee.getDepartment()).isEqualTo(employeeRequest.getDepartment());
    }

    @Test
    void isUnique_whenTheSameEmployee_thanReturnsTrue() {
        when(employeeRepository.findOneByEmail(anyString())).thenReturn(employee);
        boolean result = employeeService.isUnique(employee.getEmail(), employee.getId());
        verify(employeeRepository).findOneByEmail(stringArgumentCaptor.capture());
        String employeeEmail = stringArgumentCaptor.getValue();
        assertThat(employeeEmail).isEqualTo(employee.getEmail());
        assertThat(result).isEqualTo(true);
    }

    @Test
    void isUnique_whenEmailIsUnique_thanReturnsTrue() {
        id = 4;
        when(employeeRepository.findOneByEmail(anyString())).thenReturn(null);
        boolean result = employeeService.isUnique(email, id);
        verify(employeeRepository).findOneByEmail(stringArgumentCaptor.capture());
        String employeeEmail = stringArgumentCaptor.getValue();
        assertThat(employeeEmail).isEqualTo(email);
        assertThat(result).isEqualTo(true);
    }

    @Test
    void isUnique_whenEmailIsNotUnique_thanReturnsFalse() {
        id = 12;
        when(employeeRepository.findOneByEmail(anyString())).thenReturn(employee);
        boolean result = employeeService.isUnique(employee.getEmail(), id);
        verify(employeeRepository).findOneByEmail(stringArgumentCaptor.capture());
        String employeeEmail = stringArgumentCaptor.getValue();
        assertThat(employeeEmail).isEqualTo(employee.getEmail());
        assertThat(result).isEqualTo(false);
    }

    @Test
    void findAll() {
        when(employeeRepository.findAll()).thenReturn(employees);
        Iterable<Employee> employeeIterable = employeeService.findAll();
        List<Employee> employeeList = StreamSupport.stream(employeeIterable.spliterator(), false)
                .collect(Collectors.toList());
        assertThat(employeeList.size()).isEqualTo(employeeList.size());
        assertThat(employeeList.stream().findFirst().get()).isEqualTo(employee);
    }

    @Test
    void delete() {
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.ofNullable(employee));
        employeeService.delete(employee.getId());
        verify(employeeRepository).delete(employeeArgumentCaptor.capture());
        Employee employee1 = employeeArgumentCaptor.getValue();
        assertThat(employee.getId()).isEqualTo(employee1.getId());
    }

    @Test
    void update() {
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
        employeeService.update(employeeRequest);
        verify(employeeRepository).save(employeeArgumentCaptor.capture());
        Employee employee = employeeArgumentCaptor.getValue();
        assertThat(employeeRequest.getId()).isEqualTo(employee.getId());
        assertThat(employeeRequest.getName()).isEqualTo(employee.getName());
        assertThat(employeeRequest.getEmail()).isEqualTo(employee.getEmail());
        assertThat(employeeRequest.getPhone()).isEqualTo(employee.getPhone());
        assertThat(employeeRequest.getDateOfBirth()).isEqualTo(employee.getDateOfBirth());
        assertThat(employeeRequest.getDepartment()).isEqualTo(employee.getDepartment());
    }

    @Test
    void findById() {
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.ofNullable(employee));
        employeeService.findById(employee.getId());
        verify(employeeRepository).findById(integerArgumentCaptor.capture());
        int id = integerArgumentCaptor.getValue();
        assertThat(id).isEqualTo(employee.getId());
    }
}