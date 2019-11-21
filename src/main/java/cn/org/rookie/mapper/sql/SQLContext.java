package cn.org.rookie.mapper.sql;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SQLContext {
    private static final Map<String, SQLMaker> SQL_MAP = new ConcurrentHashMap<>();

    public static SQLMaker get(String name) {
        return SQL_MAP.get(name);
    }

    public static void put(String name, SQLMaker sql) {
        SQL_MAP.put(name, sql);
    }
}
