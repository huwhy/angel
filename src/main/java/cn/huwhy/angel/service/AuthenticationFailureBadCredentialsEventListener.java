//package com.comblife.water.server.service;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.context.ApplicationListener;
//import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AuthenticationFailureBadCredentialsEventListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
//    private final Logger logger = LogManager.getLogger(getClass());
//
//    @Override
//    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent authenticationFailureBadCredentialsEvent) {
//        String username = authenticationFailureBadCredentialsEvent.getAuthentication().getName();
//
//        logger.info("AuthenticationFailureBadCredentialsEventListener - onApplicationEvent, user login failed, username = {}", username);
//    }
//}
