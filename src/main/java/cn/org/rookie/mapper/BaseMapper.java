package cn.org.rookie.mapper;

import cn.org.rookie.mapper.provider.BaseMapperProvider;
import cn.org.rookie.mapper.sql.Wrapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BaseMapper<T, E> {

    @InsertProvider(type = BaseMapperProvider.class, method = "insert")
    int insert(T entity);

    @DeleteProvider(type = BaseMapperProvider.class, method = "deleteByPrimary")
    int deleteByPrimary(E id);

    @DeleteProvider(type = BaseMapperProvider.class, method = "delete")
    int delete(@Param("wrapper") Wrapper wrapper);

    @UpdateProvider(type = BaseMapperProvider.class, method = "updateByPrimary")
    int updateByPrimary(@Param("entity") T entity);

    @DeleteProvider(type = BaseMapperProvider.class, method = "update")
    int update(@Param("entity") T entity, @Param("wrapper") Wrapper wrapper);

    @SelectProvider(type = BaseMapperProvider.class, method = "selectByPrimary")
    T selectByPrimary(E id);

    @SelectProvider(type = BaseMapperProvider.class, method = "selectOne")
    T selectOne(@Param("wrapper") Wrapper wrapper);

    @SelectProvider(type = BaseMapperProvider.class, method = "selectList")
    List<T> select();

    @SelectProvider(type = BaseMapperProvider.class, method = "selectList")
    List<T> selectList(@Param("wrapper") Wrapper wrapper);

}


