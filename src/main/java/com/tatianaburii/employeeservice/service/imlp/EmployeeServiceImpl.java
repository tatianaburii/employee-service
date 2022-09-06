package com.tatianaburii.employeeservice.service.imlp;

import com.tatianaburii.employeeservice.controller.dto.EmployeeDto;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.domain.Employee;
import com.tatianaburii.employeeservice.exceptions.EmployeeNotFoundException;
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
    private static final String EMPLOYEE_NOT_FOUND = "Employee with id = %s not found";

    @Override
    public Iterable<Employee> findAll() {
        return repository.findAll();
    }

    @Override
    public Employee findById(int id) {
        return repository.findById(id)
                .orElseThrow(()-> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND + id));
    }

    @Override
    @Transactional
    public Employee save(EmployeeDto employeeDto, Department department) {
        String name = employeeDto.getName();
        String phone = employeeDto.getPhone();
        String email = employeeDto.getEmail();
        LocalDate dareOfBirth = employeeDto.getDateOfBirth();
        Employee employee = new Employee(name, phone, email, dareOfBirth, department);
        log.info("Employee {} is created");
        return repository.save(employee);
    }

    @Override
    public void delete(int id) {
        Employee employee = findById(id);
        repository.delete(employee);
        log.info("Employee {} is deleted");
    }

    @Override
    public boolean isUnique(String email, int id) {
        Employee employee = repository.findOneByEmail(email);
        return employee == null || employee.getId() == id;
    }

    @Override
    @Transactional
    public void update(EmployeeDto employeeDto, Department department) {
        Employee e = repository.findById(employeeDto.getId())
                .orElseThrow(()-> new EmployeeNotFoundException(String.format(EMPLOYEE_NOT_FOUND, employeeDto.getId())));
        e.setName(employeeDto.getName());
        e.setPhone(employeeDto.getPhone());
        e.setEmail(employeeDto.getEmail());
        e.setDateOfBirth(employeeDto.getDateOfBirth());
        e.setDepartment(department);
        repository.save(e);
        log.info("Employee {} is updated");
    }
}
