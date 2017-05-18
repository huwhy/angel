package cn.huwhy.angel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.huwhy.angel.ibatis.BaseDao;
import cn.huwhy.angel.po.ResourceUrl;

public interface ResourceUrlDao extends BaseDao<ResourceUrl, Integer> {

    List<ResourceUrl> all();

    List<ResourceUrl> listUserResources(@Param("userId") Integer userId);
}
