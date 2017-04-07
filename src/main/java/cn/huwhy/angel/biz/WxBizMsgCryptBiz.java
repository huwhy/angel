package cn.huwhy.angel.biz;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.huwhy.angel.po.MpConfig;
import cn.huwhy.wx.sdk.aes.AesException;
import cn.huwhy.wx.sdk.aes.SHA1;
import cn.huwhy.wx.sdk.aes.WXBizMsgCrypt;

@Component
public class WxBizMsgCryptBiz {
    private static Map<String, WXBizMsgCrypt> msgCryptMap = new HashMap<>();
    private static Object                     lock        = new Object();

    public boolean check(MpConfig config, String timestamp, String nonce, String signature) throws AesException, NoSuchAlgorithmException {
        WXBizMsgCrypt crypt = msgCryptMap.get(config.getAppId());
        if (crypt == null) {
            synchronized (lock) {
                crypt = msgCryptMap.get(config.getAppId());
                if (crypt == null) {
                    crypt = new WXBizMsgCrypt(config.getToken(), config.getAesKey(), config.getAppId());
                    msgCryptMap.put(config.getAppId(), crypt);
                }
            }
        }
        return crypt.check(signature, timestamp, nonce);
    }

    public static void main(String[] args) throws AesException, NoSuchAlgorithmException {
        String signature="78d8d11bc3abf3b2ad573e0f06f44e0623550986";
        String echostr="261501770283347967";
        String timestamp="1489322977";
        String nonce="1200781602";
        String token = "daojunshuo";
        System.out.println(SHA1.gen(token, timestamp, nonce));
    }
}