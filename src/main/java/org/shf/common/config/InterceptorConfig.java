package org.shf.common.config;

import org.shf.common.interceptor.KuaYuInterceptor;
import org.shf.common.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//拦截器的配置文件类,配置拦截那些，不拦那些
//声明这是一个配置文件类
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    //解决带后缀的请求，忽略后缀
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(true)	//设置是否是后缀模式匹配,即:/test.*
                .setUseTrailingSlashMatch(true);	//设置是否自动后缀路径模式匹配,即：/test/
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册跨域拦截器类
        InterceptorRegistration kuayuInter = registry.addInterceptor(new KuaYuInterceptor());
        //拦截所有
        kuayuInter.addPathPatterns("/**");

        //注册登录拦截器
        InterceptorRegistration loginInter = registry.addInterceptor(new LoginInterceptor());
        loginInter.addPathPatterns("/UserController/selectUser.do");
    }
}
