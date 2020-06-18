package com.hdu.springmvc.handleradapter;

import com.hdu.springmvc.annotation.ResponseBody;
import com.hdu.springmvc.handleradapter.iface.HandlerAdapter;
import com.hdu.springmvc.handlermapping.HandlerMethod;
import com.hdu.springmvc.model.ModelAndView;
import com.hdu.springmvc.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestMappingHandlerAdapter implements HandlerAdapter {
    @Override
    public ModelAndView handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1.将Object类型的handler强转为HandlerMethod类型
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 2.取出Method对象和Controller对象
        Method method = handlerMethod.getMethod();
        Object controller = handlerMethod.getHandler();
        // 3.要处理方法参数的绑定(请求URL中的参数是字符串类型，而Controller方法的参数类型是多种多样的）
        Object[] args = getParameter(request, method);
        // 4.通过反射调用对应的方法，并得到返回值(就是调用Controller类中被RequestMapping注解所对应的方法)
        Object returnValue = method.invoke(controller, args);
        // 5.处理返回值
        handleReturnValue(response, method, returnValue);
        return null;
    }

    private Object[] getParameter(HttpServletRequest request, Method method) {
        List<Object> parameterList = new ArrayList<Object>();
        // 将请求URL中的参数，获取到一个Map集合中
        Map<String, String[]> parameterMap = request.getParameterMap();

        // 获取Controller方法的参数
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            // 获取参数名称
            String name = parameter.getName();
            // 需要根据方法中的参数名称去获取parameterMap中对应key的值
            String[] stringValue = parameterMap.get(name);

            // 获取目标方法参数类型
            Class<?> type = parameter.getType();
            // 类型转换
            Object value = convertValue(stringValue, type);

            parameterList.add(value);
        }
        return parameterList.toArray();
    }

    private Object convertValue(String[] stringValue, Class<?> type) {
        if (stringValue == null || stringValue.length == 0) {
            return null;
        }
        // TODO 使用策略模式去优化
        if (type == List.class) {
            return stringValue;
        } else if (type == Integer.class) {
            return Integer.parseInt(stringValue[0]);
        } else if (type == String.class) {
            return stringValue[0];
        }
        return null;
    }

    private void handleReturnValue(HttpServletResponse response, Method method, Object returnValue) throws Exception {
        // 是否带有@ResponseBody注解
        if (method.isAnnotationPresent(ResponseBody.class)) {
            if (returnValue instanceof String) {
                response.setContentType("text/plain;charset=utf8");
                response.getWriter().write(returnValue.toString());
            } else if (returnValue instanceof Map) {
                response.setContentType("application/json;charset=utf8");
                response.getWriter().write(JsonUtils.object2Json(returnValue));
            } // ....
        } else {
            // 页面跳转处理
        }
    }

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof HandlerMethod);
    }
}
