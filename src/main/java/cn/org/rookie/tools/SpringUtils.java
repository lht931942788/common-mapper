package cn.org.rookie.tools;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    private static DefaultListableBeanFactory beanFactory;

    public static boolean addBean(String name, Class c) {
        try {
            beanFactory.registerBeanDefinition(name, BeanDefinitionBuilder.genericBeanDefinition(c).getRawBeanDefinition());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> c) {
        return applicationContext.getBean(c);
    }

    public static <T> T getBean(String name, Class<T> c) {
        return applicationContext.getBean(name, c);
    }

    public static boolean removeBean(String name) {
        try {
            beanFactory.removeBeanDefinition(name);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
        beanFactory = (DefaultListableBeanFactory) ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
    }

}
