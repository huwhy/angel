package cn.huwhy.angel.dao;

import org.apache.ibatis.annotations.Param;

import cn.huwhy.angel.enums.SeoKey;
import cn.huwhy.angel.ibatis.BaseDao;
import cn.huwhy.angel.po.Seo;

public interface SeoDao extends BaseDao<Seo, Integer> {

    Seo getSeo(@Param("seoKey") SeoKey seoKey, @Param("targetId") Integer targetId);
}
