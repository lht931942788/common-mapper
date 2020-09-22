package cn.org.rookie.mapper.annotation;

import cn.org.rookie.mapper.BaseMapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JoinTable {

    Class<? extends BaseMapper<?, ?>> mappedClass();
}
