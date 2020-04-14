package cn.org.rookie.mapper.sql;

import cn.org.rookie.mapper.sql.where.Condition;
import cn.org.rookie.mapper.sql.where.Wrapper;
import cn.org.rookie.mapper.table.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class SQLBuilder {

    private final TableInfo tableInfo;
    private final Class<?> type;
    private final SQL INSERT = new SQL();
    private final SQL DELETE = new SQL();
    private final SQL UPDATE = new SQL();
    private final SQL SELECT = new SQL();
    private SQL sql;

    public SQLBuilder(Class<?> type) {
        this.tableInfo = new TableInfo(type);
        this.type = type;

        StringBuilder value = new StringBuilder();
        StringBuilder values = new StringBuilder();

        StringBuilder set = new StringBuilder();

        List<ColumnInfo> columns = this.tableInfo.getColumns();
        String tableName = getTableName();
        String primaryFieldName = getPrimaryFieldName();
        String primaryColumnName = getPrimaryColumnName();

        INSERT.INSERT_INTO(tableName);
        DELETE.DELETE_FROM(tableName);
        UPDATE.UPDATE(tableName);
        SELECT.FROM(tableName);
        SELECT.SELECT(select(tableName, primaryColumnName, primaryFieldName));

        for (int i = 0; i < columns.size(); i++) {
            ColumnInfo columnInfo = columns.get(i);
            String columnName = columnInfo.getColumnName();
            String fieldName = columnInfo.getFieldName();
            String separator = ",";
            if (i == 0) {
                separator = "";
            }

            value.append(ifScript(fieldName, columnName, separator));
            values.append(ifScript(fieldName, sharp(fieldName), separator));

            set.append(ifScript(fieldName, set(columnName, fieldName), separator));

            SELECT.SELECT(select(tableName, columnName, fieldName));
        }
        INSERT.VALUES(primaryColumnName, sharp(primaryFieldName));
        INSERT.VALUES(value.toString(), values.toString());

        UPDATE.SET(set.toString());

        for (JoinColumnInfo joinColumnInfo : tableInfo.getJoinColumns()) {
            String joinTableName = joinColumnInfo.getTableName();
            SELECT.FROM(joinTableName);
            SELECT.SELECT(select(joinTableName, joinColumnInfo.getColumnName(), joinColumnInfo.getFieldName()));
            List<AssociationInfo> associations = joinColumnInfo.getAssociations();
            for (AssociationInfo associationInfo : associations) {
                SELECT.WHERE(condition(tableName, associationInfo.getTarget(), joinTableName, associationInfo.getAssociation()));
            }
        }
    }

    public SQLBuilder insert() {
        sql = INSERT;
        return this;
    }

    public SQLBuilder delete() {
        sql = DELETE;
        return this;
    }

    public SQLBuilder update() {
        sql = UPDATE;
        return this;
    }

    public SQLBuilder select() {
        sql = SELECT;
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
            if (wrapper.getOrder().size() > 0) {
                sql.ORDER_BY(wrapper.getOrder().toArray(new String[0]));
            }
        }
        return this;
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
        return String.format("<if test=\"%s != null and %s != ''\">%s%s</if>", fieldName, fieldName, separator, content);
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
