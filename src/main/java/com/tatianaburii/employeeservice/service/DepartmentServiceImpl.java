package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.DepartmentRequest;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.repository.DepartmentRepository;
import com.tatianaburii.employeeservice.repository.Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DepartmentServiceImpl extends AbstractService<DepartmentRequest, Department> implements DepartmentService {
    DepartmentRepository repository;

    public DepartmentServiceImpl(Repository<DepartmentRequest, Department> abstractRepository, DepartmentRepository repository) {
        super(abstractRepository);
        this.repository = repository;
    }

    @Override
    public void save(DepartmentRequest departmentRequest) {
        String name = departmentRequest.getName();
        repository.save(new Department(name));
        log.info("Department created {}", name);
    }
}
