package com.tatianaburii.employeeservice.domain;

import com.tatianaburii.employeeservice.controller.dto.DepartmentDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "departments")
@Builder
@FieldDefaults(level = PRIVATE)
public class Department {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  Long id;

  @Column
  String name;

  @Column
  @CreationTimestamp
  LocalDateTime createdAt;

  @ToString.Exclude
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "department", fetch = FetchType.LAZY)
  @OrderBy("id asc")
  List<Employee> employees;

  @Column
  @Builder.Default
  Boolean active = true;

  public static Department create(DepartmentDto dto) {
    return Department.builder()
        .name(dto.getName())
        .build();
  }

  public void update(DepartmentDto dto) {
    name = dto.getName();
  }

  public void delete() {
    active = false;
  }

  public DepartmentDto toDto() {
    return DepartmentDto.builder()
        .id(id)
        .name(name)
        .build();
  }
}
