package com.hdu.springmvc.handlermapping;

import com.hdu.springmvc.annotation.Controller;
import com.hdu.springmvc.annotation.RequestMapping;
import com.hdu.springmvc.handlermapping.iface.HandlerMapping;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对于注解方式的处理器建立映射关系
 */
public class RequestMappingHandlerMapping implements HandlerMapping, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;
    // uri和处理器对象的映射集合
    private Map<String, Object> mappings = new HashMap<>();

    public void init() {
        try {
            // 1.要获取所有的BeanDefinition，获取Class对象
            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
            for (String beanDefinitionName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
                String beanClassName = beanDefinition.getBeanClassName();
                Class<?> clazz = Class.forName(beanClassName);
                // 2. 查找带有Controller注解的类
                if(isHandler(clazz)){
                    Method[] methods = clazz.getDeclaredMethods();
                    for (Method method : methods) {
                        // 3.获取该Controller类中所有带@RequestMapping注解的方法
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            // 4.取出@RequestMapping注解中的value值（请求URL）。
                            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                            String url = requestMapping.value();
                            // 5.获取@RequestMapping注解对应的方法的Method对象
                            // 6.根据BeanDefinition中的beanName从ioc容器中获取Controller对象
                            Object controller = beanFactory.getBean(beanDefinitionName);
                            // 7.讲Method对象和Controller对象封装到HandlerMethod对象中
                            HandlerMethod handlerMethod = new HandlerMethod(controller, method);
                            // 8.建立映射关系
                            mappings.put(url, handlerMethod);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean isHandler(Class<?> clazz) {
        return (clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(RequestMapping.class));
    }

    @Override
    public Object getHandler(HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI();
        return mappings.get(uri);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory =  (DefaultListableBeanFactory) beanFactory;
    }
}
