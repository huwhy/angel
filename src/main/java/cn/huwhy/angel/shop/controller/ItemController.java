package cn.huwhy.angel.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/item")
public class ItemController {

    @RequestMapping("{id:\\d+}.html")
    public String detail(@PathVariable("id") Long id) {

        return "shop/item/detail";
    }

}
