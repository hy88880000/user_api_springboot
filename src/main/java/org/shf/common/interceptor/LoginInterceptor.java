package org.shf.common.interceptor;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.shf.model.po.User;
import org.shf.utils.JwtUtil;
import org.shf.utils.UserRedis;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Base64;



public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从请求中拿出token码
        String token = request.getParameter("token");
        //判断token有没有值
        if (StringUtils.isEmpty(token)){
            throw new Exception("信息不完整");
        }
        //通过Base64解密
        byte[] decode = Base64.getDecoder().decode(token.getBytes());
        //将字节数组转成string字符串
        String s = new String(decode);
        //分隔成一个字符串数组
        String[] split = s.split(",");
        //判断信息是否被篡改
        if (split.length != 2){
            throw new Exception("请求信息格式不正确");
        }
        //账号
        String account = split[0];
        //sign码
        String sign = split[1];
        //通过JWT解密
        String unsign = JwtUtil.unsign(sign, String.class);
        //判断解密出来的值是否为空,如果为空设置响应信息让用户重新登录
        if (StringUtils.isEmpty(unsign)){
            //设置以什么数据类型返回给客户端
            response.setContentType("Application/json");
            //设置返回信息的编码格式
            response.setCharacterEncoding("UTF-8");
            //写入流
            PrintWriter writer = response.getWriter();
            //设置返回的状态码
            writer.write(JSONObject.toJSONString("code",1000));
            return false;
        }
        //取出redis中的sign码
        String redisSign = UserRedis.get("user_" + account);
        //判断是不是最新的密钥
        if (redisSign.equals(sign) != true){
            throw new Exception("登录过期，请重新登录！");
        }
        //前面验证通过后重新设置redis数据的过期时间
        UserRedis.set("user_"+account,sign,60*60);
        //登录完成后将用户信息放到request中,方便以后使用
        User user = new User();
        user.setAccount(account);
        request.setAttribute("loginUser",user);
        return true;
    }
}
