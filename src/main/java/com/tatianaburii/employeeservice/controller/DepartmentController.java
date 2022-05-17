package com.tatianaburii.employeeservice.controller;

import com.tatianaburii.employeeservice.controller.dto.DepartmentRequest;
import com.tatianaburii.employeeservice.service.DepartmentService;
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
        return "create-department";//ToDo return "departments" view;
    }


}
