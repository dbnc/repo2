package com.bugs.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@Component
@WebFilter(urlPatterns = "/user/*" ,filterName = "ToLoginFilter")
public class ToLoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String userEmail = (String)session.getAttribute("userEmail");
        String path = request.getContextPath();
        if(userEmail!=null){
            filterChain.doFilter(request,response);
        }else {
            response.sendRedirect(path+"/");
        }

    }

    @Override
    public void destroy() {

    }
}
