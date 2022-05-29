package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.EmployeeRequest;
import com.tatianaburii.employeeservice.domain.Employee;
import com.tatianaburii.employeeservice.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
        LocalDate dareOfBirth = employeeRequest.getDateOfBirth();
        int departmentId = employeeRequest.getDepartmentId();
        repository.save(new Employee(name, phone, email, dareOfBirth, departmentId));
        log.info("Employee created {}", name);
    }

    @Override
    public boolean isUnique(String email, int id) {
        int idByEmail = repository.findIdByEmail(email);
        return idByEmail < 0 || idByEmail == id;
    }

    @Override
    public List<Employee> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public void update(EmployeeRequest employeeRequest) {
        repository.update(employeeRequest);
    }

    @Override
    public Employee findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Employee> findByDepartmentId(int departmentId) {
        return repository.findByDepartmentId(departmentId);
    }
}
