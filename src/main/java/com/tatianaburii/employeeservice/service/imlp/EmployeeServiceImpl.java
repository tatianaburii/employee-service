package com.tatianaburii.employeeservice.service.imlp;

import com.tatianaburii.employeeservice.controller.dto.EmployeeRequest;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.domain.Employee;
import com.tatianaburii.employeeservice.repository.EmployeeRepository;
import com.tatianaburii.employeeservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeRepository repository;

    @Override
    public Iterable<Employee> findAll() {
        return repository.findAll();
    }

    @Override
    public Employee findById(int id) {
        return repository.findById(id).get();
    }

    @Override
    @Transactional
    public Employee save(EmployeeRequest employeeRequest) {
        String name = employeeRequest.getName();
        String phone = employeeRequest.getPhone();
        String email = employeeRequest.getEmail();
        LocalDate dareOfBirth = employeeRequest.getDateOfBirth();
        Department department = employeeRequest.getDepartment();
        Employee employee = new Employee(name, phone, email, dareOfBirth, department);
        return repository.save(employee);
    }

    @Override
    public void delete(int id) {
        Employee employee = findById(id);
        repository.delete(employee);
    }

    @Override
    public boolean isUnique(String email, int id) {
        Employee employee = repository.findOneByEmail(email);
        return employee == null || employee.getId() == id;
    }

    @Override
    @Transactional
    public void update(EmployeeRequest employeeRequest) {
        Employee e = repository.findById(employeeRequest.getId()).get();
        e.setName(employeeRequest.getName());
        e.setPhone(employeeRequest.getPhone());
        e.setEmail(employeeRequest.getEmail());
        e.setDateOfBirth(employeeRequest.getDateOfBirth());
        e.setDepartment(employeeRequest.getDepartment());
        repository.save(e);
    }
}
