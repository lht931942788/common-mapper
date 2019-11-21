package cn.org.rookie.mapper.provider;

import cn.org.rookie.mapper.sql.SQLMaker;
import cn.org.rookie.mapper.sql.SQLContext;
import cn.org.rookie.mapper.sql.Wrapper;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.lang.reflect.ParameterizedType;

@SuppressWarnings("unused")
public class BaseMapperProvider {

    public String insert(ProviderContext context) {
        return getSQL(context).insert().build();
    }

    public String deleteByPrimary(Object o, ProviderContext context) {
        return getSQL(context).delete().byPrimary().build();
    }

    public String delete(Wrapper wrapper, ProviderContext context) {
        String sql;
        if (wrapper != null) {
            sql = getSQL(context).delete().where(wrapper.structure()).build();
        } else {
            sql = getSQL(context).delete().build();
        }
        return sql;
    }

    public String updateByPrimary(Object o, ProviderContext context) {
        return getSQL(context).update().byPrimary().build();
    }

    public String update(Object entity, Wrapper wrapper, ProviderContext context) {
        String sql;
        if (wrapper != null) {
            sql = getSQL(context).update().where(wrapper.structure()).build();
        } else {
            sql = getSQL(context).update().build();
        }
        return sql;
    }

    public String selectOne(Wrapper wrapper, ProviderContext context) {
        String sql;
        if (wrapper != null) {
            sql = getSQL(context).select().where(wrapper.structure()).build();
        } else {
            sql = getSQL(context).select().build();
        }
        return sql;
    }

    public String selectByPrimary(Object o, ProviderContext context) {
        return getSQL(context).select().byPrimary().build();
    }

    public String selectList(Wrapper wrapper, ProviderContext context) {
        String sql;
        if (wrapper != null) {
            sql = getSQL(context).select().where(wrapper.structure()).build();
        } else {
            sql = getSQL(context).select().build();
        }
        return sql;
    }

    private Class getEntityType(ProviderContext context) {
        return (Class) ((ParameterizedType) (context.getMapperType().getGenericInterfaces()[0])).getActualTypeArguments()[0];
    }

    private SQLMaker getSQL(ProviderContext context) {
        String name = getEntityType(context).getName();
        SQLMaker sql = SQLContext.get(name);
        if (sql == null) {
            sql = new SQLMaker(getEntityType(context));
            SQLContext.put(name, sql);
        }
        return sql;
    }

}
