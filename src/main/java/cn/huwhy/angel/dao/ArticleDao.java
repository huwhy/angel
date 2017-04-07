package cn.huwhy.angel.dao;

import org.apache.ibatis.annotations.Param;

import cn.huwhy.angel.enums.ArticleStatus;
import cn.huwhy.angel.ibatis.BaseDao;
import cn.huwhy.angel.po.Article;

public interface ArticleDao extends BaseDao<Article, Integer> {
    void setStatus(@Param("id") Integer id, @Param("status")ArticleStatus status);

}
