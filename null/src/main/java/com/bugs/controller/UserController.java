package com.bugs.controller;

import com.bugs.entity.Movies;
import com.bugs.entity.UserInfo;
import com.bugs.mapper.IndexMovieMapper;
import com.bugs.mapper.MoviesMapper;
import com.bugs.mapper.UserInfoMapper;
import com.bugs.mapper.UserMapper;
import com.bugs.service.IndexService;
import com.bugs.service.LoginService;
import com.bugs.service.MovieListService;
import com.bugs.service.UserInfoService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sun.org.apache.regexp.internal.RE;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.sun.org.apache.xpath.internal.operations.Variable;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.print.Pageable;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    private IndexService indexService;

    /**
     * 首页
     *
     * @param request
     * @param response
     */
    @RequestMapping("/user/index")
    public ModelAndView indexList(Model model, HttpServletRequest request, HttpServletResponse response) {
        String str = request.getParameter("page");
        if (str == null) {
            str = "1";
        }
        System.out.println(str + "strpage");
        int page = Integer.parseInt(str);
        System.out.println(page);
        Integer size = indexService.sendAll();
        List<Movies> movies = indexService.sendListByPage(page);
        model.addAttribute("movieList", movies).addAttribute("page", page).addAttribute("size", size / 5);
        return new ModelAndView("/user/index", "model", model);
    }

    @Autowired
    private MovieListService movieListService;

    /**
     * 电影列表
     *
     * @param request
     * @param response
     */
    @RequestMapping("/user/listMovie")
    public ModelAndView movieList(Model model, HttpServletRequest request, HttpServletResponse response) {
        String str = request.getParameter("page");
        System.out.println("现在所在的page=="+str);
        if (str == null) {
            str = "1";
        }
        int page = Integer.parseInt(str);
        System.out.println(page + "...........");
        //总页数
        Integer size = movieListService.sendAll();
        List<Movies> movies = movieListService.sendListByPage(page);
        model.addAttribute("movieList", movies).addAttribute("size", size).addAttribute("page", page);
        return new ModelAndView("/user/listMovie", "model", model);
    }

    /**
     * 增加电影
     */
    @RequestMapping("/user/insertMovie")
    public void returnIndex(HttpServletRequest request,HttpServletResponse response) {
        String movie_name = request.getParameter("movie_name");
        String movie_url = request.getParameter("movie_url");
        String movie_img = request.getParameter("movie_img");
        String movie_info = request.getParameter("movie_info");
        String id = UUID.randomUUID().toString();
        Movies movie = new Movies(id, movie_img, movie_name, movie_url, movie_info);
        Map<String, Object> map = movieListService.insertMovie(movie);
        System.out.println( map.get("code"));
//        return new ModelAndView("/user/index");
        try {
            response.sendRedirect(request.getContextPath()+"/user/index");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/user/deleteSession")
    public void deleteSession(HttpServletRequest request,HttpServletResponse response){
        request.getSession().invalidate();
        try {
            response.sendRedirect(request.getContextPath()+"/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 播放的同时保存到history列表
     */
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/user/display")//播放的同时
    public ModelAndView display(HttpServletRequest request, Model model) {
        String url = request.getParameter("url");
        String title = request.getParameter("movieName");
        String page = request.getParameter("page");
        System.out.println(page+"............page");
        System.out.println("正在播放的网址为；" + url);
        if (url != null) {
            String email = (String) request.getSession().getAttribute("userEmail");
            System.out.println("url======" + url);
            //保存到history列表
            userInfoService.insertUrlToHistory(email, url);
            model.addAttribute("url", url).addAttribute("title", title).addAttribute("page", page);
            return new ModelAndView("/user/display", "model", model);
        } else {
            return null;
        }
    }

    /**
     * 收藏
     */
    @RequestMapping("/user/insertCollection")
    public void insertCollection(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        if (page == null) {
            page = "1";
        }
        System.out.println("当前要收藏的页面时" + page);
        String url = request.getParameter("url");
        String email = (String) request.getSession().getAttribute("userEmail");
        userInfoService.insertUrlToCollection(email, url);
        String path = request.getContextPath();
        try {
            response.sendRedirect(path + "/user/listMovie?page=" + page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/user/sendCollection")
    public ModelAndView sendController(Model model, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("userEmail");
        String str = request.getParameter("page");
        if (str == null) {
            str = "1";
        }
        int page = Integer.parseInt(str);
        //每页展示的条数最多为24条
        List<Movies> movies = userInfoService.sendCollection(email, page);
//        System.out.println("这是collection的movies=="+movies);

        model.addAttribute("movies", movies).addAttribute("page", page);
        return new ModelAndView("/user/collection", "model", model);
    }

    @RequestMapping("/user/deleteCollection")
//    public ModelAndView deleteController(HttpServletRequest request){
    public void deleteController(HttpServletRequest request, HttpServletResponse response) {
        String email = (String) request.getSession().getAttribute("userEmail");
        String page = request.getParameter("page");
        if (page == null) {
            page = "1";
        }
        System.out.println("-----要删除的内容所在的page-----------" + page);
        String url = request.getParameter("url");
        userInfoService.deleteUrlToCollection(email, url);
        try {
            response.sendRedirect(request.getContextPath() + "/user/sendCollection?page=" + page);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        sendController()
//        return new ModelAndView("/user/sendCollection?page=" + page);
    }

    @RequestMapping("/user/sendHistory")
    public ModelAndView sendHistory(Model model, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("userEmail");
        String page = request.getParameter("page");
        if (page == null) {
            page = "1";
        }
        int pg = Integer.parseInt(page);
        List<Movies> movies = userInfoService.sendHistory(email, pg);
        model.addAttribute("movies", movies).addAttribute("page", pg);
        return new ModelAndView("/user/history", "model", model);
    }

    @RequestMapping("/user/deleteHistory")
    public void deleteHistory(HttpServletRequest request, HttpServletResponse response) {
        String email = (String) request.getSession().getAttribute("userEmail");
        String page = request.getParameter("page");
        String url = request.getParameter("url");
        userInfoService.deleteUrlToHistory(email, url);
        try {
            response.sendRedirect(request.getContextPath() + "/user/sendHistory?page=" + page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private LoginService loginService;

    @RequestMapping("/user/Personal")
    public ModelAndView open() {
        return new ModelAndView("/user/Personal");
    }

    @RequestMapping("/user/updatePassword")
    @ResponseBody
    public String updatePassword(HttpServletRequest request, HttpServletResponse response) {
        String oldPassowrd = request.getParameter("oldPassowrd");
        String newPassword = request.getParameter("newPassword");
        String email = (String) request.getSession().getAttribute("userEmail");
        System.out.println(oldPassowrd+"..................");
        Map<String, Object> map = loginService.updatePassword(email, oldPassowrd, newPassword);
        String code = (String) map.get("code");
        if (code == null) {
            return "修改成功<a href='/user/index'>点此返回首页</a>";
        } else {
            return code;
        }

    }

    @RequestMapping("/user/AddMovies")
    public void insertMovie(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        Integer permissions = (Integer) session.getAttribute("permissions");
        if(permissions>=5){
            try {
                response.sendRedirect("/user/addMovies.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                response.sendRedirect("/user/listMovie");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @RequestMapping("/user/seach")
    public ModelAndView seachMovies(Model model , HttpServletRequest request){
        String seachName = request.getParameter("seachName");
        List<Movies> movies = movieListService.sendByName(seachName);
        model.addAttribute("movies", movies);
        return new ModelAndView("/user/seach","model",model);
    }
}
