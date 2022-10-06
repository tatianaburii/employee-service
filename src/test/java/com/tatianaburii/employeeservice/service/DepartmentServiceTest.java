package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.api.dto.DepartmentDto;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.domain.DepartmentFixture;
import com.tatianaburii.employeeservice.api.dto.DepartmentRequestFixture;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {
  @Mock
  DepartmentRepository departmentRepository;
  @InjectMocks
  DepartmentServiceImpl departmentService;
  @Captor
  ArgumentCaptor<Department> departmentArgumentCaptor;
  @Captor
  ArgumentCaptor<Long> longArgumentCaptor;
  List<Department> departments;
  Department department;
  DepartmentDto dto;
  Long id;

  @BeforeEach
  public void init() {
    department = DepartmentFixture.createDepartment();
    departments = List.of(department);
    dto = DepartmentRequestFixture.createDepartmentRequest();
    id = 3L;
  }
  @Test
  void findAll() {
    when(departmentRepository.findByActiveTrue()).thenReturn(departments);
    List<Department> actualDepartments = departmentService.findAll();
    assertThat(departments).isEqualTo(actualDepartments);
  }

  @Test
  void create() {
    departmentService.create(dto);
    verify(departmentRepository).save(departmentArgumentCaptor.capture());
    Department actualDepartment = departmentArgumentCaptor.getValue();
    assertThat(actualDepartment.getName()).isEqualTo(dto.getName());
  }

  @Test
  void findById() {
    when(departmentRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.ofNullable(department));
    departmentService.findById(department.getId());
    verify(departmentRepository).findByIdAndActiveTrue(longArgumentCaptor.capture());
    id = longArgumentCaptor.getValue();
    assertThat(id).isEqualTo(department.getId());
  }

  @Test
  void update() {
    departmentService.update(department, dto);
    verify(departmentRepository).save(departmentArgumentCaptor.capture());
    Department actualDepartment = departmentArgumentCaptor.getValue();
    assertThat(actualDepartment.getId()).isEqualTo(dto.getId());
    assertThat(actualDepartment.getName()).isEqualTo(dto.getName());
  }

  @Test
  void delete() {
    departmentService.delete(department);
    verify(departmentRepository).save(departmentArgumentCaptor.capture());
    Department actualDepartment = departmentArgumentCaptor.getValue();
    assertThat(actualDepartment.getId()).isEqualTo(dto.getId());
    assertThat(actualDepartment.getActive()).isFalse();
  }
}