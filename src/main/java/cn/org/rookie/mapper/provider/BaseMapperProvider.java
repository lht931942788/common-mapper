package cn.org.rookie.mapper.provider;

import cn.org.rookie.mapper.sql.SQL;
import cn.org.rookie.mapper.sql.SQLContext;
import cn.org.rookie.mapper.sql.Wrapper;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public class BaseMapperProvider {

    public String insert(ProviderContext context) {
        return getSQL(context).insert().build();
    }

    public String deleteByPrimary(Object o, ProviderContext context) {
        return getSQL(context).delete().byPrimary().build();
    }

    public String delete(Wrapper wrapper, ProviderContext context) {
        return getSQL(context).delete().where(wrapper).build();
    }

    public String updateByPrimary(Object o, ProviderContext context) {
        return getSQL(context).update().byPrimary().build();
    }

    public String update(Object entity, Wrapper wrapper, ProviderContext context) {
        return getSQL(context).update().where(wrapper).build();
    }

    public String selectOne(Wrapper wrapper, ProviderContext context) {
        return getSQL(context).select().where(wrapper).build();
    }

    public String selectByPrimary(Object o, ProviderContext context) {
        return getSQL(context).select().byPrimary().build();
    }

    public String selectList(Wrapper wrapper, ProviderContext context) {
        return getSQL(context).select().where(wrapper).build();
    }

    private Class getEntityType(ProviderContext context) {
        return (Class) ((ParameterizedType) (context.getMapperType().getGenericInterfaces()[0])).getActualTypeArguments()[0];
    }

    private SQL getSQL(ProviderContext context) {
        String name = getEntityType(context).getName();
        SQL sql = SQLContext.get(name);
        if (sql == null) {
            sql = new SQL(getEntityType(context));
            SQLContext.put(name, sql);
        }
        return sql;
    }

}
