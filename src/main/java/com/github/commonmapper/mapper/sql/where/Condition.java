package com.github.commonmapper.mapper.sql.where;

public abstract class Condition {
    protected String columnName;
    protected Boolean isAnd;

    public Condition(String columnName) {
        this.columnName = columnName;
    }

    public Boolean isAnd() {
        return isAnd;
    }

    public void setIsAnd(boolean isAnd) {
        this.isAnd = isAnd;
    }

    public abstract String render();


}
