package com.tatianaburii.employeeservice.controller;

import com.tatianaburii.employeeservice.controller.dto.EmployeeDto;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.domain.Employee;
import com.tatianaburii.employeeservice.exceptions.DepartmentNotFoundException;
import com.tatianaburii.employeeservice.exceptions.EmployeeNotFoundException;
import com.tatianaburii.employeeservice.service.DepartmentService;
import com.tatianaburii.employeeservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static lombok.AccessLevel.PRIVATE;

@Controller
@RequestMapping(value = {"/employees"})
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class EmployeeController {

    private static final String EMPLOYEE_NOT_FOUND = "Employee with id = %s not found";
    private static final String DEPARTMENT_NOT_FOUND = "Department with id = %s not found";
    EmployeeService employeeService;
    DepartmentService departmentService;

    @GetMapping(value = {"/add"})
    public String showAddForm(Model model) {
        model.addAttribute("employee", new EmployeeDto());
        model.addAttribute("departments", departmentService.findAll());
        return "create-employee";
    }

    @PostMapping(value = {"/create"})
    public String create(@Valid @ModelAttribute("employee") EmployeeDto dto, BindingResult bindingResult, Model model) {
        log.info("Got EmployeeRequest {}", dto);
        if (employeeService.isUnique(dto.getEmail())) {
            log.warn("EmployeeRequest`e email is not unique {}", dto.getEmail());
            bindingResult.rejectValue("email", "email", "A employee already exists for this email.");
        }
        if (bindingResult.hasErrors()) {
            log.warn("Validation problems, return form.");
            model.addAttribute("departments", departmentService.findAll());
            return "create-employee";
        }
        Department department = departmentService.findById(dto.getDepartmentId())
            .orElseThrow(() -> new DepartmentNotFoundException(String.format(DEPARTMENT_NOT_FOUND, dto.getDepartmentId())));
        employeeService.create(dto, department);
        model.addAttribute("departments", departmentService.findAll());
        return "redirect:/employees";
    }

    @GetMapping
    public String findAll(Model model) {
        Iterable<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);
        model.addAttribute("departments", departmentService.findAll());
        return "employees";
    }

    @GetMapping(value = {"/{id}/delete"})
    public String delete(@PathVariable("id") Long id) {
        Employee employee = employeeService.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException(String.format(EMPLOYEE_NOT_FOUND, id)));
        employeeService.delete(employee);
        return "redirect:/employees";
    }

    @GetMapping(value = "/{id}/update")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException(String.format(EMPLOYEE_NOT_FOUND, id)));
        EmployeeDto employeeDto = employee.toDto();
        model.addAttribute("employee", employeeDto);
        model.addAttribute("departments", departmentService.findAll());
        return "update-employee";
    }

    @PostMapping(value = "/save")
    public String update(@Valid @ModelAttribute("employee") EmployeeDto dto, BindingResult bindingResult) {
        Employee employee = employeeService.findById(dto.getId())
            .orElseThrow(() -> new EmployeeNotFoundException(String.format(EMPLOYEE_NOT_FOUND, dto.getId())));
        if (!employeeService.isUnique(dto.getEmail())){
            log.warn("EmployeeRequest`e email is not unique {}", dto.getEmail());
            bindingResult.rejectValue("email", "email", "A employee already exists for this email");
        }
        if (bindingResult.hasErrors()) {
            log.warn("Validation problems, return form.");
            return "update-employee";
        }
        Department department = departmentService.findById(dto.getDepartmentId())
            .orElseThrow(() -> new DepartmentNotFoundException(String.format(DEPARTMENT_NOT_FOUND, dto.getDepartmentId())));
        employeeService.update(employee, dto, department);
        return "redirect:/employees";
    }
}
