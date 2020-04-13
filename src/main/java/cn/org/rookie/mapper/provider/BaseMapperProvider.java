package cn.org.rookie.mapper.provider;

import cn.org.rookie.mapper.sql.SQLBuilder;
import cn.org.rookie.mapper.sql.Wrapper;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public class BaseMapperProvider {

    /**
     * 用来缓存已经生成的SQL
     */

    private static final Map<String, SQLBuilder> CONTEXT = new ConcurrentHashMap<>();

    public String insert(ProviderContext context) {
        return getSqlBuilder(context).insert().build();
    }

    public String deleteByPrimary(Object o, ProviderContext context) {
        return getSqlBuilder(context).select().byPrimary().build();
    }

    public String delete(Wrapper wrapper, ProviderContext context) {
        return getSqlBuilder(context).delete().where(wrapper).build();
    }

    public String updateByPrimary(Object o, ProviderContext context) {
        return getSqlBuilder(context).update().byPrimary().build();
    }

    public String update(Object entity, Wrapper wrapper, ProviderContext context) {
        return getSqlBuilder(context).update().where(wrapper).build();
    }

    public String select(Wrapper wrapper, ProviderContext context) {
        return getSqlBuilder(context).select().where(wrapper).build();
    }

    public String selectByPrimary(Object o, ProviderContext context) {
        return getSqlBuilder(context).select().byPrimary().build();
    }

    private Class getEntityType(ProviderContext context) {
        return (Class) ((ParameterizedType) (context.getMapperType().getGenericInterfaces()[0])).getActualTypeArguments()[0];
    }

    private SQLBuilder getSqlBuilder(ProviderContext context) {
        String name = getEntityType(context).getName();
        SQLBuilder sqlBuilder = CONTEXT.get(name);
        if (sqlBuilder == null) {
            sqlBuilder = new SQLBuilder(getEntityType(context));
            CONTEXT.put(name, sqlBuilder);
        }
        return sqlBuilder.reset();
    }
}
