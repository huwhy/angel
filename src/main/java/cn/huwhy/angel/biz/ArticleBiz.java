package cn.huwhy.angel.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import cn.huwhy.angel.biz.manager.ArticleManager;
import cn.huwhy.angel.biz.manager.CategoryManager;
import cn.huwhy.angel.common.Paging;
import cn.huwhy.angel.po.Article;
import cn.huwhy.angel.po.Category;
import cn.huwhy.angel.term.ArticleTerm;

@Service
public class ArticleBiz {

    @Autowired
    private ArticleManager  articleManager;
    @Autowired
    private CategoryManager categoryManager;

    public Integer save(Article article) {
        return articleManager.save(article);
    }

    public Article get(Integer id) {
        Article article = articleManager.get(id);
        if (article.getFirstCid() > 0) {
            Category firstCat = categoryManager.get(article.getFirstCid());
            if (firstCat != null) {
                article.setFirstName(firstCat.getName());
            }
        }
        if (article.getSecondCid() > 0) {
            Category secondCat = categoryManager.get(article.getFirstCid());
            if (secondCat != null) {
                article.setSecondName(secondCat.getName());
            }
        }
        if (article.getThirdCid() > 0) {
            Category cat = categoryManager.get(article.getFirstCid());
            if (cat != null) {
                article.setThirdName(cat.getName());
            }
        }
        return article;
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
