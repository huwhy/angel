//package com.comblife.water.server.security;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.List;
//
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.access.SecurityConfig;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//import org.springframework.stereotype.Service;
//
///**
// * @author huwhy
// * @date 2016/12/14
// * @Desc
// */
//@Service("myMetadataSource")
//public class MyMetadataSource implements FilterInvocationSecurityMetadataSource {
//    private List<RequestMatcher> requestMatchers = new ArrayList<>();
//
//    public MyMetadataSource() {
//        requestMatchers.add(new AntPathRequestMatcher("/assets/**"));
//        requestMatchers.add(new AntPathRequestMatcher("/login.html"));
//        requestMatchers.add(new AntPathRequestMatcher("/login_by_wx.html"));
//        requestMatchers.add(new AntPathRequestMatcher("/login_by_wx", "POST"));
//        requestMatchers.add(new AntPathRequestMatcher("/login", "POST"));
//        requestMatchers.add(new AntPathRequestMatcher("/logout"));
//    }
//
//    @Override
//    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//        // 获取当前的URL地址
//        FilterInvocation filterInvocation = (FilterInvocation) object;
//        for (RequestMatcher requestMatcher : requestMatchers) {
//            if (requestMatcher.matches(filterInvocation.getRequest())) {
//                Collection<ConfigAttribute> c = new HashSet<>();
//                SecurityConfig role = new SecurityConfig("ROLE_ANONYMOUS");
//                c.add(role);
//                return c;
//            }
//        }
//        Collection<ConfigAttribute> c = new HashSet<>();
//        SecurityConfig role = new SecurityConfig("ROLE_USER");
//        c.add(role);
//        return c; // 将privilege中的roles改为Collection<ConfigAttribute>，role需要实现ConfigAttribute接口
//    }
//
//    @Override
//    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        return null;
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return true;
//    }
//}
