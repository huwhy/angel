package cn.huwhy.angel.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = {"/", "/index.html"})
    public String home(HttpServletRequest request) {
        request.getSession().setMaxInactiveInterval(0);
        return "index";
    }

}
