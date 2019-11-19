package cn.org.rookie.service;

import cn.org.rookie.mapper.sql.Wrapper;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

public interface BaseService<T, E> extends ApplicationContextAware {

    boolean insert(T entity);

    boolean deleteById(E id);

    boolean deleteByIds(E[] ids);

    boolean updateById(T entity);

    T selectById(E id);

    List<T> select();

    List<T> selectList(T entity);

    List<T> selectList(Wrapper wrapper);
}
