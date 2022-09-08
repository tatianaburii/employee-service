package com.tatianaburii.employeeservice.api.controller;

import com.tatianaburii.employeeservice.api.dto.DepartmentRequestFixture;
import com.tatianaburii.employeeservice.controller.DepartmentController;
import com.tatianaburii.employeeservice.controller.dto.DepartmentDto;
import com.tatianaburii.employeeservice.domain.*;
import com.tatianaburii.employeeservice.service.DepartmentService;
import com.tatianaburii.employeeservice.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DepartmentController.class)
@AutoConfigureMockMvc
class DepartmentControllerTest {
  @Autowired
  protected MockMvc mockMvc;
  @MockBean
  DepartmentService departmentService;
  @MockBean
  EmployeeService employeeService;
  Department department;
  List<Department> departments;

  DepartmentDto departmentRequest;

  @BeforeEach
  public void init() {
    departmentRequest = DepartmentRequestFixture.createDepartmentRequest();
    department = DepartmentFixture.createDepartment();
    departments = List.of(DepartmentFixture.createDepartment());
  }

  @Test
  void showAddForm() throws Exception {
    this.mockMvc.perform(get("/departments/add"))
        .andExpect(status().isOk())
        .andExpect(view().name("create-department"))
        .andExpect(model().attribute("department", instanceOf(DepartmentDto.class)));
  }

  @Test
  public void findAll() throws Exception {
    when(departmentService.findAll()).thenReturn(departments);
    this.mockMvc.perform(get("/departments"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("departments", departments))
        .andExpect(view().name("departments"));
  }

  @Test
  public void create_whenInvalidInput_thanReturnsAddForm() throws Exception {
    when(departmentService.isUnique(anyString())).thenReturn(true);
    mockMvc.perform(post("/departments/create")
            .param("name", departmentRequest.getName()))
        .andExpect(status().isOk())
        .andExpect(view().name("create-department"));

  }

  @Test
  void create_whenValidInput_thenReturns300() throws Exception {
    when(departmentService.isUnique(anyString())).thenReturn(false);
    mockMvc.perform(post("/departments/create")
            .param("name", departmentRequest.getName()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/departments"))
        .andReturn();
  }

  @Test
  void delete_whenValidInput_thenRedirectionToDepartments() throws Exception {
    int id = 1;
    when(departmentService.findById(anyLong())).thenReturn(Optional.ofNullable(department));
    mockMvc.perform(get("/departments/{id}/delete", id)
            .param("id", String.valueOf(id)))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/departments"));
  }

  @Test
  void delete_whenInvalidInput_thenRedirectionToNotFound() throws Exception {
    int id = 1;
    when(departmentService.findById(anyLong())).thenReturn(null);
    mockMvc.perform(get("/departments/{id}/delete", id)
            .param("id", String.valueOf(id)))
        .andExpect(status().isOk())
        .andExpect(view().name("error"));
  }

  @Test
  void showUpdateForm_whenInvalidInput_thenRedirectionToNotFound() throws Exception {
    int id = 1;
    when(departmentService.findById(anyLong())).thenReturn(null);
    this.mockMvc.perform(get("/departments/{id}/update", id)
            .param("id", String.valueOf(id)))
        .andExpect(status().isOk())
        .andExpect(view().name("error"));
  }

  @Test
  void showUpdateForm_whenValidInput_thenReturnForm() throws Exception {
    Long id = 1L;
    when(departmentService.findById(id)).thenReturn(Optional.ofNullable(department));
    this.mockMvc.perform(get("/departments/{id}/update", id)
            .param("id", String.valueOf(id)))
        .andExpect(status().isOk())
        .andExpect(model().attribute("department", instanceOf(DepartmentDto.class)))
        .andExpect(view().name("update-department"));
  }

  @Test
  public void save_whenInvalidInput_ReturnsNotFound() throws Exception {
    when(departmentService.isUnique(anyString())).thenReturn(false);
    mockMvc.perform(post("/departments/save")
            .param("name", departmentRequest.getName()))
        .andExpect(status().isOk())
        .andExpect(view().name("not-found-error"));

  }

  @Test
  void update_whenValidInput_thenReturns200() throws Exception {
    when(departmentService.isUnique(anyString())).thenReturn(false);
    mockMvc.perform(post("/departments/save")
            .param("name", departmentRequest.getName()))
        .andExpect(status().is2xxSuccessful())
        .andReturn();
  }
//todo: it does not work

//  @Test
//  void findById_whenValidInput_thenReturnListOfEmployees() throws Exception {
//    Long id = 1L;
//    when(departmentService.findById(anyLong())).thenReturn(Optional.ofNullable(department));
//    mockMvc.perform(get("/departments/{id}/employees", id)
//            .param("id", String.valueOf(id)))
//        .andExpect(status().isOk())
//        .andExpect(model().attribute("employees", employees))
//        .andExpect(view().name("employees"));
//  }

  @Test
  void findById_whenInvalidInput_thenReturnNotFound() throws Exception {
    int id = 2;
    when(departmentService.findById(anyLong())).thenReturn(Optional.empty());
    mockMvc.perform(get("/departments/{id}/employees", id)
            .param("id", String.valueOf(id)))
        .andExpect(status().isOk())
        .andExpect(view().name("not-found-error"));
  }
}