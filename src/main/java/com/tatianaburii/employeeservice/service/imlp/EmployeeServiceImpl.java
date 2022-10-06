package com.tatianaburii.employeeservice.service.imlp;

import com.tatianaburii.employeeservice.api.dto.EmployeeDto;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.domain.Employee;
import com.tatianaburii.employeeservice.repository.EmployeeRepository;
import com.tatianaburii.employeeservice.service.EmployeeService;
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
public class EmployeeServiceImpl implements EmployeeService {
  EmployeeRepository repository;

  @Override
  public List<Employee> findAll() {
    return repository.findByActiveTrueAndDepartmentActiveTrue();
  }

  @Override
  public Optional<Employee> findById(Long id) {
    return repository.findByIdAndActiveTrue(id);
  }

  @Override
  @Transactional
  public Employee create(EmployeeDto dto, Department department) {
    return save(Employee.create(dto, department));
  }

  private Employee save(Employee employee) {
    return repository.save(employee);
  }

  @Override
  public void delete(Employee employee) {
    employee.delete();
    save(employee);
  }

  @Override
  public boolean isUnique(String email) {
    return repository.existsByEmailAndActiveTrue(email);
  }

  @Override
  @Transactional
  public void update(Employee employee, EmployeeDto employeeDto, Department department) {
    employee.update(employeeDto, department);
    repository.save(employee);
  }
}
