package cn.org.rookie.mapper.entity;


import cn.org.rookie.mapper.annotation.Column;
import cn.org.rookie.mapper.utils.StringUtils;

import java.lang.reflect.Field;

public class PrimaryInfo {
    private String columnName;
    private String fieldName;

    public PrimaryInfo(Field field) {
        Column column = field.getAnnotation(Column.class);
        fieldName = field.getName();
        if (column == null || "".equals(column.value())) {
            columnName = StringUtils.camelCaseToUnderscore(field.getName());
        } else {
            columnName = column.value();
        }
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
