package cn.huwhy.angel.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.huwhy.angel.biz.ItemImportService;
import cn.huwhy.angel.biz.manager.ItemManager;
import cn.huwhy.angel.common.Json;

@Controller
@RequestMapping("/admin/item")
public class AdminItemController {
    @Autowired
    private ItemManager       itemManager;
    @Autowired
    private ItemImportService itemImportService;


    @RequestMapping("list.html")
    public String list(ModelMap map, HttpServletRequest request) {

        return "admin/item/list";
    }

    @RequestMapping("add")
    @ResponseBody
    public Json add(String url) {

        return Json.SUCCESS();
    }

}
