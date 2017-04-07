package cn.huwhy.angel.biz.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.huwhy.angel.common.Paging;
import cn.huwhy.angel.dao.ArticleDao;
import cn.huwhy.angel.enums.ArticleStatus;
import cn.huwhy.angel.po.Article;
import cn.huwhy.angel.term.ArticleTerm;

@Component
public class ArticleManager {

    @Autowired
    private ArticleDao articleDao;

    public Integer save(Article article) {
        articleDao.save(article);
        return article.getId();
    }

    public Paging<Article> findPaging(ArticleTerm term) {
        List<Article> data = articleDao.findPaging(term);
        return new Paging<>(term, data);
    }

    public void display(Integer id) {
        articleDao.setStatus(id, ArticleStatus.display);
    }

    public void hide(Integer id) {
        articleDao.setStatus(id, ArticleStatus.hide);
    }

}
