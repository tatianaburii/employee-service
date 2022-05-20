package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.DepartmentRequest;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository repository;

    @Override
    public void save(DepartmentRequest departmentRequest) {
        String name = departmentRequest.getName();
        repository.save(new Department(name));
        log.info("Department created {}", name);
    }

    @Override
    public boolean isUnique(String name) {
        return repository.findIdByName(name) < 0;
    }

    @Override
    public List<Department> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public void update(DepartmentRequest departmentRequest) {
        repository.update(departmentRequest);

    }

    @Override
    public Department findById(int id) {
        return repository.findById(id);
    }
}
