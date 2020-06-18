package com.hdu.springmvc.handler.iface;

import com.hdu.springmvc.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义处理器的编写规范
 * 这种规范要求返回一个返回值ModelAndView对象
 */
public interface SimpleControllerHandler {
    ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
