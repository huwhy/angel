package cn.huwhy.angel.web;

import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Strings;

import cn.huwhy.angel.biz.WxBizMsgCryptBiz;
import cn.huwhy.angel.biz.manager.MpConfigManager;
import cn.huwhy.angel.po.MpConfig;
import cn.huwhy.wx.sdk.aes.AesException;

@Controller
@RequestMapping("mp")
public class MpEndpointController extends BaseController {
    private static String ERROR_MSG   = "非法请求";
    private static String SUCCESS_MSG = "";

    @Autowired
    private MpConfigManager  mpConfigManager;
    @Autowired
    private WxBizMsgCryptBiz wxBizMsgCryptBiz;

    @RequestMapping(value = "/endpoint/{salt:\\d{5}}{id}")
    public void endPoint(HttpServletRequest request,
                         HttpServletResponse response,
                         @PathVariable Integer salt,
                         @PathVariable("id") Integer id) throws AesException, NoSuchAlgorithmException {
        logger.debug("endpoint: {}", id);
        MpConfig config = mpConfigManager.get(id);
        if (config != null && config.getSalt().equals(salt)) {
            String signature = request.getParameter("signature");
            String nonce = request.getParameter("nonce");
            String timestamp = request.getParameter("timestamp");
            String echostr = request.getParameter("echostr");
            if (!Strings.isNullOrEmpty(echostr)) {
                logger.debug("endpoint: {} - success", id);
                printResponse(response, echostr);
                return;
            }
            if (!wxBizMsgCryptBiz.check(config, timestamp, nonce, signature)) {
                logger.debug("endpoint: {} - ckeckError", id);
                printResponse(response, ERROR_MSG);
                return;
            }
            printResponse(response, SUCCESS_MSG);
        } else {
            logger.debug("endpoint: {} - failure", id);
            printResponse(response, ERROR_MSG);
        }
    }

    void printResponse(HttpServletResponse response, String message) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            if (!Strings.isNullOrEmpty(message))
                writer.write(message);
        } catch (Exception e) {
            logger.error("WxMpEndpointController - printResponse - exception", e);
        } finally {
            try {
                if (null != writer)
                    writer.close();
            } catch (Exception ignore) {
            }
        }
    }

}
