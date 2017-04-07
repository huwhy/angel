package cn.huwhy.angel.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.huwhy.angel.biz.CategoryBiz;
import cn.huwhy.angel.common.Json;
import cn.huwhy.angel.common.Paging;
import cn.huwhy.angel.po.Category;
import cn.huwhy.angel.term.CategoryTerm;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryBiz categoryBiz;

    @RequestMapping("list.html")
    public String list(ModelMap modelMap) {
        CategoryTerm term = new CategoryTerm();
        Paging<Category> paging = categoryBiz.findPaging(term);
        modelMap.addAttribute("paging", paging);
        modelMap.addAttribute("data", JSON.toJSONString(paging.getData()));
        return "category/list";
    }

    @RequestMapping("next")
    @ResponseBody
    public Json next(@RequestParam(value = "pageNum", defaultValue = "2") Integer pageNum,
                     @RequestParam(value = "pid", defaultValue = "0") Integer pid,
                     @RequestParam(value = "name", defaultValue = "") String name,
                     @RequestParam(value = "level", defaultValue = "0") Integer level) {
        CategoryTerm term = new CategoryTerm();
        term.setName(name);
        term.setPid(pid);
        term.setLevel(level);
        term.setPageNum(pageNum);
        Paging<Category> paging = categoryBiz.findPaging(term);
        return Json.SUCCESS().setData(paging);
    }

}
