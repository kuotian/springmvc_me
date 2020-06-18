package com.hdu.springmvc.handlermapping;

import com.hdu.springmvc.handler.AddUserHandler;
import com.hdu.springmvc.handler.QueryUserHandler;
import com.hdu.springmvc.handlermapping.iface.HandlerMapping;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class SimpleUrlHandlerMapping implements HandlerMapping, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;
    // uri和处理器对象的映射集合
    private Map<String, Object> mappings = new HashMap<>();

    /**
     * BeanNameUrlHandlerMapping的初始化方法
     */
    public void init() {
        mappings.put("/addUser3", new AddUserHandler());
        mappings.put("/queryUser3", new QueryUserHandler());
    }

    @Override
    public Object getHandler(HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI();
        return mappings.get(uri);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }
}
