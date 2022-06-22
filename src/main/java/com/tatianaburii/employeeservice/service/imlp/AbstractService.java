package com.tatianaburii.employeeservice.service.imlp;

import com.tatianaburii.employeeservice.controller.dto.AbstractRequest;
import com.tatianaburii.employeeservice.domain.AbstractEntity;
import com.tatianaburii.employeeservice.repository.Repository;
import com.tatianaburii.employeeservice.service.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AbstractService<R extends AbstractRequest, E extends AbstractEntity> implements Service<R, E> {
    protected Repository<R, E> repository;

    @Override
    public abstract void save(R abstractRequest);

    @Override
    public boolean isUnique(String param, int id) {
        Optional<Integer> idByParam = repository.findIdByParam(param);
        return idByParam.isEmpty() || idByParam.equals(Optional.of(id));
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
        log.info("Entity with id {} is deleted ", id);
    }

    @Transactional
    @Override
    public void update(R abstractRequest) {
        repository.update(abstractRequest);
        log.info("Entity {} is updated ", abstractRequest);
    }

    @Override
    public E findById(int id) {
        return repository.findById(id);
    }

    protected AbstractService(Repository<R, E> repository) {
        this.repository = repository;
    }
}