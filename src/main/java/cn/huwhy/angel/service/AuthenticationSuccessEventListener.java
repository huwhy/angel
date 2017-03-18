//package com.comblife.water.server.service;
//
//import javax.annotation.Resource;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.context.ApplicationListener;
//import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
//import org.springframework.stereotype.Component;
//
//import com.comblife.water.server.biz.manager.UserManager;
//import com.comblife.water.server.biz.manager.UserManager;
//
//@Component
//public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
//    private final Logger logger = LogManager.getLogger(getClass());
//
//    @Resource
//    private UserManager userManager;
//
//    @Override
//    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
//        String username = authenticationSuccessEvent.getAuthentication().getName();
//        userManager.updateLastLoginTime(username);
//
//        logger.info("AuthenticationSuccessEventListener - onApplicationEvent, user login, username = {}", username);
//    }
//}
