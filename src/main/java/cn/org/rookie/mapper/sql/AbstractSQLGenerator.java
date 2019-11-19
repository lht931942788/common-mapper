package cn.org.rookie.mapper.sql;

import cn.org.rookie.mapper.table.ColumnInfo;
import cn.org.rookie.mapper.table.JoinColumnInfo;
import cn.org.rookie.mapper.table.TableInfo;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public abstract class AbstractSQLGenerator implements SQLGenerator {

    @Override
    public SQL insert(TableInfo tableInfo) {
        String tableName = tableInfo.getTableName();
        SQL sql = new SQL().INSERT_INTO(tableName);
        sql.INTO_COLUMNS(tableInfo.getColumnNames()).INTO_VALUES(tableInfo.getFieldNames());
        return sql;
    }

    @Override
    public SQL delete(TableInfo tableInfo) {
        String tableName = tableInfo.getTableName();
        return new SQL().DELETE_FROM(tableName);
    }

    @Override
    public SQL update(TableInfo tableInfo) {
        String tableName = tableInfo.getTableName();
        SQL sql = new SQL().UPDATE(tableName);
        List<ColumnInfo> columns = tableInfo.getColumns();
        for (ColumnInfo columnInfo : columns) {
            if (!columnInfo.isPrimary()) {
                sql.SET(columnInfo.getColumnName() + " = #{entity." + columnInfo.getFieldName() + "}");
            }
        }
        return sql;
    }

    @Override
    public SQL select(TableInfo tableInfo, boolean order) {
        String tableName = tableInfo.getTableName();
        SQL sql = new SQL();
        List<ColumnInfo> columns = tableInfo.getColumns();
        for (ColumnInfo columnInfo : columns) {
            sql.SELECT(tableName + "." + columnInfo.getColumnName() + " \"" + columnInfo.getFieldName() + "\"");
            if (order && columnInfo.isOrder()) {
                sql.ORDER_BY(tableName + "." + columnInfo.getColumnName() + " " + columnInfo.getOrderType());
            }
        }
        sql.FROM(tableName);
        List<JoinColumnInfo> joinTables = tableInfo.getJoinTables();
        for (JoinColumnInfo joinColumnInfo : joinTables) {
            String joinTable = joinColumnInfo.getTableName();
            sql.SELECT(joinTable + "." + joinColumnInfo.getColumnName() + " \"" + joinColumnInfo.getFieldName() + "\"");
            String[] wheres = joinColumnInfo.getWheres();
            //sql.WHERE(wheres);
            sql.LEFT_OUTER_JOIN(joinTable + " on " + String.join(" and ", wheres));
        }
        return sql;
    }

    @Override
    public SQL count(TableInfo tableInfo, Wrapper wrapper) {
        return new SQL().SELECT("count(*)").FROM("(" + wrapper.where(select(tableInfo, false)) + ") temp");
    }

    @Override
    public abstract SQL selectByPage(TableInfo tableInfo);
}
