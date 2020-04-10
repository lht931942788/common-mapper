package com.github.commonmapper.mapper.table;

import com.github.commonmapper.mapper.annotation.Column;
import com.github.commonmapper.utils.StringUtils;

import java.lang.reflect.Field;

public class ColumnInfo {

    private String fieldName;
    private String columnName;
    private Boolean isOrder = false;
    private String orderType = "aes";

    public ColumnInfo(Field field) {
        Column column = field.getAnnotation(Column.class);
        fieldName = field.getName();
        if (column == null || "".equals(column.value())) {
            columnName = StringUtils.camelCaseToUnderscore(field.getName());
        } else {
            columnName = column.value();
            isOrder = column.order();
            orderType = column.orderType();
        }
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getColumnName() {
        return columnName;
    }

    public boolean isOrder() {
        return isOrder;
    }

    public String getOrderType() {
        return orderType;
    }

}
