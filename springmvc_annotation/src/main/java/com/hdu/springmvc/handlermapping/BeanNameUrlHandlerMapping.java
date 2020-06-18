package com.hdu.springmvc.handlermapping;

import com.hdu.springmvc.handlermapping.iface.HandlerMapping;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通过bean标签的name属性和bean标签对应的实例建立映射关系
 */
public class BeanNameUrlHandlerMapping implements HandlerMapping, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;
    // uri和处理器对象的映射集合
    private Map<String, Object> mappings = new HashMap<>();

    /**
     * BeanNameUrlHandlerMapping的初始化方法
     */
    public void init(){
        // 从spring容器中（BeanFactory）获取所有的handler的BeanDefinition对象
        // Spring 容器中所有 JavaBean 的名称
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            // 判断bean的name属性，是否是以/开头
            if( beanDefinitionName.startsWith("/") ) {
                // 取出bean的name属性，并且获取bean实例
                Object bean = beanFactory.getBean(beanDefinitionName);
                // 建立映射关系
                mappings.put(beanDefinitionName, bean);
            }
        }
    }

    @Override
    public Object getHandler(HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI();
        return mappings.get(uri);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory)beanFactory;
    }
}
