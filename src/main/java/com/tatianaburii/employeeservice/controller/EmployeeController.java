package com.tatianaburii.employeeservice.controller;

import com.tatianaburii.employeeservice.controller.dto.EmployeeRequest;
import com.tatianaburii.employeeservice.domain.Employee;
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
@RequestMapping(value = {"/employees"})
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

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
            log.info("EmployeeRequest`e email is not unique {}", employeeRequest.getEmail());
            bindingResult.rejectValue("email", "email", "A employee already exists for this email.");
        }
        if (bindingResult.hasErrors()) {
            log.info("Validation problems, return form.");
            model.addAttribute("departments", departmentService.findAll());
            return "create-employee";
        }
        employeeService.save(employeeRequest);
        model.addAttribute("employee", new EmployeeRequest());
        return "redirect:/employees";
    }

    @GetMapping
    public String findAll(Model model) {
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping(value = {"/{id}/delete"})
    public String delete(@PathVariable("id") int id) {
        if (employeeService.findById(id) == null) {
            log.info("Employee with id {} is not found, return not-found page", id);
            return "not-found-employee";
        }
        employeeService.delete(id);
        return "redirect:/employees";
    }

    @GetMapping(value = "/{id}/update")
    public String showUpdateForm(@PathVariable int id, Model model) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            log.info("Employee with id {} is not found, return not-found page", id);
            return "not-found-employee";
        }
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departmentService.findAll());
        return "update-employee";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("employee") EmployeeRequest employeeRequest, BindingResult bindingResult) {
        if (!employeeService.isUnique(employeeRequest.getEmail(), employeeRequest.getId())) {
            log.info("EmployeeRequest`e email is not unique {}", employeeRequest.getEmail());
            bindingResult.rejectValue("email", "email", "A employee already exists for this email");
        }
        if (bindingResult.hasErrors()) {
            log.info("Validation problems, return form.");
            return "update-employee";
        }
        employeeService.update(employeeRequest);
        return "redirect:/employees";
    }
}
