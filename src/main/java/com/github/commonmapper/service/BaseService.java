package com.github.commonmapper.service;

import com.github.commonmapper.mapper.sql.Wrapper;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

public interface BaseService<T, E> extends ApplicationContextAware {

    boolean insert(T entity);

    boolean deleteById(E id);

    boolean deleteByIds(E[] ids);

    boolean updateById(T entity);

    T selectById(E id);

    T selectOne(Wrapper wrapper);

    List<T> select();

    List<T> select(T entity);

    List<T> select(Wrapper wrapper);
}
