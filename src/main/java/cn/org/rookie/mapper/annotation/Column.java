package cn.org.rookie.mapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

    boolean primary() default false;

    String value() default "";

    boolean order() default false;

    String orderType() default "desc";

}
