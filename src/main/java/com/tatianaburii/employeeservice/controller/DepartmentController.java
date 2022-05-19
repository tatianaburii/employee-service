package com.tatianaburii.employeeservice.controller;

import com.tatianaburii.employeeservice.controller.dto.DepartmentRequest;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/departments"})
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class DepartmentController {
    DepartmentService departmentService;

    @GetMapping(value = {"/add"})
    public String view(Model model) {
        model.addAttribute("department", new DepartmentRequest());
        return "create-department";
    }

    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("department") DepartmentRequest departmentRequest, BindingResult bindingResult, Model model) {
        if (!departmentService.isUnique(departmentRequest.getName())) {
            bindingResult.rejectValue("name", "name", "A department already exists for this name.");
        }
        if (bindingResult.hasErrors()){
            return "create-department";
        }
        departmentService.save(departmentRequest);
        model.addAttribute("department", new DepartmentRequest());
        return "redirect:/departments";
    }
    @GetMapping
    public String findAll(Model model){
        List<Department> departments = departmentService.findAll();
        model.addAttribute("departments", departments);
        return "departments";
    }
    @GetMapping( value = {"{id}/delete"})
    public String delete(@PathVariable("id")int id){
        departmentService.delete(id);
        return "redirect:/departments";
    }
}
