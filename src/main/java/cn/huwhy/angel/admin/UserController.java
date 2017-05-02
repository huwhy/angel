package cn.huwhy.angel.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.huwhy.angel.biz.UserBiz;
import cn.huwhy.angel.common.Json;
import cn.huwhy.angel.common.Paging;
import cn.huwhy.angel.po.User;
import cn.huwhy.angel.term.UserTerm;

@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserBiz userBiz;

    @RequestMapping("list.html")
    public String list(ModelMap modelMap) {
        Paging<User> paging = userBiz.findUsers(new UserTerm());
        modelMap.addAttribute("paging", paging);
        modelMap.addAttribute("data", JSON.toJSONString(paging.getData()));
        return "admin/user/list";
    }

    @RequestMapping("next")
    @ResponseBody
    public Json next(String username, Integer pageNum) {
        UserTerm term = new UserTerm();
        term.setUsername(username);
        term.setPageNum(pageNum);
        Paging<User> paging = userBiz.findUsers(new UserTerm());
        return Json.SUCCESS().setData(paging);
    }

}
