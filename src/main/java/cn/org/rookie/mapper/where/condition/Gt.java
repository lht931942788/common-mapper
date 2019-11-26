package cn.org.rookie.mapper.where.condition;

import cn.org.rookie.mapper.where.SingleCondition;

public class Gt extends SingleCondition {
    public Gt(String columnName) {
        super(columnName);
    }

    @Override
    public String getTerm() {
        return "&gt;";
    }
}
