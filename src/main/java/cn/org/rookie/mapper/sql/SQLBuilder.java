package cn.org.rookie.mapper.sql;

import cn.org.rookie.mapper.sql.where.Condition;
import cn.org.rookie.mapper.table.*;
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

    public SQLBuilder insert() {
        List<ColumnInfo> columns = tableInfo.getColumns();
        sql = new SQL();
        sql.INSERT_INTO(getTableName());
        StringBuilder value = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            ColumnInfo columnInfo = columns.get(i);
            String columnName = columnInfo.getColumnName();
            String fieldName = columnInfo.getFieldName();
            if (i + 1 == columns.size()) {
                value.append(ifScript(fieldName, columnName, ""));
                values.append(ifScript(fieldName, sharp(fieldName), ""));
            } else {
                value.append(ifScript(fieldName, columnName, ","));
                values.append(ifScript(fieldName, sharp(fieldName), ","));
            }
        }
        sql.VALUES(getPrimaryColumnName(), sharp(getPrimaryFieldName()));
        sql.VALUES(value.toString(), values.toString());
        return this;
    }

    public SQLBuilder delete() {
        sql = new SQL().DELETE_FROM(getTableName());
        return this;
    }

    public SQLBuilder update() {
        List<ColumnInfo> columns = tableInfo.getColumns();
        sql = new SQL();
        sql.UPDATE(getTableName());
        StringBuilder set = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            ColumnInfo columnInfo = columns.get(i);
            String fieldName = columnInfo.getFieldName();
            String columnName = columnInfo.getColumnName();
            if (i + 1 == columns.size()) {
                set.append(ifScript(fieldName, set(columnName, fieldName), ""));
            } else {
                set.append(ifScript(fieldName, set(columnName, fieldName), ","));
            }
        }
        sql.SET(set.toString());
        return this;
    }

    public SQLBuilder select() {
        sql = new SQL();
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
                sql.WHERE(tableName + "." + associationInfo.getTarget() + " = " + joinTableName + "." + associationInfo.getAssociation());
            }
        }
        return this;
    }

    public SQLBuilder byPrimary() {
        sql.WHERE(getTableName() + "." + getPrimaryColumnName() + " = #{id}");
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

    public String build() {
        return "<script>" + sql.toString() + "</script>";
    }

    private String ifScript(String fieldName, String content, String split) {
        return "<if test=\"" + fieldName + " != null and " + fieldName + " != ''\">" + content + split + "</if>";
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

    private String sharp(String content) {
        return String.format("#{%s}", content);
    }

    private String select(String tableName, String columnName, String fieldName) {
        return String.format("%s.%s \"%s\"", tableName, columnName, fieldName);
    }

    private String set(String columnName, String fieldName) {
        return String.format("%s = #{%s}", columnName, fieldName);
    }

}
