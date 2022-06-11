package com.tatianaburii.employeeservice.controller.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DepartmentRequest extends AbstractRequest {
    public DepartmentRequest(int id, String name) {
        super(id, name);
    }
}
