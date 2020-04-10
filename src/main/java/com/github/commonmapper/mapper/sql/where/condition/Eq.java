package com.github.commonmapper.mapper.sql.where.condition;

import com.github.commonmapper.mapper.sql.where.SingleCondition;

public class Eq extends SingleCondition {

    public Eq(String columnName) {
        super(columnName);
    }

    @Override
    public String getTerm() {
        return "=";
    }
}
