package org.shf.controller;

import org.apache.commons.lang3.StringUtils;
import org.shf.model.po.Area;
import org.shf.model.po.User;
import org.shf.service.UserService;
import org.shf.utils.AliOSSUtil;
import org.shf.utils.JwtUtil;
import org.shf.utils.UploadUtil;
import org.shf.utils.UserRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("UserController")
public class UserController {
    @Autowired
    private UserService userService;

    //注册
    @RequestMapping("addUser")
    public Map addUser(User user){
        Map map = new HashMap();
        Boolean result = userService.selectUserAccount(user.getAccount());
        try {
            if (result==true){
                userService.addUser(user);
                map.put("code",200);
            }else {
                map.put("code",300);
            }
        }catch (Exception e){
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }

    //登录
    @RequestMapping("login")
    public Map login(String account,String pwd){
        Map map = new HashMap();
        try {
            if (StringUtils.isNotBlank(account)){
                Boolean result = userService.selectUserAccount(account);
                if (result == false){
                    if (StringUtils.isNotBlank(pwd)){
                        String pw = userService.selectPwdByAccount(account);
                        if (pwd.equals(pw)){
                            //对用户唯一标示通过JWT加密并设置最大过期时间
                            String sign = JwtUtil.sign(account, 60 * 60 * 1000);
                            //加密完成存放到redis中设置过期时间
                            UserRedis.set("user_"+account,sign,60*60);
                            //防止客户端篡改信息在通过Base64加密
                            String token = Base64.getEncoder().encodeToString((account + "," + sign).getBytes());
                            map.put("code",200);//登录成功
                            map.put("token",token);//将token码返回前台
                        }else {
                            map.put("code",700);//密码错误
                        }
                    }else {
                        map.put("code",600);//密码不能为空
                    }
                }else {
                    map.put("code",300);//账号不存在
                }
            }else {
                map.put("code",100);//账号不能为空
            }
        }catch (Exception e){
            map.put("code",500);//后台代码错误
            map.put("message",e.getMessage());
            e.printStackTrace();
        }

        return map;
    }

    @RequestMapping("selectUser")
    public Map selectUser(){
        Map data = userService.selectUser();

        return data;
    }

    @RequestMapping("selectArea")
    public List selectArea(){
        List<Area>list = userService.selectArea();
        return list;
    }

    @RequestMapping("deleteById")
    public Map deleteById(Integer id){
        Map map = new HashMap();
        User user = userService.selectUserById(id);
        try {
            if (StringUtils.isNotBlank(user.getImgPath())){
                AliOSSUtil.deleteFile(user.getImgPath());
            }
            userService.deleteById(id);
            map.put("code",200);
        }catch (Exception e){
            map.put("code",500);
        }



        return map;
    }


    @RequestMapping("uploadFile")
    public Map uploadFile(MultipartFile image) {
        Map map = new HashMap();
        try {
            String fileName = AliOSSUtil.uploadFile(image.getInputStream(),image.getOriginalFilename());
            if (fileName != null){
                map.put("fileName",fileName);
                map.put("message",true);
            }
        }catch (Exception e){
            map.put("message",e.getMessage());
            e.printStackTrace();
        }

        return map;
    }

}
