package cn.huwhy.angel.biz.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

import cn.huwhy.angel.common.Paging;
import cn.huwhy.angel.dao.MpConfigDao;
import cn.huwhy.angel.po.MpConfig;
import cn.huwhy.angel.term.MpConfigTerm;
import cn.huwhy.angel.util.RandomUtils;
import cn.huwhy.wx.sdk.aes.SHA1;
import cn.huwhy.wx.sdk.api.AccessTokenApi;
import cn.huwhy.wx.sdk.api.JsApiTicketApi;
import cn.huwhy.wx.sdk.model.AccessToken;
import cn.huwhy.wx.sdk.model.JsTicket;
import cn.huwhy.wx.sdk.model.WxJsApiSignature;

@Component
public class MpConfigManager {

    @Autowired
    private MpConfigDao mpConfigDao;

    public static Object accessTokenLock = new Object();
    public static Object jsTickedLock    = new Object();

    public void save(MpConfig po) {
        mpConfigDao.save(po);
    }

    public MpConfig get(Integer id) {
        return mpConfigDao.get(id);
    }

    public Paging<MpConfig> findPaging(MpConfigTerm term) {
        if (!Strings.isNullOrEmpty(term.getName())) {
            term.setName(term.getName() + "%");
        }
        List<MpConfig> list = mpConfigDao.findPaging(term);
        return new Paging<>(term, list);
    }

    public String getAccessToken(Integer id) {
        MpConfig config = get(id);
        if (config != null) {
            if (Strings.isNullOrEmpty(config.getAccessToken()) || config.getExpiresTime() > System.currentTimeMillis()) {
                synchronized (accessTokenLock) {
                    if (Strings.isNullOrEmpty(config.getAccessToken()) || config.getExpiresTime() > System.currentTimeMillis()) {
                        AccessToken token = AccessTokenApi.getAppAccessToken(config.getAppId(), config.getSecret());
                        if (token != null) {
                            config.setAccessToken(token.getAccessToken());
                            config.setExpiresTime(System.currentTimeMillis() + 720000L);
                            mpConfigDao.save(config);
                        }
                    }
                }
            }
            return config.getAccessToken();
        }
        return "";
    }

    public String getJsTicket(Integer id) {
        MpConfig config = get(id);
        if (config != null) {
            if (Strings.isNullOrEmpty(config.getJsApiTicket()) || config.getJsExpiresTime() > System.currentTimeMillis()) {
                synchronized (jsTickedLock) {
                    if (Strings.isNullOrEmpty(config.getJsApiTicket()) || config.getJsExpiresTime() > System.currentTimeMillis()) {
                        JsTicket ticked = JsApiTicketApi.getTicked(getAccessToken(id));
                        if (ticked != null) {
                            config.setAccessToken(ticked.getTicket());
                            config.setJsExpiresTime(System.currentTimeMillis() + 720000L);
                            mpConfigDao.save(config);
                        }
                    }
                }
            }
            return config.getJsApiTicket();
        }
        return "";
    }

    public WxJsApiSignature getJsApiSignature(int id, String url) {
        long timestamp = System.currentTimeMillis() / 1000;
        String noncestr = RandomUtils.getRandomStr();
        String jsapiTicket = getJsTicket(id);
        try {
            String signature = SHA1.genWithAmple(
                    "jsapi_ticket=" + jsapiTicket,
                    "noncestr=" + noncestr,
                    "timestamp=" + timestamp,
                    "url=" + url
            );
            MpConfig config = get(id);
            WxJsApiSignature jsapiSignature = new WxJsApiSignature();
            jsapiSignature.setAppId(config.getAppId());
            jsapiSignature.setTimestamp(timestamp);
            jsapiSignature.setNonceStr(noncestr);
            jsapiSignature.setUrl(url);
            jsapiSignature.setSignature(signature);
            return jsapiSignature;
        } catch (Exception e) {
            return null;
        }
    }

}