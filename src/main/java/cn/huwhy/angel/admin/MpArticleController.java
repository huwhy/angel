package cn.huwhy.angel.admin;

import java.io.IOException;

import org.markdown4j.Markdown4jProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.huwhy.angel.biz.MpArticleBiz;
import cn.huwhy.angel.common.Json;
import cn.huwhy.angel.common.Paging;
import cn.huwhy.angel.enums.ArticleStatus;
import cn.huwhy.angel.po.MpArticle;
import cn.huwhy.angel.term.MpArticleTerm;
import cn.huwhy.angel.util.EnumUtils;

@Controller
@RequestMapping("/mp-article")
public class MpArticleController {

    @Autowired
    private MpArticleBiz articleBiz;

    @RequestMapping("list.html")
    public String list(ModelMap modelMap) {
        MpArticleTerm term = new MpArticleTerm();
        Paging<MpArticle> paging = articleBiz.findPaging(term);
        modelMap.addAttribute("paging", paging);
        modelMap.addAttribute("statuses", ArticleStatus.values());
        modelMap.addAttribute("data", JSON.toJSONString(paging.getData()));
        return "mp_article/list";
    }

    @RequestMapping("{id:\\d+}.html")
    public String detail(ModelMap modelMap, @PathVariable("id") Integer id) {
        MpArticle article = articleBiz.get(id);
        modelMap.addAttribute("article", article);
        return "mp_article/detail";
    }

    @RequestMapping("add.html")
    public String addHtml() {
        return "mp_article/add";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public Json save(String title, String content) {
        MpArticle article = new MpArticle();
        article.setTitle(title);
        article.setContent(content);
        article.setStatus(ArticleStatus.display);
        articleBiz.save(article);
        return Json.SUCCESS();
    }

    private Markdown4jProcessor markdown4jProcessor = new Markdown4jProcessor();
    @RequestMapping(value = "marked", method = RequestMethod.POST)
    @ResponseBody
    public Json marked(String text) throws IOException {
        return Json.SUCCESS().setData(markdown4jProcessor.process(text));
    }

    @RequestMapping("next")
    @ResponseBody
    public Json next(@RequestParam(value = "title", defaultValue = "") String title,
                     @RequestParam(value = "status", defaultValue = "") String statusName,
                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        MpArticleTerm term = new MpArticleTerm();
        term.setTitle(title);
        term.setStatus(EnumUtils.valueOf(ArticleStatus.class, statusName));
        term.setPageNum(pageNum);
        Paging<MpArticle> paging = articleBiz.findPaging(term);
        return Json.SUCCESS().setData(paging);
    }

    @RequestMapping(value = "display", method = RequestMethod.POST)
    @ResponseBody
    public Json display(@RequestParam(value = "id") Integer id) {
        articleBiz.display(id);
        return Json.SUCCESS();
    }

    @RequestMapping(value = "hide", method = RequestMethod.POST)
    @ResponseBody
    public Json hide(@RequestParam(value = "id") Integer id) {
        articleBiz.hide(id);
        return Json.SUCCESS();
    }

}
