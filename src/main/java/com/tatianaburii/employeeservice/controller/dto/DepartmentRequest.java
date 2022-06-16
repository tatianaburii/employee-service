package com.tatianaburii.employeeservice.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class DepartmentRequest extends AbstractRequest {
    public DepartmentRequest(int id, String name) {
        super(id, name);
    }

    public DepartmentRequest() {
    }
}
