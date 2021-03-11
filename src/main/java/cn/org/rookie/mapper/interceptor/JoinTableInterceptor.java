/*
package cn.org.rookie.mapper.interceptor;

import cn.org.rookie.jeesdp.mapper.BaseMapper;
import cn.org.rookie.jeesdp.mapper.annotation.Association;
import cn.org.rookie.jeesdp.mapper.annotation.JoinTable;
import cn.org.rookie.jeesdp.mapper.sql.where.Wrapper;
import cn.org.rookie.jeesdp.mapper.utils.JoinTableFlag;
import cn.org.rookie.mapper.BaseMapper;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
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

@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class JoinTableInterceptor implements Interceptor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    */
/**
 * 注解配置分步查询
 *
 * @param invocation mybatis
 * @return 返回分步查询的结果集
 *//*


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
                Class<?> type = (Class<?>) ((ParameterizedType) (Class.forName(resource).getGenericInterfaces()[0])).getActualTypeArguments()[0];
                Field[] fields = type.getDeclaredFields();
                List<?> list = (List<?>) result;
                for (Field field : fields) {
                    JoinTable joinTable = field.getAnnotation(JoinTable.class);
                    if (joinTable != null) {
                        field.setAccessible(true);
                        BaseMapper<?, ?> baseMapper = applicationContext.getBean(joinTable.mappedClass());
                        list.forEach(row -> {
                            try {
                                Wrapper wrapper = Wrapper.build();
                                Association[] associations = joinTable.joinColumn().relations();
                                for (Association association : associations) {
                                    wrapper.eq(association.association(), field.get(association.target()));
                                }
                                if (joinTable.isCollection()) {
                                    field.set(row, baseMapper.selectList(wrapper));
                                } else {
                                    field.set(row, baseMapper.selectOne(wrapper));
                                }
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
*/
