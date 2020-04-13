package cn.org.rookie.mapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置字段
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

    /**
     * 配置字段名称
     *
     * @return 字段名称
     */
    String value() default "";

    /**
     * 配置字段排序
     *
     * @return 字段排序
     */
    boolean order() default false;

    /**
     * 配置字段排序类型
     *
     * @return 段排序类型
     */
    String orderType() default "desc";

}
