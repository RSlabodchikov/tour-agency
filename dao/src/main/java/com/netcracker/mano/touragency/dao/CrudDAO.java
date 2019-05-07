package com.netcracker.mano.touragency.dao;

import com.netcracker.mano.touragency.entity.BaseEntity;

import java.util.List;


public interface CrudDAO<T extends BaseEntity> {


    T getById(long id);

    T add(T entity);

    T update(T entity);

    void delete(long id);

    List< T> getAll();
}
