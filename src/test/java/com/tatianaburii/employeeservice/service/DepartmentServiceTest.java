package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.DepartmentRequest;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.repository.DepartmentRepository;
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
    ArgumentCaptor<DepartmentRequest> departmentRequestArgumentCaptor;
    @Captor
    ArgumentCaptor<Integer> integerArgumentCaptor;
    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;
    DepartmentRequest departmentRequest;
    Department department;

    @BeforeEach
    public void init() {
        department = new Department("Name1");
        departmentRequest = new DepartmentRequest(2, "Name");
    }

    @Test
    void saveDepartmentTest() {
        DepartmentRequest departmentRequest = new DepartmentRequest(1, "Name");
        departmentService.save(departmentRequest);
        verify(departmentRepository).save(departmentArgumentCaptor.capture());
        Department department = departmentArgumentCaptor.getValue();
        assertThat(department.getName()).isEqualTo(departmentRequest.getName());
    }

    @Test
    void isUnique_whenTheSameName_thanReturnsTrue() {
        String name = "Name";
        int id = 1;
        when(departmentRepository.findIdByParam(anyString())).thenReturn(Optional.of(1));
        boolean result = departmentService.isUnique(name, id);
        verify(departmentRepository).findIdByParam(stringArgumentCaptor.capture());
        String departmentName = stringArgumentCaptor.getValue();
        assertThat(departmentName).isEqualTo(name);
        assertThat(result).isEqualTo(true);
    }

    @Test
    void isUnique_whenNameIsUnique_thanReturnsTrue() {
        String name = "Name";
        int id = 1;
        when(departmentRepository.findIdByParam(anyString())).thenReturn(Optional.empty());
        boolean result = departmentService.isUnique(name, id);
        verify(departmentRepository).findIdByParam(stringArgumentCaptor.capture());
        String departmentName = stringArgumentCaptor.getValue();
        assertThat(departmentName).isEqualTo(name);
        assertThat(result).isEqualTo(true);
    }

    @Test
    void isUnique_whenNameIsNotUnique_thanReturnsTrue() {
        String name = "Name";
        int id = 1;
        when(departmentRepository.findIdByParam(anyString())).thenReturn(Optional.of(9));
        boolean result = departmentService.isUnique(name, id);
        verify(departmentRepository).findIdByParam(stringArgumentCaptor.capture());
        String departmentName = stringArgumentCaptor.getValue();
        assertThat(departmentName).isEqualTo(name);
        assertThat(result).isEqualTo(false);
    }

    @Test
    void findAll() {
        Department department2 = new Department("Name2");
        doReturn(List.of(department, department2)).when(departmentRepository).findAll();
        List<Department> departments = departmentService.findAll();
        assertThat(departments.size()).isEqualTo(2);
    }

    @Test
    void delete() {
        departmentService.delete(department.getId());
        verify(departmentRepository).delete(integerArgumentCaptor.capture());
        int id = integerArgumentCaptor.getValue();
        assertThat(id).isEqualTo(department.getId());
    }

    @Test
    void update() {
        departmentService.update(departmentRequest);
        verify(departmentRepository).update(departmentRequestArgumentCaptor.capture());
        DepartmentRequest departmentRequest1 = departmentRequestArgumentCaptor.getValue();
        assertThat(departmentRequest1.getId()).isEqualTo(departmentRequest.getId());
        assertThat(departmentRequest1.getName()).isEqualTo(departmentRequest.getName());
    }

    @Test
    void findById() {
        departmentService.findById(department.getId());
        verify(departmentRepository).findById(integerArgumentCaptor.capture());
        int id = integerArgumentCaptor.getValue();
        assertThat(id).isEqualTo(department.getId());
    }
}