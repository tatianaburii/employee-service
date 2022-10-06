package com.tatianaburii.employeeservice.api.controller;

import com.tatianaburii.employeeservice.api.dto.DepartmentDto;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.domain.Employee;
import com.tatianaburii.employeeservice.api.exceptions.DepartmentNotFoundException;
import com.tatianaburii.employeeservice.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Controller
@RequestMapping(value = {"/departments"})
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class DepartmentController {
  private static final String DEPARTMENT_NOT_FOUND = "Department with id = %s not found";
  DepartmentService departmentService;

  @GetMapping(value = {"/add"})
  public String showAddForm(Model model) {
    model.addAttribute("department", new DepartmentDto());
    return "create-department";
  }

  @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
  public String create(@Valid @ModelAttribute("department")
                       DepartmentDto departmentDto,
                       BindingResult bindingResult, Model model) {
    log.info("Got departmentRequest {}", departmentDto);
    if (departmentService.isUnique(departmentDto.getName())) {
      log.warn("Name is not unique: {}", departmentDto.getName());
      bindingResult.rejectValue("name", "name", "A department already exists for this name.");
    }
    if (bindingResult.hasErrors()) {
      log.warn("There are validations problem, return form.");
      return "create-department";
    }
    departmentService.create(departmentDto);
    model.addAttribute("department", new DepartmentDto());
    return "redirect:/departments";
  }

  @GetMapping
  public String findAll(Model model) {
    Iterable<Department> departments = departmentService.findAll();
    model.addAttribute("departments", departments);
    return "departments";
  }

  @GetMapping(value = {"/{id}/delete"})
  public String delete(
      @PathVariable("id") Long id) {
    Department department = departmentService.findById(id)
        .orElseThrow(() -> new DepartmentNotFoundException(String.format(DEPARTMENT_NOT_FOUND, id)));
    departmentService.delete(department);
    return "redirect:/departments";
  }

  @GetMapping(value = "/{id}/update")
  public String showUpdateForm(
      @PathVariable Long id, Model model) {
    Department department = departmentService.findById(id)
        .orElseThrow(() -> new DepartmentNotFoundException(String.format(DEPARTMENT_NOT_FOUND, id)));
    DepartmentDto departmentDto = department.toDto();
    model.addAttribute("department", departmentDto);
    return "update-department";
  }

  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public String update(@Valid
                       @ModelAttribute("department")
                       DepartmentDto dto, BindingResult bindingResult) {
    Department department = departmentService.findById(dto.getId())
        .orElseThrow(() -> new DepartmentNotFoundException(String.format(DEPARTMENT_NOT_FOUND, dto.getId())));
    if (departmentService.isUnique(dto.getName())) {
      log.warn("Name is not unique: {}", dto.getName());
      bindingResult.rejectValue("name", "name", "A department already exists for this name.");
    }
    if (bindingResult.hasErrors()) {
      log.warn("There are validations problem, return form.");
      return "update-department";
    }
    departmentService.update(department, dto);
    return "redirect:/departments";
  }

  @GetMapping(value = "/{id}/employees")
  public String findEmployeeByDepartmentId(
      @PathVariable Long id, Model model) {
    Department department = departmentService.findById(id)
        .orElseThrow(() -> new DepartmentNotFoundException(String.format(DEPARTMENT_NOT_FOUND, id)));
    Iterable<Employee> employees = department.getEmployees().stream()
        .filter(Employee::getActive).collect(Collectors.toList());
    model.addAttribute("employees", employees);
    return "employees";
  }
}
