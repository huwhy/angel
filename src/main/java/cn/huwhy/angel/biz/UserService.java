package cn.huwhy.angel.biz;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cn.huwhy.angel.biz.manager.UserManager;
import cn.huwhy.angel.dao.ResourceUrlDao;
import cn.huwhy.angel.po.ResourceUrl;
import cn.huwhy.angel.po.User;
import cn.huwhy.angel.security.MyGrantedAuthority;
import cn.huwhy.angel.security.UserDetail;

@Service
public class UserService implements UserDetailsService {
    @Resource
    private UserManager    userManager;
    @Resource
    private ResourceUrlDao resourceUrlDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userManager.findByName(username);
        if (null == user)
            throw new UsernameNotFoundException("");
        List<ResourceUrl> resourceUrls = resourceUrlDao.listUserResources(user.getId());
        Set<GrantedAuthority> authoritySet = resourceUrls.stream().map(MyGrantedAuthority::new).collect(Collectors.toSet());
        return new UserDetail(user.getUsername(), user.getPassword(), authoritySet);
    }

}
