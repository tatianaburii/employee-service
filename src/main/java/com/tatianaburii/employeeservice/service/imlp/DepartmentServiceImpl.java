package com.tatianaburii.employeeservice.service.imlp;

import com.tatianaburii.employeeservice.controller.dto.DepartmentRequest;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.repository.DepartmentRepository;
import com.tatianaburii.employeeservice.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository departmentRepository;


    @Override
    public Iterable<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department findById(int id) {
        return departmentRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Department save(DepartmentRequest departmentRequest) {
        Department department = new Department(departmentRequest.getName());
        return departmentRepository.save(department);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Department department = findById(id);
        departmentRepository.delete(department);
    }

    @Override
    public boolean isUnique(String name, int id) {
        Department department = departmentRepository.findOneByName(name);
        return department == null || department.getId() == id;
    }

    @Override
    @Transactional
    public void update(DepartmentRequest departmentRequest) {
        Department d = departmentRepository.findById(departmentRequest.getId()).get();
        d.setName(departmentRequest.getName());
        departmentRepository.save(d);
    }
}
