package com.tatianaburii.employeeservice.controller;

import com.tatianaburii.employeeservice.controller.dto.EmployeeRequest;
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
        model.addAttribute("employee", new EmployeeRequest());
        model.addAttribute("departments", departmentService.findAll());
        return "create-employee";
    }

    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("employee") EmployeeRequest employeeRequest, BindingResult bindingResult, Model model) {
        log.info("Got EmployeeRequest {}", employeeRequest);
        if (!employeeService.isUnique(employeeRequest.getEmail(), employeeRequest.getId())) {
            log.warn("EmployeeRequest`e email is not unique {}", employeeRequest.getEmail());
            bindingResult.rejectValue("email", "email", "A employee already exists for this email.");
        }
        if (bindingResult.hasErrors()) {
            log.warn("Validation problems, return form.");
            model.addAttribute("departments", departmentService.findAll());
            return "create-employee";
        }
        if (departmentService.findById(employeeRequest.getDepartment().getId()) == null) {
            throw new DepartmentNotFoundException(String.format(DEPARTMENT_NOT_FOUND, employeeRequest.getDepartment()));
        }
        employeeService.save(employeeRequest);
        model.addAttribute("employee", new EmployeeRequest());
        return "redirect:/employees";
    }

    @GetMapping
    public String findAll(Model model) {
        Iterable<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping(value = {"/{id}/delete"})
    public String delete(@PathVariable("id") int id) {
        if (employeeService.findById(id) == null) {
            throw new EmployeeNotFoundException(String.format(EMPLOYEE_NOT_FOUND, id));
        }
        employeeService.delete(id);
        return "redirect:/employees";
    }

    @GetMapping(value = "/{id}/update")
    public String showUpdateForm(@PathVariable int id, Model model) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            throw new EmployeeNotFoundException(String.format(EMPLOYEE_NOT_FOUND, id));
        }
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departmentService.findAll());
        return "update-employee";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("employee") EmployeeRequest employeeRequest, BindingResult bindingResult) {
        if (!employeeService.isUnique(employeeRequest.getEmail(), employeeRequest.getId())){
            log.warn("EmployeeRequest`e email is not unique {}", employeeRequest.getEmail());
            bindingResult.rejectValue("email", "email", "A employee already exists for this email");
        }
        if (bindingResult.hasErrors()) {
            log.warn("Validation problems, return form.");
            return "update-employee";
        }
        if (departmentService.findById(employeeRequest.getDepartment().getId()) == null) {
            throw new DepartmentNotFoundException(String.format(DEPARTMENT_NOT_FOUND, employeeRequest.getDepartment()));
        }
        employeeService.update(employeeRequest);
        return "redirect:/employees";
    }
}
