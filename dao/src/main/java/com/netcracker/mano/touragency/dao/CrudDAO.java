package com.netcracker.mano.touragency.dao;

import com.netcracker.mano.touragency.entity.BaseEntity;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;

import java.util.List;


public interface CrudDAO<T extends BaseEntity> {


    T getById(long id) throws EntityNotFoundException;

    T add(T entity) throws CannotCreateEntityException;

    T update(T entity) throws CannotUpdateEntityException;

    void delete(long id);

    List< T> getAll();
}
