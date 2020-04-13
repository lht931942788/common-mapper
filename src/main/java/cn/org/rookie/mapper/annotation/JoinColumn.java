package cn.org.rookie.mapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置关联表
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JoinColumn {

    /**
     * 配置要关联的表
     *
     * @return 表名
     */

    String tableName();

    /**
     * 配置字段名称
     *
     * @return 字段名称
     */

    String column() default "";

    /**
     * 配置两表之间的关系
     *
     * @return 两表之间的关系
     */
    Association[] relations();
}
