package com.tatianaburii.employeeservice.controller;

import com.tatianaburii.employeeservice.controller.dto.DepartmentRequest;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.domain.Employee;
import com.tatianaburii.employeeservice.exceptions.DepartmentNotFoundException;
import com.tatianaburii.employeeservice.service.DepartmentService;
import com.tatianaburii.employeeservice.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = {"/departments"})
@Slf4j
public class DepartmentController {
    private static final String DEPARTMENT_NOT_FOUND = "Department with id = %s not found";
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping(value = {"/add"})
    public String view(Model model) {
        model.addAttribute("department", new DepartmentRequest());
        return "create-department";
    }

    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("department") DepartmentRequest departmentRequest, BindingResult bindingResult, Model model) {
        log.info("Got departmentRequest {}", departmentRequest);
        if (!departmentService.isUnique(departmentRequest.getName(), departmentRequest.getId())) {
            log.warn("Name is not unique: {}", departmentRequest.getName());
            bindingResult.rejectValue("name", "name", "A department already exists for this name.");
        }
        if (bindingResult.hasErrors()) {
            log.warn("There are validations problem, return form.");
            return "create-department";
        }
        departmentService.save(departmentRequest);
        model.addAttribute("department", new DepartmentRequest());
        return "redirect:/departments";
    }

    @GetMapping
    public String findAll(Model model) {
        List<Department> departments = departmentService.findAll();
        model.addAttribute("departments", departments);
        return "departments";
    }

    @GetMapping(value = {"/{id}/delete"})
    public String delete(@PathVariable("id") int id) {
        if (departmentService.findById(id) == null) {
            throw new DepartmentNotFoundException(String.format(DEPARTMENT_NOT_FOUND, id));
        }
        departmentService.delete(id);
        return "redirect:/departments";
    }

    @GetMapping(value = "/{id}/update")
    public String showUpdateForm(@PathVariable int id, Model model) {
        Department department = departmentService.findById(id);
        if (department == null) {
            throw new DepartmentNotFoundException(String.format(DEPARTMENT_NOT_FOUND, id));
        }
        model.addAttribute("department", department);
        return "update-department";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("department") DepartmentRequest departmentRequest, BindingResult bindingResult) {
        if (!departmentService.isUnique(departmentRequest.getName(), departmentRequest.getId())) {
            log.warn("Name is not unique: {}", departmentRequest.getName());
            bindingResult.rejectValue("name", "name", "A department already exists for this name.");
        }
        if (bindingResult.hasErrors()) {
            log.warn("There are validations problem, return form.");
            return "update-department";
        }
        departmentService.update(departmentRequest);
        return "redirect:/departments";
    }

    @GetMapping(value = "/{id}/employees")
    public String findEmployeeByDepartmentId(@PathVariable int id, Model model) {
        if (departmentService.findById(id) == null) {
            throw new DepartmentNotFoundException(String.format(DEPARTMENT_NOT_FOUND, id));
        }
        List<Employee> employees = employeeService.findByDepartmentId(id);
        model.addAttribute("employees", employees);
        return "employees";
    }

}
