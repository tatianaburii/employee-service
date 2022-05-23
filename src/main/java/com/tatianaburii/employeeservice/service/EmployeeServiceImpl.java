package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.EmployeeRequest;
import com.tatianaburii.employeeservice.domain.Employee;
import com.tatianaburii.employeeservice.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeRepository repository;

    @Override
    public void save(EmployeeRequest employeeRequest) {
        String name = employeeRequest.getName();
        String phone = employeeRequest.getPhone();
        String email = employeeRequest.getEmail();
        int departmentId = employeeRequest.getDepartmentId();
        repository.save(new Employee(name, phone, email, departmentId));
        log.info("Employee created {}", name);
    }

    @Override
    public boolean isUnique(String email) {
        return repository.findIdByEmail(email) < 0;
    }
}
