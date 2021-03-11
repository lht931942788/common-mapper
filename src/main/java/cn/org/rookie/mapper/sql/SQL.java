package cn.org.rookie.mapper.sql;

import cn.org.rookie.mapper.entity.*;

import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

public class SQL {

    private final TableInfo tableInfo;
    private final Class<?> type;
    private final String INSERT;
    private final String DELETE;
    private final String UPDATE;
    private final String SELECT;
    private String sql;

    public SQL(Class<?> type) {
        final org.apache.ibatis.jdbc.SQL insert = new org.apache.ibatis.jdbc.SQL();
        final org.apache.ibatis.jdbc.SQL delete = new org.apache.ibatis.jdbc.SQL();
        final org.apache.ibatis.jdbc.SQL update = new org.apache.ibatis.jdbc.SQL();
        final org.apache.ibatis.jdbc.SQL select = new org.apache.ibatis.jdbc.SQL();

        this.tableInfo = new TableInfo(type);
        this.type = type;

        StringBuilder value = new StringBuilder();
        StringBuilder values = new StringBuilder();

        StringBuilder set = new StringBuilder();

        List<ColumnInfo> columns = this.tableInfo.getColumns();
        String tableName = getTableName();
        String primaryFieldName = getPrimaryFieldName();
        String primaryColumnName = getPrimaryColumnName();

        insert.INSERT_INTO(tableName);
        delete.DELETE_FROM(tableName);
        update.UPDATE(tableName);
        select.FROM(tableName);
        select.SELECT(select(tableName, primaryColumnName, primaryFieldName));

        int size = columns.size();
        for (ColumnInfo columnInfo : columns) {
            String columnName = columnInfo.getColumnName();
            String fieldName = columnInfo.getFieldName();
            value.append(ifScript(fieldName, columnName));
            values.append(ifScript(fieldName, sharp(fieldName)));

            set.append(ifScript(fieldName, set(columnName, fieldName)));

            select.SELECT(select(tableName, columnName, fieldName));
        }
        insert.VALUES(primaryColumnName, sharp(primaryFieldName));
        insert.VALUES(trimScript(value.toString()), trimScript(values.toString()));

        update.SET(trimScript(set.toString()));

        for (JoinColumnInfo joinColumnInfo : tableInfo.getJoinColumns()) {
            String joinTableName = joinColumnInfo.getTableName();
            select.FROM(joinTableName);
            select.SELECT(select(joinTableName, joinColumnInfo.getColumnName(), joinColumnInfo.getFieldName()));
            List<AssociationInfo> associations = joinColumnInfo.getAssociations();
            for (AssociationInfo associationInfo : associations) {
                select.WHERE(condition(tableName, associationInfo.getTarget(), joinTableName, associationInfo.getAssociation()));
            }
        }

        this.INSERT = insert.toString();
        this.DELETE = delete.toString();
        this.UPDATE = update.toString();
        this.SELECT = select.toString();
    }

    private static String join(Collection<String> source) {
        StringJoiner joiner = new StringJoiner(",", " ORDER BY ", "");
        for (String s : source) {
            joiner.add(s);
        }
        return joiner.toString();
    }

    public SQL insert() {
        sql = INSERT;
        return this;
    }

    public SQL delete() {
        sql = DELETE;
        return this;
    }

    public SQL update() {
        sql = UPDATE;
        return this;
    }

    public SQL select() {
        sql = SELECT;
        return this;
    }

    public SQL byPrimary() {
        sql += (" WHERE " + condition(getTableName(), getPrimaryColumnName(), "id"));
        return this;
    }

    public Class<?> getType() {
        return type;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public String getTableName() {
        return tableInfo.getTableName();
    }

    public PrimaryInfo getPrimaryInfo() {
        return tableInfo.getPrimaryInfo();
    }

    public String getPrimaryColumnName() {
        return getPrimaryInfo().getColumnName();
    }

    public String getPrimaryFieldName() {
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

    private String setScript(String content) {
        return String.format("<set>%s</set>", content);
    }

    private String trimScript(String content) {
        return String.format("<trim prefix=\"\" suffixOverrides=\"%s\">%s</trim>", ",", content);
    }

    private String ifScript(String fieldName, String content) {
        return String.format("<if test=\"%s != null\">%s</if>", fieldName, content);
    }

    private String condition(String tableName, String columnName, String fieldName) {
        return String.format("%s.%s = #{%s}", tableName, columnName, fieldName);
    }

    private String condition(String tableName, String left, String joinTableName, String right) {
        return String.format("%s.%s = %s.%s", tableName, left, joinTableName, right);
    }

    public String build() {
        return "<script>" + sql + "</script>";
    }

    public SQL where(Wrapper wrapper) {
        StringBuilder where = new StringBuilder();
        if (wrapper != null) {
            List<Condition> conditions = wrapper.getConditions();
            for (int i = 0; i < conditions.size(); i++) {
                Condition condition = conditions.get(i);
                if (i != 0) {
                    if (condition.isAnd()) {
                        where.append(" AND ");
                    } else {
                        where.append(" OR ");
                    }
                }
                where.append(condition.render());
            }
            List<String> order = wrapper.getOrder();
            if (where.length() > 0) {
                if (sql.contains("WHERE")) {
                    sql += (" AND " + where);
                } else {
                    sql += (" WHERE " + where);
                }
            }

            if (order.size() > 0) {
                sql += join(order);
            }
        }
        return this;
    }

}
