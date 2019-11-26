package cn.org.rookie.mapper.where.condition;

import cn.org.rookie.mapper.where.SingleCondition;

public class Like extends SingleCondition {

    public Like(String columnName, boolean isAnd) {
        super(columnName);
    }

    @Override
    public String getTerm() {
        return "like";
    }

}
