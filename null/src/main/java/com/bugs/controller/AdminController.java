package com.bugs.controller;

import com.bugs.entity.Movies;
import com.bugs.entity.User;
import com.bugs.service.AdminService;
import com.bugs.service.LoginService;
import com.bugs.service.MovieListService;
import com.bugs.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("user/admin")
    public void openIndex(Model model, HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
//        System.out.println(email + "      " + password);
        Map<String, Object> map = adminService.sendAdmin(email, password);
        System.out.println(map);
        Object code = map.get("code");
        if (code == null) {
            try {
                response.sendRedirect(request.getContextPath() + "/user/admin/sendUsers");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.sendRedirect(request.getContextPath() + "/user/index");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Autowired
    private UserService userService;
    @Autowired
    private MovieListService movieListService;

    @RequestMapping("/user/admin/updateUsers")
    public ModelAndView updateUser(Model model, HttpServletRequest request) {
        String page = request.getParameter("userPage");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String permissions = request.getParameter("permissions");
        System.out.println("        " + page + username + email + password + permissions);
        model.addAttribute("email", email)
                .addAttribute("page", page)
                .addAttribute("username", username)
                .addAttribute("password", password)
                .addAttribute("permissions", permissions);
        return new ModelAndView("/user/admin/updateUser", "model", model);
    }

    @RequestMapping("/user/admin/updateUsers1")
//    public ModelAndView returnList(Model model,HttpServletRequest request,HttpServletResponse response){
    public void returnList(HttpServletRequest request, HttpServletResponse response) {
        String userPage = request.getParameter("userPage");
        if (userPage == null) {
            userPage = "1";
        }
        String oldEmail = request.getParameter("oldEmail");
        int page = Integer.parseInt(userPage);
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Integer permissions = Integer.parseInt(request.getParameter("permissions"));
        User user = new User("", email, username, password, permissions);
        System.out.println(user);
        userService.updateUser(oldEmail,user);
        System.out.println("修改完成");
        try {
            response.sendRedirect(request.getContextPath() + "/user/admin/sendUsers?userPage=" + page);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return new ModelAndView("/user/admin/sendUsers?userPage="+page);

    }

    @RequestMapping("/user/admin/deleteUsers")
//    public ModelAndView deleteUser(HttpServletRequest request,HttpServletResponse response){
    public void deleteUsers(HttpServletRequest request,HttpServletResponse response) {
        String email = request.getParameter("email");
        String userPage = request.getParameter("userPage");
        userService.deleteUser(email);
        try {
            response.sendRedirect(request.getContextPath()+"/user/admin/sendUsers?userPage="+userPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/user/admin/sendUsers")
    public ModelAndView sendUsers(Model model, HttpServletRequest request) {
        String userPage = request.getParameter("userPage");
        if (userPage == null) {
            userPage = "1";
        }
//        System.out.println(userPage);
        int page = Integer.parseInt(userPage);
        List<User> users = userService.sendByPage(page);
//        System.out.println("List<User>"+users);
        model.addAttribute("users", users).addAttribute("userPage", page);
        return new ModelAndView("/user/admin/sendUser", "model", model);
    }

    @RequestMapping("/user/admin/sendResources")
    public ModelAndView sendResources(Model model, HttpServletRequest request) {
        String resourcePage = request.getParameter("resourcePage");
        if (resourcePage == null) {
            resourcePage = "1";
        }
        int resourcePg = Integer.parseInt(resourcePage);
        List<Movies> movies = movieListService.sendListByPage(resourcePg);
        model.addAttribute("movies", movies).addAttribute("resourcePage", resourcePg);
        return new ModelAndView("/user/admin/sendResources", "model", model);
    }

    @RequestMapping("/user/admin/deleteResources")
//    public ModelAndView deleteUser(HttpServletRequest request,HttpServletResponse response){
    public void deleteResources(HttpServletRequest request,HttpServletResponse response) {
        String movieUrl = request.getParameter("movieUrl");
        String resourcePage = request.getParameter("resourcePage");
        System.out.println(resourcePage+"-------------");
        movieListService.deleteResource(movieUrl);
        try {
            response.sendRedirect(request.getContextPath()+"/user/admin/sendResources?resourcePage="+resourcePage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/user/admin/sendAdmins")
    public ModelAndView sendAdmin(Model model, HttpServletRequest request) {
        String adminPage = request.getParameter("adminPage");
        if (adminPage == null) {
            adminPage = "1";
        }
        int adminPg = Integer.parseInt(adminPage);
        List<User> admins = userService.sendAdminByPage(adminPg);
        model.addAttribute("admins", admins)
                .addAttribute("adminPage", adminPg);
        return new ModelAndView("/user/admin/sendAdmin", "model", model);
    }

    @RequestMapping("/user/admin/updateAdmin")
    public ModelAndView updateAdmin(Model model, HttpServletRequest request) {
        String email = request.getParameter("email");
        String adminPage = request.getParameter("adminPage");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String permissions = request.getParameter("permissions");
        System.out.println("        "+email + adminPage + username  + password + permissions);
        model.addAttribute("adminPage", adminPage)
                .addAttribute("email",email)
                .addAttribute("username", username)
                .addAttribute("password", password)
                .addAttribute("permissions", permissions);
        return new ModelAndView("/user/admin/updateAdmin", "model", model);
    }

    @RequestMapping("/user/admin/updateAdmin1")
//    public ModelAndView returnList(Model model,HttpServletRequest request,HttpServletResponse response){
    public void returnAdminList(HttpServletRequest request, HttpServletResponse response) {
        String adminPage = request.getParameter("adminPage");
        if (adminPage == null) {
            adminPage = "1";
        }
        String email = request.getParameter("email");
        int page = Integer.parseInt(adminPage);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Integer permissions = Integer.parseInt(request.getParameter("permissions"));
        User user = new User("", email, username, password, permissions);
        System.out.println(user);
        userService.updateAdmin(user);
        System.out.println("修改完成");
        try {
            response.sendRedirect(request.getContextPath() + "/user/admin/sendAdmins?adminPage=" + page);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return new ModelAndView("/user/admin/sendUsers?userPage="+page);

    }

    @RequestMapping("/user/admin/deleteAdmin")
//    public ModelAndView deleteUser(HttpServletRequest request,HttpServletResponse response){
    public void deleteAdmin(HttpServletRequest request,HttpServletResponse response) {
        String email = request.getParameter("email");
        String adminPage = request.getParameter("adminPage");
        userService.deleteUser(email);
        try {
            response.sendRedirect(request.getContextPath()+"/user/admin/sendAdmins?adminPage="+adminPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/user/admin/insertAdmin")
    public ModelAndView insertAdmin(Model model,HttpServletRequest request){
        String adminPage = request.getParameter("adminPage");
        model.addAttribute("adminPage", adminPage);
        return new ModelAndView("/user/admin/insertAdmin", "model", model);
    }

    @RequestMapping("/user/admin/insertAdmin1")
    public void insertAdm(HttpServletRequest request, HttpServletResponse response){
        String adminPage = request.getParameter("adminPage");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int permissions = Integer.parseInt(request.getParameter("permissions"));
        String id = UUID.randomUUID().toString();
        User user = new User(id, email, username, password, permissions);
        Object code = adminService.insertAdmin(user).get("code");
        if(code==null) {
            try {
                response.sendRedirect("/user/admin/sendAdmins?adminPage=" + adminPage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                response.sendRedirect("/404.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
