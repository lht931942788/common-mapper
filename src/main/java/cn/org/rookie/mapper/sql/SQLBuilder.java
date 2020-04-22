package cn.org.rookie.mapper.sql;

import cn.org.rookie.mapper.sql.where.Condition;
import cn.org.rookie.mapper.sql.where.Wrapper;
import cn.org.rookie.mapper.entity.*;
import cn.org.rookie.mapper.utils.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class SQLBuilder {

    private final TableInfo tableInfo;
    private final Class<?> type;
    private final String INSERT;
    private final String DELETE;
    private final String UPDATE;
    private final String SELECT;
    private String sql;

    public SQLBuilder(Class<?> type) {
        final SQL insert = new SQL();
        final SQL delete = new SQL();
        final SQL update = new SQL();
        final SQL select = new SQL();

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

            select.SELECT(select(tableName, columnName, fieldName));
        }
        insert.VALUES(primaryColumnName, sharp(primaryFieldName));
        insert.VALUES(value.toString(), values.toString());

        update.SET(set.toString());

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
        sql += (" WHERE " + condition(getTableName(), getPrimaryColumnName(), "id"));
        return this;
    }

    public SQLBuilder where(Wrapper wrapper) {
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
                sql += StringUtils.join(",", " ORDER BY ", "", order);
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
        return "<script>" + sql + "</script>";
    }


}
