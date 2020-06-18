package com.hdu.springmvc.servlet;

import com.hdu.springmvc.handleradapter.iface.HandlerAdapter;
import com.hdu.springmvc.handlermapping.iface.HandlerMapping;
import com.hdu.springmvc.model.ModelAndView;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 编写前端控制器 作用：接收所有http请求、响应处理结果给浏览器，分发请求（先找到处理器，再请求处理器去处理请求）
 */
public class DispatcherServlet extends AbstractHttpServlet {

    private DefaultListableBeanFactory beanFactory;
    // 处理器映射器的策略集合
    private List<HandlerMapping> handlerMappings;
    // 处理器适配器的策略集合
    private List<HandlerAdapter> handlerAdapters;

    public void init(ServletConfig config){
        // 从web.xml中获取springmvc的配置文件路径
        String contextConfigLocation = config.getInitParameter("contextConfigLocation");
        // 初始化spring容器，需要提前一次性初始化bean实例
        initSpringContainer(contextConfigLocation);
        // 初始化策略集合
        initHandlerMappings();
        initHandlerAdapters();
    }

    private void initSpringContainer(String contextConfigLocation) {
        beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        Resource resource = new ClassPathResource(contextConfigLocation);
        reader.loadBeanDefinitions(resource);
    }
    private void initHandlerMappings() {
        // 从spring容器中，初始化所有的HandlerMapping的策略
        handlerMappings = new ArrayList<>(beanFactory.getBeansOfType(HandlerMapping.class).values());
    }
    private void initHandlerAdapters() {
        handlerAdapters = new ArrayList<>(beanFactory.getBeansOfType(HandlerAdapter.class).values());
    }


    @Override
    public void doDispatch(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 根据请求查找处理器类(HandlerMapping)
            Object handler = getHandler(request);
            // 去执行处理器类的方法(HandlerAdapter)
//            // 使用策略模式+适配器模式去优化以下代码
//            if (handler instanceof HttpRequestHandler) {
//                ((HttpRequestHandler) handler).handleRequest(request, response);
//            } else if (handler instanceof SimpleControllerHandler) {
//                ((SimpleControllerHandler) handler).handleRequest(request, response);
//            }
            if(handler == null){
                return;
            }
            // 先找处理器适配器
            HandlerAdapter ha = getHandlerAdapter(handler);
            if (ha == null) {
                return;
            }
            // 再去调用处理器适配器的统一处理请求方法
            ModelAndView mv = ha.handleRequest(handler, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getHandler(HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI();

        // 使用策略模式
        if (handlerMappings != null && handlerMappings.size() > 0) {
            for (HandlerMapping handlerMapping : handlerMappings) {
                Object handler = handlerMapping.getHandler(request);
                if (handler != null) {
                    return handler;
                }
            }
        }
        return null;
    }

    private HandlerAdapter getHandlerAdapter(Object handler) {
        // 如果HandlerAdapter1能处理handler，则返回HandlerAdapter1
        // 如果HandlerAdapter2能处理handler，则返回HandlerAdapter2
        // 如果HandlerAdapter3能处理handler，则返回HandlerAdapter3

        if (handlerAdapters != null && handlerAdapters.size() > 0) {
            for (HandlerAdapter handlerAdapter : handlerAdapters) {
                // 判断该处理器适配器是否支持该处理器
                if (handlerAdapter.supports(handler)) {
                    return handlerAdapter;
                }
            }
        }
        return null;
    }
}
