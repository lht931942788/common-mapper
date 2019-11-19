package cn.org.rookie.mapper.sql;

import cn.org.rookie.mapper.table.TableInfo;
import org.apache.ibatis.jdbc.SQL;

public class MySQLGenerator extends AbstractSQLGenerator {
    @Override
    public SQL selectByPage(TableInfo tableInfo) {
        return super.select(tableInfo, true).LIMIT("#{page.start},#{page.pageSize}");
    }
}
