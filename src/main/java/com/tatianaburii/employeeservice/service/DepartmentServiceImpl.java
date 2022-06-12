package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.DepartmentRequest;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.repository.DepartmentRepository;
import com.tatianaburii.employeeservice.repository.EmployeeRepository;
import com.tatianaburii.employeeservice.repository.Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DepartmentServiceImpl extends AbstractService<DepartmentRequest, Department> implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentServiceImpl(Repository<DepartmentRequest, Department> repository, DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        super(repository);
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void save(DepartmentRequest departmentRequest) {
        String name = departmentRequest.getName();
        departmentRepository.save(new Department(name));
        log.info("Department created {}", name);
    }

    @Override
    public void delete(int id) {
        departmentRepository.delete(id);
        employeeRepository.findByDepartmentId(id).forEach(employee -> employeeRepository.delete(employee.getId()));
    }
}
