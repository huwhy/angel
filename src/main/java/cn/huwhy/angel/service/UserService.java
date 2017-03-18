//package com.comblife.water.server.service;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import javax.annotation.Resource;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.comblife.water.server.biz.manager.AuthorityManager;
//import com.comblife.water.server.biz.manager.UserManager;
//import com.comblife.water.server.biz.model.User;
//import com.comblife.water.server.dao.po.AuthorityDo;
//import com.comblife.water.server.dao.po.UserDo;
//
//@Service
//public class UserService implements UserDetailsService {
//    @Resource
//    private UserManager      userManager;
//    @Resource
//    private AuthorityManager authorityManager;
//    @Value("${user.rolePrefix:ROLE_}")
//    private String           rolePrefix;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserDo userDo = userManager.findByName(username);
//        if (null == userDo)
//            throw new UsernameNotFoundException("");
//
//        Set<GrantedAuthority> authoritySet = authorityManager.findByUserId(userDo.getId()).stream()
//                .map(authorityDo -> new SimpleGrantedAuthority(rolePrefix + authorityDo.getAuthority())).collect(Collectors.toSet());
//
//        return new User(username, userDo.getPassword(), userDo.getOpenId(), userDo.getEnabled(), true, true, true, authoritySet);
//    }
//
//    public UserDetails save(UserDo userDo) {
//        userManager.save(userDo);
//        AuthorityDo authorityDo = new AuthorityDo();
//        authorityDo.setAuthority("USER");
//        authorityDo.setUserId(userDo.getId());
//        authorityManager.save(authorityDo);
//        Set<GrantedAuthority> authoritySet = authorityManager.findByUserId(userDo.getId()).stream()
//                .map(authority -> new SimpleGrantedAuthority(rolePrefix + authority.getAuthority())).collect(Collectors.toSet());
//        return new User(userDo.getUsername(), userDo.getPassword(), userDo.getOpenId(), userDo.getEnabled(), true, true, true, authoritySet);
//    }
//
//    public User loadUserByOpenId(String openId) {
//        UserDo userDo = userManager.findByOpenId(openId);
//        if (null == userDo)
//            return null;
//        Set<GrantedAuthority> authoritySet = authorityManager.findByUserId(userDo.getId()).stream()
//                .map(authorityDo -> new SimpleGrantedAuthority(rolePrefix + authorityDo.getAuthority())).collect(Collectors.toSet());
//
//        return new User(userDo.getUsername(), userDo.getPassword(), userDo.getOpenId(), userDo.getEnabled(), true, true, true, authoritySet);
//    }
//
//    public void setRolePrefix(String rolePrefix) {
//        this.rolePrefix = rolePrefix;
//    }
//}
