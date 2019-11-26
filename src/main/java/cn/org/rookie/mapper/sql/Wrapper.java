package cn.org.rookie.mapper.sql;

import cn.org.rookie.mapper.where.*;
import cn.org.rookie.mapper.where.condition.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wrapper {

    private Map<String, Object> params = new HashMap<>();
    private List<Condition> conditions = new ArrayList<>();
    private List<String> orders = new ArrayList<>();
    private boolean isAnd = true;

    private Wrapper() {
    }

    public static Wrapper build() {
        return new Wrapper();
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

    private Wrapper addCondition(SingleCondition condition, boolean isSharp) {
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
        return addCondition(new Lt(columnName), false);
    }

    public Wrapper in(String columnName, Object param) {
        params.put(columnName, param);
        return addCondition(new IsNull(columnName));
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

