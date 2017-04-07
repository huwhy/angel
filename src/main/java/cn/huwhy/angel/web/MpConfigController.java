package cn.huwhy.angel.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.huwhy.angel.beetl.Angel;
import cn.huwhy.angel.biz.manager.MpConfigManager;
import cn.huwhy.angel.common.Json;
import cn.huwhy.angel.common.Paging;
import cn.huwhy.angel.po.MpConfig;
import cn.huwhy.angel.term.MpConfigTerm;

@Controller
@RequestMapping("/mp")
public class MpConfigController {

    @Autowired
    private MpConfigManager mpConfigManager;

    @RequestMapping("list.html")
    public String list(ModelMap modelMap) {
        MpConfigTerm term = new MpConfigTerm();
        term.setPageSize(10);
        term.setPageNum(1);
        Paging<MpConfig> paging = mpConfigManager.findPaging(term);
        modelMap.addAttribute("paging", paging);
        modelMap.addAttribute("data", JSON.toJSONString(paging.getData()));
        return "mp/list";
    }

    @RequestMapping("next")
    @ResponseBody
    public Json next(@RequestParam(value = "appId", required = false) String appId,
                     @RequestParam("pageNo") Integer pageNo) {
        MpConfigTerm term = new MpConfigTerm();
        term.setPageSize(10);
        term.setPageNum(pageNo);
        term.setAppId(appId);
        Paging<MpConfig> paging = mpConfigManager.findPaging(term);
        return Json.SUCCESS().setData(paging);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public Json save(@RequestBody MpConfig po) {
        po.setSalt(Angel.random(5));
        mpConfigManager.save(po);
        return Json.SUCCESS();
    }
}
