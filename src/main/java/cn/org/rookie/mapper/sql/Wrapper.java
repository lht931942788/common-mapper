package cn.org.rookie.mapper.sql;

import cn.org.rookie.mapper.sql.condition.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wrapper {

    private final Map<String, Object> params = new HashMap<>();
    private final List<Condition> conditions = new ArrayList<>();
    private List<String> orders = new ArrayList<>();
    private boolean isAnd = true;

    private Wrapper() {
    }

    public static Wrapper build() {
        return new Wrapper();
    }

    public static Wrapper build(Object entity) {
        return build(entity, build());
    }

    public static Wrapper build(Object entity, Wrapper wrapper) {
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            try {
                Object o = field.get(entity);
                if (o != null && !"".equals(o)) {
                    wrapper.equal("\"" + name + "\"", o);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return wrapper;
    }


    private Wrapper add(String columnName, Object param, Condition condition, boolean isSharp) {
        if (!isSharp) {
            condition.setPrefix("$");
        }
        params.put(columnName, param);
        condition.setColumnName(columnName);
        condition.setIsAnd(isAnd);
        conditions.add(condition);
        return this;
    }

    private Wrapper add(String columnName, Condition condition) {
        condition.setColumnName(columnName);
        condition.setIsAnd(isAnd);
        conditions.add(condition);
        return this;
    }


    public Wrapper equal(String columnName, Object param) {
        return add(columnName, param, new Equal(), true);
    }

    public Wrapper notEqual(String columnName, Object param) {
        return add(columnName, param, new NotEqual(), true);
    }

    public Wrapper less(String columnName, Object param) {
        return add(columnName, param, new Less(), true);
    }

    public Wrapper greater(String columnName, Object param) {
        return add(columnName, param, new Greater(), true);
    }

    public Wrapper like(String columnName, Object param) {
        return add(columnName, param, new Like(), false);
    }

    public Wrapper in(String columnName, Object[] param) {
        return add(columnName, param, new In(), true);
    }

    public Wrapper isNull(String columnName) {
        return add(columnName, new IsNull());
    }

    public Wrapper isNotNull(String columnName) {
        return add(columnName, new IsNotNull());
    }

    public Wrapper order(String order) {
        this.orders.add(order);
        return this;
    }

    public Wrapper and() {
        isAnd = true;
        return this;
    }

    public Wrapper or() {
        isAnd = false;
        return this;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public List<String> getOrder() {
        return orders;
    }

    public void orderBy(List<String> orders) {
        this.orders = orders;
    }
}

