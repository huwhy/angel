package cn.huwhy.angel.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @RequestMapping("{id:\\d+}.html")
    public String shop(ModelMap map, @PathVariable("id") Long id) {


        return "shop/shop";
    }

}
