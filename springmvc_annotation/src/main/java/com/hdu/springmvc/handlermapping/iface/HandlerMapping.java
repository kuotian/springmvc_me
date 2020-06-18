package com.hdu.springmvc.handlermapping.iface;

import javax.servlet.http.HttpServletRequest;

/**
 * 定义处理器映射器的编写规范
 */
public interface HandlerMapping {
    /**
     * 根据请求查找处理器
     * @param request
     * @return
     * @throws Exception
     */
    Object getHandler(HttpServletRequest request) throws Exception;
}
