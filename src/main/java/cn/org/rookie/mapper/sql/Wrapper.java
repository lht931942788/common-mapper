package cn.org.rookie.mapper.sql;

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

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public List<String> getOrder() {
        return orders;
    }

    public void setOrder(List<String> orders) {
        this.orders = orders;
    }

    private Wrapper addCondition(String columnName, Object param, String sql) {
        addCondition(columnName, param, sql, true);
        return this;
    }

    private Wrapper addCondition(String columnName, Object param, String sql, boolean isSharp) {
        Condition condition = new Condition(columnName, sql, isAnd);
        conditions.add(condition);
        params.put(columnName, param);
        if (!isSharp) {
            condition.setPrefix("$");
        }
        return this;
    }

    public Wrapper eq(String columnName, Object param) {
        return addCondition(columnName, param, " = %s");
    }

    public Wrapper notEq(String columnName, Object param) {
        return addCondition(columnName, param, " != %s");
    }

    public Wrapper lt(String columnName, Object param) {
        return addCondition(columnName, param, " &lt; %s");
    }

    public Wrapper gt(String columnName, Object param) {
        return addCondition(columnName, param, " &gt; %s");
    }

    public Wrapper like(String columnName, Object param) {
        return addCondition(columnName, param, " like '%%s%'", false);
    }

    public Wrapper leftLike(String columnName, Object param) {
        return addCondition(columnName, param, " like '%%s'", false);
    }

    public Wrapper rightLike(String columnName, Object param) {
        return addCondition(columnName, param, " like '%s%'", false);
    }

    public Wrapper in(String columnName, Object param) {
        return addCondition(columnName, param, " in (%s)", false);
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

