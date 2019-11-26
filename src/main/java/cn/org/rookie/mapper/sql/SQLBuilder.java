package cn.org.rookie.mapper.sql;

import cn.org.rookie.mapper.table.AssociationInfo;
import cn.org.rookie.mapper.table.ColumnInfo;
import cn.org.rookie.mapper.table.JoinColumnInfo;
import cn.org.rookie.mapper.table.TableInfo;
import cn.org.rookie.mapper.where.Condition;
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
        sql.INSERT_INTO(tableInfo.getTableName());
        for (ColumnInfo columnInfo : columns) {
            sql.VALUES(columnInfo.getColumnName(), "#{" + columnInfo.getFieldName() + "}");
        }
        return this;
    }

    public SQLBuilder delete() {
        sql = new SQL().DELETE_FROM(tableInfo.getTableName());
        return this;
    }

    public SQLBuilder update() {
        List<ColumnInfo> columns = tableInfo.getColumns();
        sql = new SQL();
        sql.UPDATE(tableInfo.getTableName());
        for (ColumnInfo columnInfo : columns) {
            sql.SET(columnInfo.getColumnName() + " = #{" + columnInfo.getFieldName() + "}");
        }
        return this;
    }

    public SQLBuilder select() {
        sql = new SQL();
        String tableName = tableInfo.getTableName();
        for (ColumnInfo columnInfo : tableInfo.getColumns()) {
            sql.SELECT(tableName + "." + columnInfo.getColumnName() + " \"" + columnInfo.getFieldName() + "\"");
        }
        sql.FROM(tableName);
        for (JoinColumnInfo joinColumnInfo : tableInfo.getJoinColumns()) {
            sql.FROM(joinColumnInfo.getTableName());
            sql.SELECT(joinColumnInfo.getTableName() + "." + joinColumnInfo.getColumnName() + " \"" + joinColumnInfo.getFieldName() + "\"");
            List<AssociationInfo> associations = joinColumnInfo.getAssociations();
            for (AssociationInfo associationInfo : associations) {
                sql.WHERE(tableName + "." + associationInfo.getTarget() + " = " + joinColumnInfo.getTableName() + "." + associationInfo.getAssociation());
            }
        }
        return this;
    }

    public SQLBuilder byPrimary() {
        sql.WHERE(tableInfo.getTableName() + "." + tableInfo.getPrimaryInfo().getColumnName() + " = #{id}");
        return this;
    }

    public SQLBuilder where(Wrapper wrapper) {
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
        return this;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public Class<?> getType() {
        return type;
    }

    public String build() {
        return sql.toString();
    }
}
