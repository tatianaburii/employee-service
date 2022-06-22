package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.EmployeeRequest;
import com.tatianaburii.employeeservice.domain.Employee;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    @Captor
    ArgumentCaptor<EmployeeRequest> employeeRequestArgumentCaptor;
    EmployeeRequest employeeRequest;
    Employee employee;
    String email;
    int id;

    @BeforeEach
    public void init() {
        employee = new Employee("Name1", "0998877665", "emp1@gmail.com", LocalDate.of(2000, 1, 1), 1);
        employeeRequest = new EmployeeRequest(1, "Name", "0998877665", "employee1@gmail.com", LocalDate.of(2000, 1, 1), 1);
        email = "employee@email.com";
        id = 1;
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
        assertThat(employee.getDepartmentId()).isEqualTo(employeeRequest.getDepartmentId());
    }

    @Test
    void isUnique_whenTheSameEmail_thanReturnsTrue() {
        when(employeeRepository.findIdByParam(anyString())).thenReturn(Optional.of(id));
        boolean result = employeeService.isUnique(email, id);
        verify(employeeRepository).findIdByParam(stringArgumentCaptor.capture());
        String employeeEmail = stringArgumentCaptor.getValue();
        assertThat(employeeEmail).isEqualTo(email);
        assertThat(result).isEqualTo(true);
    }

    @Test
    void isUnique_whenEmailIsUnique_thanReturnsTrue() {
        when(employeeRepository.findIdByParam(anyString())).thenReturn(Optional.empty());
        boolean result = employeeService.isUnique(email, id);
        verify(employeeRepository).findIdByParam(stringArgumentCaptor.capture());
        String employeeEmail = stringArgumentCaptor.getValue();
        assertThat(employeeEmail).isEqualTo(email);
        assertThat(result).isEqualTo(true);
    }

    @Test
    void isUnique_whenEmailIsNotUnique_thanReturnsTrue() {
        when(employeeRepository.findIdByParam(anyString())).thenReturn(Optional.of(9));
        boolean result = employeeService.isUnique(email, id);
        verify(employeeRepository).findIdByParam(stringArgumentCaptor.capture());
        String employeeEmail = stringArgumentCaptor.getValue();
        assertThat(employeeEmail).isEqualTo(email);
        assertThat(result).isEqualTo(false);
    }

    @Test
    void findAll() {
        Employee employee2 = new Employee("Name2", "0998877665", "emp2@gmail.com", LocalDate.of(2000, 1, 1), 1);
        doReturn(List.of(employee, employee2)).when(employeeRepository).findAll();
        List<Employee> employees = employeeService.findAll();
        assertThat(employees.size()).isEqualTo(2);
    }

    @Test
    void delete() {
        employeeService.delete(employee.getId());
        verify(employeeRepository).delete(integerArgumentCaptor.capture());
        int id = integerArgumentCaptor.getValue();
        assertThat(id).isEqualTo(employee.getId());
    }

    @Test
    void update() {
        employeeService.update(employeeRequest);
        verify(employeeRepository).update(employeeRequestArgumentCaptor.capture());
        EmployeeRequest employeeRequest1 = employeeRequestArgumentCaptor.getValue();
        assertThat(employeeRequest1.getId()).isEqualTo(employeeRequest.getId());
        assertThat(employeeRequest1.getName()).isEqualTo(employeeRequest.getName());
        assertThat(employeeRequest1.getEmail()).isEqualTo(employeeRequest.getEmail());
        assertThat(employeeRequest1.getPhone()).isEqualTo(employeeRequest.getPhone());
        assertThat(employeeRequest1.getDateOfBirth()).isEqualTo(employeeRequest.getDateOfBirth());
        assertThat(employeeRequest1.getDepartmentId()).isEqualTo(employeeRequest.getDepartmentId());
    }

    @Test
    void findById() {
        employeeService.findById(employee.getId());
        verify(employeeRepository).findById(integerArgumentCaptor.capture());
        int id = integerArgumentCaptor.getValue();
        assertThat(id).isEqualTo(employee.getId());
    }

    @Test
    void findByDepartmentId() {
        employeeService.findByDepartmentId(employee.getDepartmentId());
        verify(employeeRepository).findByDepartmentId(integerArgumentCaptor.capture());
        int id = integerArgumentCaptor.getValue();
        assertThat(id).isEqualTo(employee.getDepartmentId());
    }
}