package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.AbstractRequest;
import com.tatianaburii.employeeservice.domain.AbstractEntity;

import java.util.List;

public interface Service<R extends AbstractRequest, E extends AbstractEntity> {
    void save(R abstractRequest);

    boolean isUnique(String param, int id);

    List<E> findAll();

    void delete(int id);

    void update(R abstractRequest);

    E findById(int id);
}