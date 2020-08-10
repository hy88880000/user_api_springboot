package org.shf.common.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KuaYuInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取访问路径，设置允许跨域的参数
        String origin = request.getHeader("Origin");
        //设置给浏览器的响应信息
        response.setHeader("Access-Control-Allow-Origin",origin);
        return true;
    }
}
