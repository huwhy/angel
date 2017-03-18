//package com.comblife.water.server.security;
//
//import java.util.Collection;
//
//import org.springframework.security.access.AccessDecisionManager;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.authentication.InsufficientAuthenticationException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Component;
//
///**
// * @author huwhy
// * @date 2016/12/14
// * @Desc
// */
//@Component("myAccessDecisionManager")
//public class AccessDecisionManagerImpl implements AccessDecisionManager {
//    @Override
//    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) throws AccessDeniedException, InsufficientAuthenticationException {
//        if (null == attributes)
//            return;
//        for (ConfigAttribute attribute : attributes) {
//            String needRole = attribute.getAttribute();
//            if ("ROLE_ANONYMOUS".equals(needRole)) {
//                return;
//            }
//            // authority为用户所被赋予的权限, needRole 为访问相应的资源应该具有的权限。
//            for (GrantedAuthority grantedAuthority : authentication
//                    .getAuthorities()) {
//                if (needRole.equals(grantedAuthority.getAuthority()))
//                    return;
//            }
//        }
//        throw new AccessDeniedException("权限不足!");
//    }
//
//    @Override
//    public boolean supports(ConfigAttribute attribute) {
//        return true;
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return true;
//    }
//}
