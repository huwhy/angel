package cn.huwhy.angel.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.huwhy.angel.biz.manager.MpArticleManager;
import cn.huwhy.angel.common.Paging;
import cn.huwhy.angel.po.MpArticle;
import cn.huwhy.angel.term.MpArticleTerm;

@Service
public class MpArticleBiz {

    @Autowired
    private MpArticleManager mpArticleManager;

    public Integer save(MpArticle article) {
        return mpArticleManager.save(article);
    }

    public MpArticle get(Integer id) {
        return mpArticleManager.get(id);
    }

    public Paging<MpArticle> findPaging(MpArticleTerm term) {
        return mpArticleManager.findPaing(term);
    }



    public void display(Integer id) {
        mpArticleManager.display(id);
    }

    public void hide(Integer id) {
        mpArticleManager.hide(id);
    }

}
