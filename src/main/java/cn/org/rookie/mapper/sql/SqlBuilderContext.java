package cn.org.rookie.mapper.sql;

import cn.org.rookie.mapper.entity.PrimaryInfo;
import cn.org.rookie.mapper.entity.TableInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SqlBuilderContext {

    private static final Map<String, SQL> CONTEXT = new ConcurrentHashMap<>();

    public static SQL getSqlBuilder(Class<?> entity) {
        String name = entity.getName();
        SQL sql = CONTEXT.get(name);
        if (sql == null) {
            sql = new SQL(entity);
            CONTEXT.put(name, sql);
        }
        return sql;
    }

    public static SQL getSqlBuilder(String name) {
        return CONTEXT.get(name);
    }

    public static TableInfo getTableInfo(String name) {
        return CONTEXT.get(name).getTableInfo();
    }

    public static PrimaryInfo getPrimaryInfo(String name) {
        return CONTEXT.get(name).getPrimaryInfo();
    }

}
