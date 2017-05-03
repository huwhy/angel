package cn.huwhy.angel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.huwhy.angel.enums.LinkType;
import cn.huwhy.angel.ibatis.BaseDao;
import cn.huwhy.angel.po.Link;

public interface LinkDao extends BaseDao<Link, Integer> {

    List<Link> findLinksByUid(@Param("uid") Integer uid,
                              @Param("type") LinkType type);
}
