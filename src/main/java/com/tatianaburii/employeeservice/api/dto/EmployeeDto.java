package com.tatianaburii.employeeservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class EmployeeDto {
  Long id;
  @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
  String name;
  @Size(min = 9, max = 30, message = "Phone number must be between 9 and 30 characters")
  String phone;
  @Email
  @Size(min = 10, max = 30, message = "Email must be between 10 and 30 characters")
  String email;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @NotNull
  LocalDate dateOfBirth;
  @NotNull
  Long departmentId;
}

