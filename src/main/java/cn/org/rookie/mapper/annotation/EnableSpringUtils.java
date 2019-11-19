package cn.org.rookie.mapper.annotation;


import cn.org.rookie.tools.SpringUtils;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SpringUtils.class})
public @interface EnableSpringUtils {
}
