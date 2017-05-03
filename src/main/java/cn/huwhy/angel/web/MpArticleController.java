package cn.huwhy.angel.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.huwhy.angel.biz.MpArticleBiz;
import cn.huwhy.angel.po.MpArticle;

@Controller("webMpArticleController")
@RequestMapping("/mp-article")
public class MpArticleController {

    @Autowired
    private MpArticleBiz articleBiz;

    @RequestMapping("/{id:\\d+}.html")
    public String article(ModelMap map,
                          @PathVariable("id") Integer id) {
        MpArticle article = articleBiz.get(id);
        map.addAttribute("article", article);
        return "blog/mp-article";
    }

}
