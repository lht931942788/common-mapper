package cn.org.rookie.mapper.entity;

import cn.org.rookie.mapper.annotation.Association;
import cn.org.rookie.mapper.annotation.JoinColumn;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoinColumnInfo {

    private final String tableName;
    private final String fieldName;
    private final String columnName;
    private final List<AssociationInfo> associations = new ArrayList<>();

    public JoinColumnInfo(Field field) {
        JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
        tableName = joinColumn.tableName();
        fieldName = field.getName();
        if ("".equals(joinColumn.column())) {
            columnName = humpToUnderline(fieldName);
        } else {
            columnName = joinColumn.column();
        }
        Association[] associations = joinColumn.relations();
        for (Association association : associations) {
            this.associations.add(new AssociationInfo(association));
        }
    }

    public String getTableName() {
        return tableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getColumnName() {
        return columnName;
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

    public List<AssociationInfo> getAssociations() {
        return associations;
    }
}
