package cn.huwhy.angel.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.huwhy.angel.biz.manager.UserManager;
import cn.huwhy.angel.common.Paging;
import cn.huwhy.angel.po.User;
import cn.huwhy.angel.term.UserTerm;

@Service
public class UserBiz {

    @Autowired
    private UserManager userManager;

    public void save(User user) {
        userManager.save(user);
    }

    public Paging<User> findUsers(UserTerm term) {
        return userManager.findUsers(term);
    }
}
