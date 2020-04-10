package com.github.commonmapper.mapper.sql.where.condition;

import com.github.commonmapper.mapper.sql.where.SingleCondition;

public class Lt extends SingleCondition {
    public Lt(String columnName) {
        super(columnName);
    }

    @Override
    public String getTerm() {
        return "&lt;";
    }
}
