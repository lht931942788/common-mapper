package cn.org.rookie.mapper.sql;

import cn.org.rookie.mapper.table.TableInfo;
import org.apache.ibatis.jdbc.SQL;

public interface SQLGenerator {

    SQL insert(TableInfo tableInfo);

    SQL delete(TableInfo tableInfo);

    SQL update(TableInfo tableInfo);

    SQL select(TableInfo tableInfo, boolean order);

    SQL count(TableInfo tableInfo, Wrapper wrapper);

    SQL selectByPage(TableInfo tableInfo);
}
