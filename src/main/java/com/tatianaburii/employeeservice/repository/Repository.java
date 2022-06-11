package com.tatianaburii.employeeservice.repository;

import com.tatianaburii.employeeservice.controller.dto.AbstractRequest;
import com.tatianaburii.employeeservice.domain.AbstractEntity;

import java.util.List;

public interface Repository<R extends AbstractRequest, E extends AbstractEntity> {
    void save(E abstractEntity);

    int findIdByParam(String name);

    List<E> findAll();

    void delete(int id);

    void update(R abstractRequest);

    E findById(int id);
}