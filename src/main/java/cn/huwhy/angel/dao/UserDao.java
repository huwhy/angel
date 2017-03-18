package cn.huwhy.angel.dao;

import org.apache.ibatis.annotations.Param;

import cn.huwhy.angel.ibatis.BaseDao;
import cn.huwhy.angel.po.User;

public interface UserDao extends BaseDao<User, Integer> {
    User findByName(@Param("username") String username);
}
