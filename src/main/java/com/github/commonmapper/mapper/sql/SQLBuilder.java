package com.github.commonmapper.mapper.sql;

import com.github.commonmapper.mapper.sql.where.Condition;
import cn.org.rookie.mapper.table.*;
import com.github.commonmapper.mapper.table.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class SQLBuilder {

    private final TableInfo tableInfo;
    private final Class<?> type;
    private SQL sql;

    public SQLBuilder(Class<?> type) {
        this.tableInfo = new TableInfo(type);
        this.type = type;
    }

    public SQLBuilder reset() {
        sql = new SQL();
        return this;
    }

    public SQLBuilder insert() {
        List<ColumnInfo> columns = tableInfo.getColumns();
        sql.INSERT_INTO(getTableName());
        StringBuilder value = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            ColumnInfo columnInfo = columns.get(i);
            String columnName = columnInfo.getColumnName();
            String fieldName = columnInfo.getFieldName();
            String separator = ",";
            if (i + 1 == columns.size()) {
                separator = "";
            }
            value.append(ifScript(fieldName, columnName, separator));
            values.append(ifScript(fieldName, sharp(fieldName), separator));
        }
        sql.VALUES(getPrimaryColumnName(), sharp(getPrimaryFieldName()));
        sql.VALUES(value.toString(), values.toString());
        return this;
    }

    public SQLBuilder delete() {
        sql.DELETE_FROM(getTableName());
        return this;
    }

    public SQLBuilder update() {
        List<ColumnInfo> columns = tableInfo.getColumns();
        sql.UPDATE(getTableName());
        StringBuilder set = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            ColumnInfo columnInfo = columns.get(i);
            String fieldName = columnInfo.getFieldName();
            String columnName = columnInfo.getColumnName();
            String separator = ",";
            if (i + 1 == columns.size()) {
                separator = "";
            }
            set.append(ifScript(fieldName, set(columnName, fieldName), separator));
        }
        sql.SET(set.toString());
        return this;
    }

    public SQLBuilder select() {
        String tableName = getTableName();
        sql.SELECT(select(tableName, getPrimaryColumnName(), getPrimaryFieldName()));
        for (ColumnInfo columnInfo : tableInfo.getColumns()) {
            sql.SELECT(select(tableName, columnInfo.getColumnName(), columnInfo.getFieldName()));
        }
        sql.FROM(tableName);
        for (JoinColumnInfo joinColumnInfo : tableInfo.getJoinColumns()) {
            String joinTableName = joinColumnInfo.getTableName();
            sql.FROM(joinTableName);
            sql.SELECT(select(joinTableName, joinColumnInfo.getColumnName(), joinColumnInfo.getFieldName()));
            List<AssociationInfo> associations = joinColumnInfo.getAssociations();
            for (AssociationInfo associationInfo : associations) {
                sql.WHERE(condition(tableName, associationInfo.getTarget(), joinTableName, associationInfo.getAssociation()));
            }
        }
        return this;
    }

    public SQLBuilder byPrimary() {
        sql.WHERE(condition(getTableName(), getPrimaryColumnName(), "id"));
        return this;
    }

    public SQLBuilder where(Wrapper wrapper) {
        if (wrapper != null) {
            List<Condition> conditions = wrapper.getConditions();
            for (Condition condition : conditions) {
                if (condition.isAnd()) {
                    sql.AND();
                }
                if (!condition.isAnd()) {
                    sql.OR();
                }
                sql.WHERE(condition.render());
            }
        }
        return this;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public Class<?> getType() {
        return type;
    }

    private String getTableName() {
        return tableInfo.getTableName();
    }

    private PrimaryInfo getPrimaryInfo() {
        return tableInfo.getPrimaryInfo();
    }

    private String getPrimaryColumnName() {
        return getPrimaryInfo().getColumnName();
    }

    private String getPrimaryFieldName() {
        return getPrimaryInfo().getFieldName();
    }

    private String select(String tableName, String columnName, String fieldName) {
        return String.format("%s.%s \"%s\"", tableName, columnName, fieldName);
    }

    private String sharp(String fieldName) {
        return String.format("#{%s}", fieldName);
    }

    private String set(String columnName, String fieldName) {
        return String.format("%s = #{%s}", columnName, fieldName);
    }

    private String ifScript(String fieldName, String content, String separator) {
        return String.format("<if test=\"%s != null and %s != ''\">%s%s</if>", fieldName, fieldName, content, separator);
    }

    private String condition(String tableName, String columnName, String fieldName) {
        return String.format("%s.%s = #{%s}", tableName, columnName, fieldName);
    }

    private String condition(String tableName, String left, String joinTableName, String right) {
        return String.format("%s.%s = %s.%s", tableName, left, joinTableName, right);
    }

    public String build() {
        return "<script>" + sql.toString() + "</script>";
    }


}
