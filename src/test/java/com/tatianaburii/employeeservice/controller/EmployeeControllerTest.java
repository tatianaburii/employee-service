package com.tatianaburii.employeeservice.controller;

import com.tatianaburii.employeeservice.controller.dto.EmployeeRequest;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.domain.Employee;
import com.tatianaburii.employeeservice.service.DepartmentService;
import com.tatianaburii.employeeservice.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.*;
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

    List<Department> departments = List.of(new Department("DepartmentName"));
    List<Employee> employees = List.of(new Employee("Name", "0998877665", "employee1@gmail.com", LocalDate.of(2000, 1, 1), 1));
    EmployeeRequest employeeRequest;

    @BeforeEach
    public void init() {
        employeeRequest = new EmployeeRequest(1, "Name", "0998877665", "employee1@gmail.com", LocalDate.of(2000, 1, 1), 1);
    }

    @Test
    void showAddForm() throws Exception {
        when(departmentService.findAll()).thenReturn(departments);
        this.mockMvc.perform(get("/employees/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-employee"))
                .andExpect(model().attribute("employee", instanceOf(EmployeeRequest.class)))
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
    void create_whenValidInput_thenReturns300() throws Exception {
        when(employeeService.isUnique(anyString(), anyInt())).thenReturn(true);
        mockMvc.perform(post("/employees/create")
                        .param("name", employeeRequest.getName())
                        .param("phone", employeeRequest.getPhone())
                        .param("email", employeeRequest.getEmail())
                        .param("dateOfBirth", String.valueOf(employeeRequest.getDateOfBirth()))
                        .param("departmentId", String.valueOf(employeeRequest.getDepartmentId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"));
    }

    @Test
    public void create_whenInvalidInput_thenReturnsAddForm() throws Exception {
        when(employeeService.isUnique(anyString(), anyInt())).thenReturn(false);
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
    void delete_whenValidInput_thenReturns300() throws Exception {
        int id = 1;
        when(employeeService.findById(anyInt())).thenReturn(employees.get(0));
        mockMvc.perform(get("/employees/{id}/delete", id)
                        .param("id", String.valueOf(id)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"));
    }

    @Test
    void delete_whenInvalidInput_thenReturnsNotFound() throws Exception {
        int id = 1;
        when(employeeService.findById(anyInt())).thenReturn(null);
        mockMvc.perform(get("/employees/{id}/delete", id)
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(view().name("not-found-employee"));
    }

    @Test
    void showUpdateForm_whenValidInput_thenReturnsUpdateForm() throws Exception {
        int id = 1;
        when(employeeService.findById(id)).thenReturn(employees.get(0));
        this.mockMvc.perform(get("/employees/{id}/update", id)
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(view().name("update-employee"))
                .andExpect(model().attribute("employee", instanceOf(Employee.class)));
    }

    @Test
    void showUpdateForm_whenInvalidInput_thenReturnsNotFound() throws Exception {
        int id = 1;
        when(employeeService.findById(id)).thenReturn(null);
        this.mockMvc.perform(get("/employees/{id}/update", id)
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(view().name("not-found-employee"));
    }

    @Test
    void save_whenValidInput_thenReturns300() throws Exception {
        when(employeeService.isUnique(anyString(), anyInt())).thenReturn(true);
        mockMvc.perform(post("/employees/save")
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
    public void save_whenInvalidInput_thenReturnsAddForm() throws Exception {
        when(employeeService.isUnique(anyString(), anyInt())).thenReturn(false);
        mockMvc.perform(post("/employees/save")
                        .param("name", employeeRequest.getName())
                        .param("phone", employeeRequest.getPhone())
                        .param("email", employeeRequest.getEmail())
                        .param("dateOfBirth", String.valueOf(employeeRequest.getDateOfBirth()))
                        .param("departmentId", String.valueOf(employeeRequest.getDepartmentId())))
                .andExpect(status().isOk())
                .andExpect(view().name("update-employee"));
    }
}
