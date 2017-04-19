package cn.huwhy.angel.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.huwhy.angel.biz.ArticleBiz;
import cn.huwhy.angel.biz.manager.SeoManager;
import cn.huwhy.angel.common.Paging;
import cn.huwhy.angel.enums.ArticleStatus;
import cn.huwhy.angel.po.Article;
import cn.huwhy.angel.term.ArticleTerm;

@Controller
public class IndexController {

    @Autowired
    private ArticleBiz articleBiz;
    @Autowired
    private SeoManager seoManager;

    @RequestMapping({"/", "index.html"})
    public String home(ModelMap map, HttpServletRequest request) {
        ArticleTerm term = new ArticleTerm();
        term.setStatus(ArticleStatus.display);
        Paging<Article> paging = articleBiz.findPaging(term);
        map.addAttribute("paging", paging);
        map.addAttribute("seo", seoManager.getHomeSeo());
        return "blog/index";
    }
}
