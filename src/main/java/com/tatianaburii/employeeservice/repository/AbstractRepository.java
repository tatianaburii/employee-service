package com.tatianaburii.employeeservice.repository;

import com.tatianaburii.employeeservice.controller.dto.AbstractRequest;
import com.tatianaburii.employeeservice.domain.AbstractEntity;


import java.util.List;
public abstract class AbstractRepository<R extends AbstractRequest, E extends AbstractEntity> implements Repository<R, E> {
//ToDo: refactor methods
    @Override
    public abstract void save(E abstractEntity);

    @Override
    public abstract int findIdByParam(String name);

    @Override
    public abstract List<E> findAll();

    @Override
    public abstract void delete(int id);

    @Override
    public abstract void update(R abstractRequest);

    @Override
    public abstract E findById(int id);
    public AbstractRepository() {
    }
}
