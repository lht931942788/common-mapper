package cn.org.rookie.mapper.entity;


import cn.org.rookie.mapper.annotation.Column;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrimaryInfo {
    private String columnName;
    private String fieldName;

    public PrimaryInfo(Field field) {
        Column column = field.getAnnotation(Column.class);
        fieldName = field.getName();
        if (column == null || "".equals(column.value())) {
            columnName = humpToUnderline(field.getName());
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

    private static String humpToUnderline(String str) {
        Matcher matcher = Pattern.compile("[A-Z]").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static String underlineToHump(String str) {
        Matcher matcher = Pattern.compile("_(\\w)").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
