package cn.org.rookie.mapper.sql.where;

import cn.org.rookie.mapper.sql.where.condition.*;

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
                    wrapper.eq("\"" + name + "\"", o);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return wrapper;
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

    public void setOrder(List<String> orders) {
        this.orders = orders;
    }

    private Wrapper addCondition(Condition condition, boolean isSharp) {
        if (!isSharp) {
            condition.setPrefix("$");
        }
        condition.setIsAnd(isAnd);
        conditions.add(condition);
        return this;
    }

    private void putParam(String columnName, Object param) {
        params.put(columnName, param);
    }

    private Wrapper addCondition(Condition condition) {
        condition.setIsAnd(isAnd);
        conditions.add(condition);
        return this;
    }

    public Wrapper eq(String columnName, Object param) {
        putParam(columnName, param);
        return addCondition(new Eq(columnName), true);
    }

    public Wrapper notEq(String columnName, Object param) {
        params.put(columnName, param);
        return addCondition(new NotEq(columnName), true);
    }

    public Wrapper lt(String columnName, Object param) {
        params.put(columnName, param);
        return addCondition(new Lt(columnName), true);
    }

    public Wrapper gt(String columnName, Object param) {
        params.put(columnName, param);
        return addCondition(new Gt(columnName), true);
    }

    public Wrapper like(String columnName, Object param) {
        params.put(columnName, param);
        return addCondition(new Like(columnName), false);
    }

    public Wrapper in(String columnName, Object[] param) {
        params.put(columnName, param);
        return addCondition(new In(columnName), true);
    }

    public Wrapper isNull(String columnName) {
        return addCondition(new IsNull(columnName));
    }

    public Wrapper isNotNull(String columnName) {
        return addCondition(new IsNotNull(columnName));
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
}

