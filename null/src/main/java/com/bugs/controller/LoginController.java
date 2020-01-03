package com.bugs.controller;

import com.bugs.Util.ValidateImageCodeUtils;
import com.bugs.service.LoginService;
import com.bugs.service.RegisteredService;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping("/")
    public ModelAndView login(Model model){
        return new ModelAndView("/login.html","model",model);
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String encode = request.getParameter("encode");
        String imgCode = (String) session.getAttribute("imgCode");
        if(imgCode.equals(encode)) {
            Map<String, Object> map = loginService.selectByEmail(email, password);
            String path = request.getContextPath();
            if (map.get("code") == null) {
                session.setAttribute("permissions", map.get("permissions"));
                session.setAttribute("userEmail", email);
                try {
                    response.sendRedirect(path + "/user/index");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return (String) map.get("code");
        }
        return "验证码错误<a href='/'>点此重新登陆</a>";
    }
    @Autowired
    private RegisteredService registeredService;

    @RequestMapping("/registered")
    @ResponseBody
    public String registered(HttpServletRequest request,HttpServletResponse response){
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String email = request.getParameter("email");
        Map<String, Object> code = registeredService.registered(email, password1, password2);
        String encode = (String)code.get("encode");
        System.out.println("    注册成功标志encode   =======     "+encode);
        if(encode==null){
//            登录
            HttpSession session = request.getSession();
            session.setAttribute("userEmail", email);
            try {
                response.sendRedirect(request.getContextPath()+"/user/index");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return encode;
    }

    @RequestMapping("/code")
    public void setImg(HttpServletRequest request,HttpServletResponse response){
        /*
         * 绘制验证码
         * 1. 绘制验证码中的字符
         * 2. 绘制图片（把绘制好的字符交给这个图片）
         * 3. 图片通过流写到页面
         */
        String img = ValidateImageCodeUtils.getSecurityCode();
        HttpSession session = request.getSession();
        System.out.println("验证码："+img);
        session.setAttribute("imgCode",img);
        BufferedImage createImg = ValidateImageCodeUtils.createImage(img);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(createImg, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
