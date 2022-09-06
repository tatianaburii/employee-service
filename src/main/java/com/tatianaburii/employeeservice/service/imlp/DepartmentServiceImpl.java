package com.tatianaburii.employeeservice.service.imlp;

import com.tatianaburii.employeeservice.controller.dto.DepartmentDto;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.exceptions.DepartmentNotFoundException;
import com.tatianaburii.employeeservice.repository.DepartmentRepository;
import com.tatianaburii.employeeservice.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository departmentRepository;
    private static final String DEPARTMENT_NOT_FOUND = "Department with id = %s not found";


    @Override
    public Iterable<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department findById(int id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(DEPARTMENT_NOT_FOUND + id));
    }

    @Override
    @Transactional
    public Department save(DepartmentDto departmentDto) {
        Department department = new Department(departmentDto.getName());
        log.info("Department {} is created");
        return departmentRepository.save(department);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Department department = findById(id);
        departmentRepository.delete(department);
        log.info("Department {} is deleted");
    }

    @Override
    public boolean isUnique(String name, int id) {
        Department department = departmentRepository.findOneByName(name);
        return department == null || department.getId() == id;
    }

    @Override
    @Transactional
    public void update(DepartmentDto departmentDto) {
        Department d = departmentRepository.findById(departmentDto.getId())
                .orElseThrow(() -> new DepartmentNotFoundException(String.format(DEPARTMENT_NOT_FOUND, departmentDto.getId())));
        d.setName(departmentDto.getName());
        departmentRepository.save(d);
        log.info("Department {} is updated");
    }
}
