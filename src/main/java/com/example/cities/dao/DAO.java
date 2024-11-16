package com.example.cities.dao;

import java.util.List;
import java.util.Optional;


public interface DAO<E> {

    List<E> findAll();
    Optional<E> findById(Integer id);
    E save(E entity);
    E update(E entity);
    void deleteById(Integer id);
}
