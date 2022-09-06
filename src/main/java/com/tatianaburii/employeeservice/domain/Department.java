package com.tatianaburii.employeeservice.domain;

import com.tatianaburii.employeeservice.controller.dto.DepartmentDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "departments")
@Builder
@FieldDefaults(level = PRIVATE)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "department_name")
    String name;
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department", fetch = FetchType.LAZY)
    @OrderBy("id asc")
    List<Employee> employees;

    public Department(String name) {
        this.name = name;
        this.createdAt = LocalDateTime.now();
    }

    public DepartmentDto toDto() {
        return DepartmentDto.builder()
                .id(id)
                .name(name)
                .build();
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
