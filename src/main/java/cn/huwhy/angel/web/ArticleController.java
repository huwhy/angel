package cn.huwhy.angel.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.huwhy.angel.biz.ArticleBiz;
import cn.huwhy.angel.common.Json;
import cn.huwhy.angel.common.Paging;
import cn.huwhy.angel.enums.ArticleStatus;
import cn.huwhy.angel.po.Article;
import cn.huwhy.angel.term.ArticleTerm;
import cn.huwhy.angel.util.EnumUtils;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleBiz articleBiz;

    @RequestMapping("list.html")
    public String list(ModelMap modelMap) {
        ArticleTerm term = new ArticleTerm();
        Paging<Article> paging = articleBiz.findPaging(term);
        modelMap.addAttribute("paging", paging);
        modelMap.addAttribute("statuses", ArticleStatus.values());
        modelMap.addAttribute("data", JSON.toJSONString(paging.getData()));
        return "article/list";
    }

    @RequestMapping("next")
    @ResponseBody
    public Json next(@RequestParam(value = "firstCid", defaultValue = "0") Integer firstCid,
                     @RequestParam(value = "secondCid", defaultValue = "0") Integer secondCid,
                     @RequestParam(value = "thirdCid", defaultValue = "0") Integer thirdCid,
                     @RequestParam(value = "title", defaultValue = "") String title,
                     @RequestParam(value = "status", defaultValue = "") String statusName,
                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        ArticleTerm term = new ArticleTerm();
        term.setTitle(title);
        term.setFirstCid(firstCid);
        term.setSecondCid(secondCid);
        term.setThirdCid(thirdCid);
        term.setStatus(EnumUtils.valueOf(ArticleStatus.class, statusName));
        term.setPageNum(pageNum);
        Paging<Article> paging = articleBiz.findPaging(term);
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
