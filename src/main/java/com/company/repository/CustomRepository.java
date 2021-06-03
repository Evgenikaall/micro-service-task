package com.company.repository;

import java.util.List;

public interface CustomRepository<T, ID> {

    <S extends T> int save(S object);

    List<T> findAll();
}
