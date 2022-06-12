package com.tatianaburii.employeeservice.repository;

import com.tatianaburii.employeeservice.controller.dto.AbstractRequest;
import com.tatianaburii.employeeservice.domain.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface Repository<R extends AbstractRequest, E extends AbstractEntity> {
    void save(E abstractEntity);

    Optional<Integer> findIdByParam(String name);

    List<E> findAll();

    void delete(int id);

    void update(R abstractRequest);

    E findById(int id);
}