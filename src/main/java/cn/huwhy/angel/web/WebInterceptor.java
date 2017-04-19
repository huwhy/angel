package cn.huwhy.angel.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.huwhy.angel.cache.CacheUtils;

public class WebInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private CacheUtils cacheUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        if (session.getAttribute("catList") == null) {
            session.setAttribute("catList", cacheUtils.getArticleCats());
        }

        return super.preHandle(request, response, handler);
    }


}