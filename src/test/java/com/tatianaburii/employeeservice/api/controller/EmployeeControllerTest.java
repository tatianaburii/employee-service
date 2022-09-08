package com.tatianaburii.employeeservice.api.controller;

import com.tatianaburii.employeeservice.api.dto.EmployeeRequestFixture;
import com.tatianaburii.employeeservice.api.dto.EmployeeResponseFixture;
import com.tatianaburii.employeeservice.controller.EmployeeController;
import com.tatianaburii.employeeservice.controller.dto.EmployeeDto;
import com.tatianaburii.employeeservice.controller.dto.EmployeeResponse;
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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EmployeeController.class)
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @MockBean
    EmployeeService employeeService;
    @MockBean
    DepartmentService departmentService;
    @Autowired
    protected MockMvc mockMvc;

    List<Department> departments = List.of(DepartmentFixture.createDepartment());
    List<Employee> employees = List.of(EmployeeFixture.createEmployee());
    Employee employee;
    EmployeeDto employeeRequest;
    EmployeeResponse response;

    @BeforeEach
    public void init() {
        employee = EmployeeFixture.createEmployee();
        response = EmployeeResponseFixture.createEmployeeResponse();
        employeeRequest = EmployeeRequestFixture.createEmployeeRequest();
    }

    @Test
    void showAddForm() throws Exception {
        when(departmentService.findAll()).thenReturn(departments);
        this.mockMvc.perform(get("/employees/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-employee"))
                .andExpect(model().attribute("employee", instanceOf(EmployeeDto.class)))
                .andExpect(model().attribute("departments", equalTo(departments)));
    }

    @Test
    public void findAll() throws Exception {
        when(employeeService.findAll()).thenReturn(employees);
        this.mockMvc.perform(get("/employees"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("employees", equalTo(employees)))
            .andExpect(view().name("employees"));
    }

    @Test
    void create_whenValidInput_thenReturns302() throws Exception {
        when(employeeService.isUnique(anyString())).thenReturn(false);
        when(departmentService.findById(anyLong())).thenReturn(Optional.ofNullable(DepartmentFixture.createDepartment()));
        mockMvc.perform(post("/employees/create")
                        .param("name", employeeRequest.getName())
                        .param("phone", employeeRequest.getPhone())
                        .param("email", employeeRequest.getEmail())
                        .param("dateOfBirth", String.valueOf(employeeRequest.getDateOfBirth()))
                        .param("departmentId", String.valueOf(employeeRequest.getDepartmentId())))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/employees"))
            .andReturn();
    }

    @Test
    public void create_whenInvalidInput_thenReturnsAddForm() throws Exception {
        when(employeeService.isUnique(anyString())).thenReturn(true);
        when(departmentService.findById(anyLong())).thenReturn(Optional.ofNullable(DepartmentFixture.createDepartment()));
        mockMvc.perform(post("/employees/create")
                        .param("name", employeeRequest.getName())
                        .param("phone", employeeRequest.getPhone())
                        .param("email", employeeRequest.getEmail())
                        .param("dateOfBirth", String.valueOf(employeeRequest.getDateOfBirth()))
                        .param("departmentId", String.valueOf(employeeRequest.getDepartmentId())))
                .andExpect(status().isOk())
                .andExpect(view().name("create-employee"));
    }

    @Test
    public void create_whenDepartmentIsNull_thenReturnsDepartmentNotFound() throws Exception {
        when(employeeService.isUnique(anyString())).thenReturn(false);
        when(departmentService.findById(anyLong())).thenReturn(Optional.empty());
        mockMvc.perform(post("/employees/create")
                        .param("name", employeeRequest.getName())
                        .param("phone", employeeRequest.getPhone())
                        .param("email", employeeRequest.getEmail())
                        .param("dateOfBirth", String.valueOf(employeeRequest.getDateOfBirth()))
                        .param("departmentId", String.valueOf(employeeRequest.getDepartmentId())))
                  .andExpect(status().isOk())
                .andExpect(view().name("not-found-error"));
    }

    @Test
    void delete_whenValidInput_thenReturns302() throws Exception {
        int id = 1;
        when(employeeService.findById(anyLong())).thenReturn(Optional.ofNullable(employee));
        mockMvc.perform(get("/employees/{id}/delete", id)
                        .param("id", String.valueOf(id)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"));
    }

    @Test
    void delete_whenInvalidInput_thenReturnsNotFound() throws Exception {
        int id = 1;
        when(employeeService.findById(anyLong())).thenReturn(Optional.empty());
        mockMvc.perform(get("/employees/{id}/delete", id)
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(view().name("not-found-error"));
    }

    @Test
    void showUpdateForm_whenValidInput_thenReturnsUpdateForm() throws Exception {
        Long id = 1L;
        when(employeeService.findById(id)).thenReturn(Optional.ofNullable(employee));
        when(departmentService.findById(id)).thenReturn(Optional.ofNullable(DepartmentFixture.createDepartment()));
        this.mockMvc.perform(get("/employees/{id}/update", id)
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(view().name("update-employee"))
                .andExpect(model().attribute("employee", instanceOf(EmployeeDto.class)));
    }

    @Test
    void showUpdateForm_whenInvalidInput_thenReturnsNotFound() throws Exception {
        Long id = 1L;
        when(employeeService.findById(id)).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/employees/{id}/update", id)
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(view().name("not-found-error"));
    }

    @Test
    void save_whenValidInput_thenReturns200() throws Exception {
        when(employeeService.isUnique(anyString())).thenReturn(false);
        when(departmentService.findById(anyLong())).thenReturn(Optional.ofNullable(DepartmentFixture.createDepartment()));
        mockMvc.perform(post("/employees/save")
                        .param("name", employeeRequest.getName())
                        .param("phone", employeeRequest.getPhone())
                        .param("email", employeeRequest.getEmail())
                        .param("dateOfBirth", String.valueOf(employeeRequest.getDateOfBirth()))
                        .param("departmentId", String.valueOf(employeeRequest.getDepartmentId())))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    public void save_whenInvalidInput_thenReturnsNotFound() throws Exception {
        when(employeeService.isUnique(anyString())).thenReturn(false);
        mockMvc.perform(post("/employees/save")
                        .param("name", employeeRequest.getName())
                        .param("phone", employeeRequest.getPhone())
                        .param("email", employeeRequest.getEmail())
                        .param("dateOfBirth", String.valueOf(employeeRequest.getDateOfBirth()))
                        .param("departmentId", String.valueOf(employeeRequest.getDepartmentId())))
                .andExpect(status().isOk())
                .andExpect(view().name("not-found-error"));
    }

    @Test
    public void save_whenDepartmentIsNull_thenReturnsDepartmentNotFound() throws Exception {
        when(employeeService.isUnique(anyString())).thenReturn(true);
        when(departmentService.findById(anyLong())).thenReturn(null);
        mockMvc.perform(post("/employees/save")
                        .param("name", employeeRequest.getName())
                        .param("phone", employeeRequest.getPhone())
                        .param("email", employeeRequest.getEmail())
                        .param("dateOfBirth", String.valueOf(employeeRequest.getDateOfBirth()))
                        .param("departmentId", String.valueOf(employeeRequest.getDepartmentId())))
                .andExpect(status().isOk())
                .andExpect(view().name("not-found-error"));
    }
}
