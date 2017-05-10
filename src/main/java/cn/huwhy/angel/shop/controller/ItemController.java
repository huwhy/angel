package cn.huwhy.angel.shop.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.huwhy.angel.beetl.Angel;
import cn.huwhy.angel.biz.ItemImportService;
import cn.huwhy.angel.biz.manager.ItemManager;
import cn.huwhy.angel.biz.manager.MpConfigManager;
import cn.huwhy.angel.common.Json;
import cn.huwhy.angel.enums.ItemStatus;
import cn.huwhy.angel.po.Item;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemManager       itemManager;
    @Autowired
    private ItemImportService itemImportService;
    @Autowired
    private MpConfigManager   mpConfigManager;
    private String domain = "https://www.huwhy.cn";

    @RequestMapping("{id:\\d+}.html")
    public String detail(HttpServletRequest request,
                         ModelMap map,
                         @PathVariable("id") Long id) {
        Item item = itemManager.get(id);
        map.addAttribute("item", item)
                .addAttribute("jsSign", mpConfigManager.getJsApiSignature(2, Angel.getUrl(domain, request)));
        return "shop/item/detail";
    }

    @RequestMapping("import.html")
    public String importItem(String url) throws IOException {
        Json json = itemImportService.importAliItem(url);
        Item item = (Item) json.getData();
        item.setStatus(ItemStatus.ONLINE);
        itemManager.save(item);
        return "redirect:/item/" + item.getId() + ".html";
    }

}
