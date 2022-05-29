package com.tatianaburii.employeeservice.controller;

import com.tatianaburii.employeeservice.controller.dto.DepartmentRequest;
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
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.anyInt;
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
    List<Department> departments = List.of(new Department("DepartmentName"));
    List<Employee> employees = List.of(new Employee("Name", "0998877665", "employee1@gmail.com", LocalDate.of(2000, 1, 1), 1));
    DepartmentRequest departmentRequest;

    @BeforeEach
    public void init() {
        departmentRequest = new DepartmentRequest(1, "Name");
    }

    @Test
    void showAddForm() throws Exception {
        this.mockMvc.perform(get("/departments/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-department"))
                .andExpect(model().attribute("department", instanceOf(DepartmentRequest.class)));
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
        when(departmentService.isUnique(anyString(), anyInt())).thenReturn(false);
        mockMvc.perform(post("/departments/create")
                        .param("name", departmentRequest.getName()))
                .andExpect(status().isOk())
                .andExpect(view().name("create-department"));

    }

    @Test
    void create_whenValidInput_thenReturns300() throws Exception {
        when(departmentService.isUnique(anyString(), anyInt())).thenReturn(true);
        mockMvc.perform(post("/departments/create")
                        .param("name", departmentRequest.getName()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/departments"))
                .andReturn();
    }

    @Test
    void delete_whenValidInput_thenRedirectionToDepartments() throws Exception {
        int id = 1;
        when(departmentService.findById(anyInt())).thenReturn(departments.get(0));
        mockMvc.perform(get("/departments/{id}/delete", id)
                        .param("id", String.valueOf(id)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/departments"));
    }

    @Test
    void delete_whenInvalidInput_thenRedirectionToNotFound() throws Exception {
        int id = 1;
        when(departmentService.findById(anyInt())).thenReturn(null);
        mockMvc.perform(get("/departments/{id}/delete", id)
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(view().name("not-found-department"));
    }

    @Test
    void showUpdateForm_whenInvalidInput_thenRedirectionToNotFound() throws Exception {
        int id = 1;
        when(departmentService.findById(anyInt())).thenReturn(null);
        this.mockMvc.perform(get("/departments/{id}/update", id)
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(view().name("not-found-department"));

    }

    @Test
    void showUpdateForm_whenValidInput_thenReturnForm() throws Exception {
        int id = 1;
        when(departmentService.findById(id)).thenReturn(departments.get(0));
        this.mockMvc.perform(get("/departments/{id}/update", id)
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("department", instanceOf(Department.class)))
                .andExpect(view().name("update-department"));
    }

    @Test
    public void save_whenInvalidInput_getAddForm() throws Exception {
        when(departmentService.isUnique(anyString(), anyInt())).thenReturn(false);
        mockMvc.perform(post("/departments/save")
                        .param("name", departmentRequest.getName()))
                .andExpect(status().isOk())
                .andExpect(view().name("update-department"));

    }

    @Test
    void save_whenValidInput_thenReturns300() throws Exception {
        when(departmentService.isUnique(anyString(), anyInt())).thenReturn(true);
        mockMvc.perform(post("/departments/save")
                        .param("name", departmentRequest.getName()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/departments"))
                .andReturn();
    }

    @Test
    void findById_whenValidInput_thenReturnListOfEmployees() throws Exception {
        int id = 1;
        when(employeeService.findByDepartmentId(anyInt())).thenReturn(employees);
        mockMvc.perform(get("/departments/{id}/employees", id)
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("employees", employees))
                .andExpect(view().name("employees"));
    }
    @Test
    void findById_whenInvalidInput_thenReturnNotFound() throws Exception {
        int id = 2;
        when(employeeService.findByDepartmentId(anyInt())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/departments/{id}/employees", id)
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(view().name("not-found-department"));
    }
}