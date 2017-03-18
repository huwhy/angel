package cn.huwhy.angel.biz.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import cn.huwhy.angel.common.Paging;
import cn.huwhy.angel.dao.UserDao;
import cn.huwhy.angel.po.User;
import cn.huwhy.angel.term.UserTerm;

@Service
public class UserManager {
    @Autowired
    private UserDao userDao;

    public void save(User user) {
        userDao.save(user);
    }

    public User findByName(String username)
            throws DataAccessException {
        return userDao.findByName(username);
    }

    public Paging<User> findUsers(UserTerm term) {
        List<User> list = userDao.findPaging(term);
        return new Paging<>(term, list);
    }

}
