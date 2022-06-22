package com.tatianaburii.employeeservice.service.imlp;

import com.tatianaburii.employeeservice.controller.dto.DepartmentRequest;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.repository.DepartmentRepository;
import com.tatianaburii.employeeservice.repository.EmployeeRepository;
import com.tatianaburii.employeeservice.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class DepartmentServiceImpl extends AbstractService<DepartmentRequest, Department> implements DepartmentService {
    private final EmployeeRepository employeeRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        super(departmentRepository);
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    @Override
    public void save(DepartmentRequest departmentRequest) {
        String name = departmentRequest.getName();
        repository.save(new Department(name));
        log.info("Department created {}", name);
    }

    @Transactional
    @Override
    public void delete(int id) {
        repository.delete(id);
        log.info("Department with id {} is deleted", id);
        employeeRepository.findByDepartmentId(id).forEach(employee -> employeeRepository.delete(employee.getId()));
        log.info("Employees with departmentId {} are deleted", id);
    }
}
