package notate;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProfilerMethodTimeBeanPostProcessor implements BeanPostProcessor {
    private Map<String,Class> beansMap = new HashMap();

    public ProfilerMethodTimeBeanPostProcessor() {

    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().isAnnotationPresent(Timed.class)){
            beansMap.put(beanName, bean.getClass());
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = beansMap.get(beanName);
        if(beanClass!=null){
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    long before = System.nanoTime();
                    Object returnValue = method.invoke(bean, args);
                    long after = System.nanoTime();
                    System.out.println("Method time: "+ (after-before));
                    return returnValue;
                }
            });
        }
        return bean;
    }
}
