package cn.huwhy.angel.dao;

import org.apache.ibatis.annotations.Param;

import cn.huwhy.angel.enums.ArticleStatus;
import cn.huwhy.angel.ibatis.BaseDao;
import cn.huwhy.angel.po.MpArticle;

public interface MpArticleDao extends BaseDao<MpArticle, Integer> {

    void setStatus(@Param("id") Integer id, @Param("status")ArticleStatus status);

}
