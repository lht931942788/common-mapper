package com.github.commonmapper.mapper.sql.where.condition;

import com.github.commonmapper.mapper.sql.where.SingleCondition;

public class NotEq extends SingleCondition {
    public NotEq(String columnName) {
        super(columnName);
    }

    @Override
    public String getTerm() {
        return "!=";
    }
}
