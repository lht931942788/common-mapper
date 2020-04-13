package cn.org.rookie.mapper.sql.where.condition;

import cn.org.rookie.mapper.sql.where.SingleCondition;

public class Like extends SingleCondition {

    public Like(String columnName) {
        super(columnName);
    }

    @Override
    public String getTerm() {
        return "LIKE";
    }

}
