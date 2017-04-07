package cn.huwhy.angel.biz.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.huwhy.angel.common.Paging;
import cn.huwhy.angel.dao.MpArticleDao;
import cn.huwhy.angel.enums.ArticleStatus;
import cn.huwhy.angel.po.MpArticle;
import cn.huwhy.angel.term.MpArticleTerm;

@Service
public class MpArticleManager {

    @Autowired
    private MpArticleDao mpArticleDao;

    public Integer save(MpArticle article) {
        mpArticleDao.save(article);
        return article.getId();
    }

    public MpArticle get(Integer id) {
        return mpArticleDao.get(id);
    }

    public Paging<MpArticle> findPaing(MpArticleTerm term) {
        List<MpArticle> list = mpArticleDao.findPaging(term);
        return new Paging<>(term, list);
    }

    public void display(Integer id) {
        mpArticleDao.setStatus(id, ArticleStatus.display);
    }

    public void hide(Integer id) {
        mpArticleDao.setStatus(id, ArticleStatus.hide);
    }

}
