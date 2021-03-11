package cn.org.rookie.mapper.sql.condition;

import cn.org.rookie.mapper.sql.SingleCondition;

public class Greater extends SingleCondition {

    @Override
    public String getTerm() {
        return "&gt;";
    }
}
