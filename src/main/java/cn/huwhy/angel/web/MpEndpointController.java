package cn.huwhy.angel.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletInputStream;
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
import cn.huwhy.wx.sdk.aes.WXBizMsgCrypt;
import cn.huwhy.wx.sdk.listener.EventHandler;
import cn.huwhy.wx.sdk.model.Command;

@Controller
@RequestMapping("mp")
public class MpEndpointController extends BaseController {
    private static String ERROR_MSG   = "非法请求";
    private static String SUCCESS_MSG = "";

    @Autowired
    private MpConfigManager  mpConfigManager;
    @Autowired
    private WxBizMsgCryptBiz wxBizMsgCryptBiz;
    @Autowired
    private EventHandler     eventHandler;

    @RequestMapping(value = "/endpoint/{salt:\\d{5}}{id}")
    public void endPoint(HttpServletRequest request,
                         HttpServletResponse response,
                         @PathVariable Integer salt,
                         @PathVariable("id") Integer id) throws AesException, NoSuchAlgorithmException, IOException {
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
            logger.debug("endpoint-params: {}, {}, {}, {}", signature, nonce, timestamp, echostr);
            if (!wxBizMsgCryptBiz.check(config, signature, timestamp, nonce)) {
                logger.debug("endpoint: {} - ckeckError", id);
                printResponse(response, ERROR_MSG);
                return;
            }
            // 处理接收消息
            ServletInputStream in = request.getInputStream();
            StringBuilder xmlMsg = new StringBuilder();
            byte[] b = new byte[4096];
            for (int n; (n = in.read(b)) != -1; ) {
                xmlMsg.append(new String(b, 0, n, "iso8859-1"));
            }
            logger.debug("receive-xml: {}", xmlMsg);
            Command command = wxBizMsgCryptBiz.transform(config, signature, timestamp, nonce, xmlMsg.toString());
            String replyMsg = eventHandler.handler(command);
            printResponse(response, Strings.isNullOrEmpty(replyMsg) ? SUCCESS_MSG : replyMsg);
        } else {
            logger.debug("endpoint: {} - failure", id);
            printResponse(response, ERROR_MSG);
        }
    }

    public static void main(String[] args) throws AesException, NoSuchAlgorithmException {
        String sign = "6c98cdde3d1e2f39af98987fe2c7bb2cb4862140";
        String nonce = "2111625032";
        String timestamp = "1491895293";
        String receiveXml = "<xml>\n" +
                "    <ToUserName><![CDATA[gh_29b368a5b449]]></ToUserName>\n" +
                "    <Encrypt><![CDATA[DZQURPqnY3ksM7kp0qevG2QdA+1DNv0pdjIt2IbXDHgCg2A9roP5nQ+eMcnogsRhNpV3ZnhR310ShMw8S+mreBY+tN3SHTbJu0yUnGLY17AaM+F1tGa4rucuD+5dSAAET5zAEIHJyKg03khsOAZtD6qXwFwWRE5cMKTopnGP09ev41HzcQ4+Vw\n" +
                "qpL95od2hVeQbMWkIn6JF6GGVECg4CH7jT18CxwwyTOAlj3nFYqAfxQ7MPBF9AvfJDgxI/vuLIKyBva8u0zQb3J4q7KpIggg08QKI3TrtP2fZTG8eZiPaiZ1d2rApFxfVrcyTNHlZvfcI5PfNCOo4rt+nsnl29I+jYKnGVYCOXGsaXq6rEcxnHEwvj3VRpKudfFM9n7bvIdj\n" +
                "Q/7C70KLHXcMSqGyh+CcRppjLU5cItp+qdVsOYKo8=]]></Encrypt>";
        WXBizMsgCrypt crypt = new WXBizMsgCrypt("daojunshuo87", "N0Agg6ZPOPRVIT8dwidiHFPP5kkQ0orT2tJQc5siU5a", "wx9b9c25b74beeb7fc");
        System.out.println(crypt.decryptMsg(sign, timestamp, nonce, receiveXml));
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
