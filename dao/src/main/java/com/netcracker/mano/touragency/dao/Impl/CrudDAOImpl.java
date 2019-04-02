package com.netcracker.mano.touragency.dao.Impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.mano.touragency.dao.CrudDAO;
import com.netcracker.mano.touragency.entity.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class CrudDAOImpl<T extends BaseEntity> implements CrudDAO<T> {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerSubtypes(User.class, Booking.class, CreditCard.class, Tour.class);
    }

    private Map<Long, T> map = new HashMap<>();
    private String filename;

    CrudDAOImpl(String filename) {
        this.filename = filename;

        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        try {
            File file = new File(this.filename);
            if (file.length() == 0) return;
            TypeReference<List<T>> mapType = new TypeReference<List<T>>() {
            };
            List<T> list = mapper.readValue(new File(this.filename), mapType);
            map = list.stream()
                    .collect(Collectors.toMap(T::getId, T -> T));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private long getLastId(Map<Long, T> entityMap) {
        return entityMap.keySet()
                .stream()
                .max(Long::compareTo)
                .orElse(0L);
    }

    @Override
    public T getById(long id) {

        return map.get(id);
    }

    @Override
    public T add(T entity) {

        if (map.isEmpty()) {
            entity.setId(1L);
            map.put(1L, entity);
        } else {
            long lastId = getLastId(map);
            entity.setId(lastId + 1L);
            map.put(lastId + 1L, entity);
        }
        saveChanges();
        return entity;
    }

    @Override
    public T update(T entity) {

        map.put(entity.getId(), entity);
        saveChanges();
        return entity;
    }

    @Override
    public void delete(long id) {

        map.remove(id);
        saveChanges();
    }

    @Override
    public List<T> getAll() {
        return map.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }


    private void saveChanges() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        try {
            mapper.writeValue(new File(filename), getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
