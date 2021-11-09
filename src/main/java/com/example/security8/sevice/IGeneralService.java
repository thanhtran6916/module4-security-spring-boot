package com.example.security8.sevice;

import java.util.Optional;

public interface IGeneralService<T> {

    Iterable<T> findAll();

    T save(T t);

    void delete(Long id);

    Optional<T> findById(Long id);

}
