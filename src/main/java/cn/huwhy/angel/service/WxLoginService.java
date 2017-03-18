//package com.comblife.water.server.service;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSON;
//import com.comblife.athena.common.model.seller.Seller;
//import com.comblife.athena.common.util.StringUtil;
//import com.comblife.athena.main.service.SellerService;
//import com.comblife.athena.wechat.service.WechatTicketService;
//import com.comblife.interfaces.Json;
//import com.comblife.water.server.biz.model.User;
//
//import cn.huwhy.bees.config.springsupport.annotation.BeesReferer;
//
///**
// * @author huwhy
// * @data 2016/11/9
// * @Desc
// */
//@Component
//public class WxLoginService {
//
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    private UserService         userService;
//    @BeesReferer(basicReferer = "wechatReferer")
//    private WechatTicketService wechatTicketApi;
//
//    @BeesReferer(basicReferer = "mainReferer")
//    private SellerService sellerService;
//
//    public Json<String> wxLogin(HttpServletRequest request, Integer ticketId) {
//        Json<String> json = wechatTicketApi.getTicketUserInfo(ticketId);
//        logger.debug("ticket: {}", JSON.toJSONString(json));
//        if (StringUtil.isEmpty(json.getModel())) {
//            return Json.ERROR();
//        }
//        User de = userService.loadUserByOpenId(json.getModel());
//        if (de == null) {
//            return Json.ERROR().setCode(666L).setMessage("对不起，您没有权限！！！");
//        }
//        Seller seller = sellerService.getSellerByOpenId(de.getOpenId());
//        if (!"15869018124".equals(seller.getPhone())) {
//            return Json.ERROR().setCode(666L).setMessage("对不起，请叫点点来！！！");
//        }
//        wechatTicketApi.clearTicketOpenId(ticketId, json.getModel());
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(de, null, de.getAuthorities());
//        token.setDetails(new WebAuthenticationDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(token);
//        return Json.SUCCESS();
//    }
//
//}
