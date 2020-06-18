package com.hdu.springmvc.handleradapter.iface;

import com.hdu.springmvc.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DispatcherServlet类中希望见到的统一的目标接口
 */
public interface HandlerAdapter {
    /**
     * 处理请求
     * @param handler 处理器类
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    ModelAndView handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception;


    /**
     * 就是判断该适配器是否适合该处理器，方便策略模式使用时进行调用
     * @param handler
     * @return
     */
    boolean supports(Object handler);
}
