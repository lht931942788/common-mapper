package cn.org.rookie.mapper.sql.where.condition;

import cn.org.rookie.mapper.sql.where.SingleCondition;

public class Eq extends SingleCondition {

    public Eq(String columnName) {
        super(columnName);
    }

    @Override
    public String getTerm() {
        return "=";
    }
}
