package cn.huwhy.angel.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import cn.huwhy.angel.biz.manager.ArticleManager;
import cn.huwhy.angel.common.Paging;
import cn.huwhy.angel.po.Article;
import cn.huwhy.angel.term.ArticleTerm;

@Service
public class ArticleBiz {

    @Autowired
    private ArticleManager articleManager;

    public Integer save(Article article) {
        return articleManager.save(article);
    }

    public Paging<Article> findPaging(ArticleTerm term) {
        if (!Strings.isNullOrEmpty(term.getTitle())) {
            term.setTitle(term.getTitle() + "%");
        }
        return articleManager.findPaging(term);
    }

    public void display(Integer id) {
        articleManager.display(id);
    }

    public void hide(Integer id) {
        articleManager.hide(id);
    }

}
