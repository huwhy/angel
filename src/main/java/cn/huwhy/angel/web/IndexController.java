package cn.huwhy.angel.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.huwhy.angel.admin.BaseController;
import cn.huwhy.angel.biz.ArticleBiz;
import cn.huwhy.angel.biz.CategoryBiz;
import cn.huwhy.angel.biz.manager.LinkManager;
import cn.huwhy.angel.biz.manager.SeoManager;
import cn.huwhy.angel.common.Paging;
import cn.huwhy.angel.enums.ArticleStatus;
import cn.huwhy.angel.po.Article;
import cn.huwhy.angel.po.Category;
import cn.huwhy.angel.po.Link;
import cn.huwhy.angel.term.ArticleTerm;
import cn.huwhy.angel.vo.Crumb;

@Controller
public class IndexController extends BaseController {

    @Autowired
    private ArticleBiz  articleBiz;
    @Autowired
    private CategoryBiz categoryBiz;
    @Autowired
    private SeoManager  seoManager;
    @Autowired
    private LinkManager linkManager;

    @RequestMapping("/MP_verify_{text}.txt")
    public void mpVerify(@PathVariable String text, HttpServletResponse response) {
        printResponse(response, text);
    }

    @RequestMapping({"/", "index.html"})
    public String home(ModelMap map, HttpServletRequest request) {
        ArticleTerm term = new ArticleTerm();
        term.setStatus(ArticleStatus.display);
        Paging<Article> paging = articleBiz.findPaging(term);
        map.addAttribute("paging", paging);
        map.addAttribute("seo", seoManager.getHomeSeo());
        return "blog/index";
    }

    @RequestMapping("/catalog/{id:\\d+}")
    public String catalog(ModelMap map, HttpServletRequest request,
                          @PathVariable("id") Integer id) {
        addCrumb(request, new Crumb("首页", "/"));
        Map<String, Object> root = new HashMap<>();
        Category cat = categoryBiz.get(id);
        root.put("name", cat.getName());
        if (cat.getPid() > 0) {
            Category parentCat = categoryBiz.get(cat.getPid());
            if (parentCat != null) {
                if (parentCat.getPid() > 0) {
                    Category ppCat = categoryBiz.get(parentCat.getPid());
                    if (ppCat != null) {
                        addCrumb(request, new Crumb(ppCat.getName(), ppCat.getPath()));
                    }
                }
                addCrumb(request, new Crumb(parentCat.getName(), parentCat.getPath()));
                root.put("parentName", parentCat.getName());
            }
        }
        addCrumb(request, new Crumb(cat.getName()));
        int firstCid = 0, secondCid = 0, thirdCid = 0;
        if (cat != null) {
            if (cat.getLevel() == 1) {
                firstCid = cat.getId();
            } else if (cat.getLevel() == 2) {
                secondCid = cat.getId();
            } else {
                thirdCid = id;
            }
        } else {
            thirdCid = id;
        }
        ArticleTerm term = new ArticleTerm();
        term.setFirstCid(firstCid);
        term.setSecondCid(secondCid);
        term.setThirdCid(thirdCid);
        term.setStatus(ArticleStatus.display);
        Paging<Article> paging = articleBiz.findPaging(term);
        map.addAttribute("paging", paging);
        map.addAttribute("seo", seoManager.getHomeSeo());
        return "blog/catalog";
    }

    @RequestMapping("/article/{id:\\d+}.html")
    public String article(ModelMap map, HttpServletRequest request,
                          @PathVariable("id") Integer id) {
        Article article = articleBiz.get(id);
        map.addAttribute("article", article);
        addCrumb(request, new Crumb("首页", "/"));
        if (!Strings.isNullOrEmpty(article.getFirstName())) {
            addCrumb(request, new Crumb(article.getFirstName()));
        }
        if (!Strings.isNullOrEmpty(article.getSecondName())) {
            addCrumb(request, new Crumb(article.getSecondName()));
        }
        if (!Strings.isNullOrEmpty(article.getThirdName())) {
            addCrumb(request, new Crumb(article.getThirdName()));
        }
        addCrumb(request, new Crumb(article.getTitle()));
        return "blog/article";
    }

    @RequestMapping("/hao123.html")
    public String hao123(ModelMap map, HttpServletRequest request) {
        List<Link> links = linkManager.findLinks();
        map.addAttribute("links", links);
        return "blog/hao123";
    }

    private void addCrumb(HttpServletRequest request, Crumb... crumbs) {
        List<Crumb> crumbList = (List<Crumb>) request.getAttribute("crumbs");
        if (crumbList == null) {
            crumbList = Lists.newArrayList();
        }
        crumbList.addAll(Arrays.asList(crumbs));
        request.setAttribute("crumbs", crumbList);
    }
}
