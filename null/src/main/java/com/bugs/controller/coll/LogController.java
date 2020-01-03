package com.bugs.controller.coll;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogController {
//        @RequestMapping("/")
    public ModelAndView login(Model model){
//    public String Login(Model model) {
        model.addAttribute("test", "这是测试");
//        return "/Test.html";
        return new ModelAndView("/Test.html", "model", model);
    }
}
