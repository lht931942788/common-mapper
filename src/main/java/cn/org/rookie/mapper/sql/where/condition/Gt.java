package cn.org.rookie.mapper.sql.where.condition;

import cn.org.rookie.mapper.sql.where.SingleCondition;

public class Gt extends SingleCondition {
    public Gt(String columnName) {
        super(columnName);
    }

    @Override
    public String getTerm() {
        return "&gt;";
    }
}
