package com.bh.sfapi.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BaseDAO<T,K> {

    void save(T t);

    void update(T t);

    void delete(K k);

    List<T> findAll();
    
}
