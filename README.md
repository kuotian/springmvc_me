# springmvc_me
手写spring mvc

### springmvc_xml

localhost\addUser 直接编写Servlet处理

localhost\addUser2 --->DispatcherServlet --->BeanNameUrlHandlerMapping--->HttpRequestHandlerAdapter---> HttpRequestHandler 的 AddUserHandler

localhost\addUser3 --->DispatcherServlet --->SimpleUrlHandlerMapping---> AddUserHandler

### springmvc_annotation(增加对annotation的支持)

localhost\addUser4 --->DispatcherServlet --->RequestMappingHandlerMapping---> RequestMappingHandlerAdapter--->UserController