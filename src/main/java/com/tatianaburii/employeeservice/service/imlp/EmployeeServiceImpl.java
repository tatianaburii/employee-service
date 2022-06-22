package com.tatianaburii.employeeservice.service.imlp;

import com.tatianaburii.employeeservice.controller.dto.EmployeeRequest;
import com.tatianaburii.employeeservice.domain.Employee;
import com.tatianaburii.employeeservice.repository.EmployeeRepository;
import com.tatianaburii.employeeservice.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class EmployeeServiceImpl extends AbstractService<EmployeeRequest, Employee> implements EmployeeService {

    public EmployeeServiceImpl(EmployeeRepository repository) {
        super(repository);
    }

    @Transactional
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
    public List<Employee> findByDepartmentId(int departmentId) {
        return ((EmployeeRepository) repository).findByDepartmentId(departmentId);
    }

}
