package cn.org.rookie.service.impl;

import cn.org.rookie.mapper.BaseMapper;
import cn.org.rookie.mapper.sql.Wrapper;
import cn.org.rookie.service.BaseService;
import cn.org.rookie.utils.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class BaseServiceImpl<M extends BaseMapper<T, E>, T, E> implements BaseService<T, E> {

    private M mapper;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    protected M getMapper() {
        if (mapper == null) {
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                mapper = applicationContext.getBean((Class<M>) ((ParameterizedType) type).getActualTypeArguments()[0]);
            }
        }
        return mapper;
    }

    @Override
    public boolean insert(T entity) {
        return getMapper().insert(entity) > 0;
    }

    @Override
    public boolean deleteById(E id) {
        return getMapper().deleteByPrimary(id) > 0;
    }

    @Override
    public boolean deleteByIds(E[] ids) {
        return getMapper().delete(Wrapper.build().in("id", StringUtils.join("','", "'", "'", (String[]) ids))) > 0;
    }

    @Override
    public boolean updateById(T entity) {
        return getMapper().updateByPrimary(entity) > 0;
    }

    @Override
    public T selectById(E id) {
        return getMapper().selectByPrimary(id);
    }

    @Override
    public T selectOne(Wrapper wrapper) {
        return getMapper().selectOne(wrapper);
    }

    @Override
    public List<T> select() {
        return getMapper().select();
    }

    @Override
    public List<T> select(T entity) {
        Wrapper wrapper = Wrapper.build();
        return getMapper().selectList(wrapper);
    }

    @Override
    public List<T> select(Wrapper wrapper) {
        return getMapper().selectList(wrapper);
    }

    private Wrapper entityToWrapper(T entity, Wrapper wrapper) {
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            try {
                Object o = field.get(entity);
                if (o != null && !"".equals(o)) {
                    wrapper.eq("\"" + name + "\"", o);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return wrapper;
    }
}
