package com.github.commonmapper.mapper.sql.where.condition;

import com.github.commonmapper.mapper.sql.where.SingleCondition;

public class Like extends SingleCondition {

    public Like(String columnName) {
        super(columnName);
    }

    @Override
    public String getTerm() {
        return "like";
    }

}
