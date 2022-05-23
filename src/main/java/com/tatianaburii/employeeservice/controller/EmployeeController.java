package com.tatianaburii.employeeservice.controller;

import com.tatianaburii.employeeservice.controller.dto.EmployeeRequest;
import com.tatianaburii.employeeservice.service.DepartmentService;
import com.tatianaburii.employeeservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

import static lombok.AccessLevel.PRIVATE;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/employees"})
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class EmployeeController {
    EmployeeService employeeService;
    DepartmentService departmentService;

    @GetMapping(value = {"/add"})
    public String view(Model model) {
        model.addAttribute("employee", new EmployeeRequest());
        model.addAttribute("departments", departmentService.findAll());
        return "create-employee";
    }

    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("employee")EmployeeRequest employeeRequest, BindingResult bindingResult, Model model) {
        if (!employeeService.isUnique(employeeRequest.getEmail())) {
            bindingResult.rejectValue("email", "email", "A employee already exists for this email.");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentService.findAll());
            return "create-employee";
        }
        employeeService.save(employeeRequest);
        model.addAttribute("employee", new EmployeeRequest());
        return "redirect:/employees/add";//todo: "redirect:/employees"
    }
}
