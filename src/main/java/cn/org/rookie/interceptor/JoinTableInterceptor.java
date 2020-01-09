package cn.org.rookie.interceptor;

import cn.org.rookie.mapper.BaseMapper;
import cn.org.rookie.mapper.annotation.JoinTable;
import cn.org.rookie.utils.JoinTableFlag;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Properties;

@Intercepts({@Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class JoinTableInterceptor implements Interceptor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @SuppressWarnings("unchecked")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        if (JoinTableFlag.getFlag()) {
            Method method = invocation.getMethod(); //代理方法
            Class<?> returnType = method.getReturnType();
            if (returnType.isAssignableFrom(List.class)) {
                Object[] args = invocation.getArgs(); //方法参数
                MappedStatement mappedStatement = (MappedStatement) args[0];
                String id = mappedStatement.getId();
                String resource = id.substring(0, id.lastIndexOf("."));
                Class<?> aClass = Class.forName(resource);
                Class type = (Class) ((ParameterizedType) (aClass.getGenericInterfaces()[0])).getActualTypeArguments()[0];
                Field[] fields = type.getDeclaredFields();
                List list = (List) result;
                for (Field field : fields) {
                    JoinTable joinTable = field.getAnnotation(JoinTable.class);
                    if (joinTable != null) {
                        field.setAccessible(true);
                        BaseMapper baseMapper = (BaseMapper) applicationContext.getBean(joinTable.mappedClass());
                        list.forEach(o -> {
                            try {
                                field.set(o, baseMapper.select());
                                //TODO 加查询条件
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
