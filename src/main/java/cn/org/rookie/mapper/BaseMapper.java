package cn.org.rookie.mapper;

import cn.org.rookie.mapper.provider.BaseMapperProvider;
import cn.org.rookie.mapper.sql.where.Wrapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BaseMapper<E, ID> {

    @InsertProvider(type = BaseMapperProvider.class, method = "insert")
    int insert(E entity);

    @DeleteProvider(type = BaseMapperProvider.class, method = "deleteByPrimary")
    int deleteByPrimary(ID id);

    @DeleteProvider(type = BaseMapperProvider.class, method = "delete")
    int delete(@Param("wrapper") Wrapper wrapper);

    @UpdateProvider(type = BaseMapperProvider.class, method = "updateByPrimary")
    int updateByPrimary(E entity);

    @DeleteProvider(type = BaseMapperProvider.class, method = "update")
    int update(@Param("entity") E entity, @Param("wrapper") Wrapper wrapper);

    @SelectProvider(type = BaseMapperProvider.class, method = "selectByPrimary")
    E select(@Param("id") ID id);

    @SelectProvider(type = BaseMapperProvider.class, method = "select")
    E selectOne(@Param("wrapper") Wrapper wrapper);

    @SelectProvider(type = BaseMapperProvider.class, method = "select")
    List<E> select();

    @SelectProvider(type = BaseMapperProvider.class, method = "select")
    List<E> selectList(@Param("wrapper") Wrapper wrapper);

}


