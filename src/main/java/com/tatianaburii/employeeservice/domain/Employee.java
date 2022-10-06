package com.tatianaburii.employeeservice.domain;

import com.tatianaburii.employeeservice.api.dto.EmployeeDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
@Builder
@FieldDefaults(level = PRIVATE)
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  Long id;
  @Column
  String name;
  @Column
  String phone;
  @Column
  String email;
  @Column
  LocalDate dateOfBirth;
  @Column
  @CreationTimestamp
  LocalDateTime createdAt;
  @ManyToOne
  @JoinColumn(name = "department_id")
  Department department;

  @Column
  @Builder.Default
  Boolean active = true;

  public static Employee create(EmployeeDto dto, Department department) {
    return Employee.builder()
        .name(dto.getName())
        .phone(dto.getPhone())
        .email(dto.getEmail())
        .dateOfBirth(dto.getDateOfBirth())
        .department(department)
        .build();
  }

  public void update(EmployeeDto dto, Department d) {
    name = dto.getName();
    phone = dto.getPhone();
    email = dto.getEmail();
    dateOfBirth = dto.getDateOfBirth();
    department = d;
  }

  public EmployeeDto toDto() {
    return EmployeeDto.builder()
        .id(id)
        .name(name)
        .phone(phone)
        .email(email)
        .dateOfBirth(dateOfBirth)
        .departmentId(department.getId())
        .build();
  }

  public void delete() {
    active = false;
  }
}


