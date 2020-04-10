package com.github.commonmapper.mapper.sql.where.condition;

import com.github.commonmapper.mapper.sql.where.SingleCondition;

public class Gt extends SingleCondition {
    public Gt(String columnName) {
        super(columnName);
    }

    @Override
    public String getTerm() {
        return "&gt;";
    }
}
