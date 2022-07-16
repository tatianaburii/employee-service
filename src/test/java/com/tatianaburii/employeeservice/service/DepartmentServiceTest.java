package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.DepartmentRequest;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.domain.DepartmentFixture;
import com.tatianaburii.employeeservice.domain.DepartmentRequestFixture;
import com.tatianaburii.employeeservice.repository.DepartmentRepository;
import com.tatianaburii.employeeservice.service.imlp.DepartmentServiceImpl;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {
    @Mock
    private DepartmentRepository departmentRepository;
    @InjectMocks
    DepartmentServiceImpl departmentService;
    @Captor
    ArgumentCaptor<Department> departmentArgumentCaptor;
    @Captor
    ArgumentCaptor<Integer> integerArgumentCaptor;
    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;
    DepartmentRequest departmentRequest;
    Department department;
    List<Department> departments;
    String name;
    int id;

    @BeforeEach
    public void init() {
        department = DepartmentFixture.createDepartment();
        departmentRequest = DepartmentRequestFixture.createDepartmentRequest();
        departments = List.of(department, new Department());
        name = "name1";
    }

    @Test
    void saveDepartmentTest() {
        departmentService.save(departmentRequest);
        verify(departmentRepository).save(departmentArgumentCaptor.capture());
        Department department = departmentArgumentCaptor.getValue();
        assertThat(department.getName()).isEqualTo(departmentRequest.getName());
    }

    @Test
    void isUnique_whenTheSameDepartment_thanReturnsTrue() {
        when(departmentRepository.findOneByName(anyString())).thenReturn(department);
        boolean result = departmentService.isUnique(department.getName(), department.getId());
        verify(departmentRepository).findOneByName(stringArgumentCaptor.capture());
        String departmentName = stringArgumentCaptor.getValue();
        assertThat(departmentName).isEqualTo(department.getName());
        assertThat(result).isEqualTo(true);
    }

    @Test
    void isUnique_whenNameIsUnique_thanReturnsTrue() {
        id = 1;
        when(departmentRepository.findOneByName(anyString())).thenReturn(null);
        boolean result = departmentService.isUnique(name, id);
        verify(departmentRepository).findOneByName(stringArgumentCaptor.capture());
        String departmentName = stringArgumentCaptor.getValue();
        assertThat(departmentName).isEqualTo(name);
        assertThat(result).isEqualTo(true);
    }

    @Test
    void isUnique_whenNameIsNotUnique_thanReturnsFalse() {
        id = 6;
        when(departmentRepository.findOneByName(anyString())).thenReturn(department);
        boolean result = departmentService.isUnique(name, id);
        verify(departmentRepository).findOneByName(stringArgumentCaptor.capture());
        String departmentName = stringArgumentCaptor.getValue();
        assertThat(departmentName).isEqualTo(name);
        assertThat(result).isEqualTo(false);
    }

    @Test
    void findAll() {
        when(departmentRepository.findAll()).thenReturn(departments);
        Iterable<Department> departmentsIterable = departmentService.findAll();
        List<Department> departmentsList =
                StreamSupport.stream(departmentsIterable.spliterator(), false)
                        .collect(Collectors.toList());
        assertThat(departmentsList.size()).isEqualTo(2);
    }

    @Test
    void delete() {
        when(departmentRepository.findById(anyInt())).thenReturn(Optional.of(department));
        departmentService.delete(department.getId());
        verify(departmentRepository).delete(departmentArgumentCaptor.capture());
        Department department1 = departmentArgumentCaptor.getValue();
        assertThat(department.getId()).isEqualTo(department1.getId());
    }

    @Test
    void update() {
        when(departmentRepository.findById(anyInt())).thenReturn(Optional.of(department));
        departmentService.update(departmentRequest);
        verify(departmentRepository).save(departmentArgumentCaptor.capture());
        Department department = departmentArgumentCaptor.getValue();
        assertThat(department.getName()).isEqualTo(departmentRequest.getName());
    }

    @Test
    void findById() {
        when(departmentRepository.findById(anyInt())).thenReturn(Optional.ofNullable(department));
        departmentService.findById(department.getId());
        verify(departmentRepository).findById(integerArgumentCaptor.capture());
        int id = integerArgumentCaptor.getValue();
        assertThat(id).isEqualTo(department.getId());
    }
}