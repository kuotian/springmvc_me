package com.hdu.springmvc.handlermapping;

import java.lang.reflect.Method;
// Method对象和Controller对象封装到HandlerMethod对象中
public class HandlerMethod {
    private Object handler; // Controller

    private Method method; // Method

    public Object getHandler() {
        return handler;
    }

    public void setHandler(Object handler) {
        this.handler = handler;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public HandlerMethod(Object handler, Method method) {
        super();
        this.handler = handler;
        this.method = method;
    }
}
