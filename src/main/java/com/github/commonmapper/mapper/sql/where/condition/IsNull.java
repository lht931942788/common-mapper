package com.github.commonmapper.mapper.sql.where.condition;

import com.github.commonmapper.mapper.sql.where.Condition;

public class IsNull extends Condition {

    public IsNull(String columnName) {
        super(columnName);
    }

    @Override
    public String render() {
        return columnName + " is null";
    }
}
