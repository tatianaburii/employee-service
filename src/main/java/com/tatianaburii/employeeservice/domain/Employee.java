package com.tatianaburii.employeeservice.domain;

import com.tatianaburii.employeeservice.controller.dto.EmployeeDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    int id;
    @Column
    String name;
    @Column
    String phone;
    @Column
    String email;
    @Column
    LocalDate dateOfBirth;
    @Column
    LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "department_id")
    Department department;

    public Employee(String name, String phone, String email, LocalDate dateOfBirth, Department department) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.dateOfBirth = dateOfBirth;
        this.department = department;
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
}


