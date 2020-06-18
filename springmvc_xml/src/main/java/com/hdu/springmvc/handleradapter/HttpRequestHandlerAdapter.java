package com.hdu.springmvc.handleradapter;

import com.hdu.springmvc.handler.iface.HttpRequestHandler;
import com.hdu.springmvc.handleradapter.iface.HandlderAdapter;
import com.hdu.springmvc.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpRequestHandlerAdapter implements HandlderAdapter {
    @Override
    public ModelAndView handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response) {
        try {
            ((HttpRequestHandler) handler).handleRequest(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof HttpRequestHandler);
    }
}
