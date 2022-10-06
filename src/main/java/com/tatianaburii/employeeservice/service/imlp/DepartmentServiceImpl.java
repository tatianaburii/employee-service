package com.tatianaburii.employeeservice.service.imlp;

import com.tatianaburii.employeeservice.api.dto.DepartmentDto;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.repository.DepartmentRepository;
import com.tatianaburii.employeeservice.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class DepartmentServiceImpl implements DepartmentService {
  DepartmentRepository repository;

  @Override
  public List<Department> findAll() {
    return repository.findByActiveTrue();
  }

  @Override
  public Optional<Department> findById(Long id) {
    return repository.findByIdAndActiveTrue(id);
  }

  @Override
  @Transactional
  public Department create(DepartmentDto departmentDto) {
    return save(Department.create(departmentDto));
  }

  private Department save(Department department) {
    return repository.save(department);
  }

  @Override
  @Transactional
  public void delete(Department department) {
    department.delete();
    save(department);
  }

  @Override
  public boolean isUnique(String name) {
    return repository.existsByNameAndActiveTrue(name);
  }

  @Override
  @Transactional
  public void update(Department department, DepartmentDto departmentDto) {
    department.update(departmentDto);
    save(department);
  }
}
