package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.AbstractRequest;
import com.tatianaburii.employeeservice.domain.AbstractEntity;
import com.tatianaburii.employeeservice.repository.AbstractRepository;
import com.tatianaburii.employeeservice.repository.Repository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class AbstractService<R extends AbstractRequest, E extends AbstractEntity> implements Service<R, E> {
    Repository<R, E> repository;

    @Override
    public abstract void save(R abstractRequest);

    @Override
    public boolean isUnique(String param, int id) {
        int idByParam = repository.findIdByParam(param);
        return idByParam < 0 || idByParam == id;
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public void update(R abstractRequest) {
        repository.update(abstractRequest);
    }

    @Override
    public E findById(int id) {
        return repository.findById(id);
    }

    public AbstractService(AbstractRepository<R, E> abstractRepository) {
        this.repository = abstractRepository;
    }
}