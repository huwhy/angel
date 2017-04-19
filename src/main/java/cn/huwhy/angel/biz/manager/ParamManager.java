package cn.huwhy.angel.biz.manager;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.huwhy.angel.dao.ParamDao;
import cn.huwhy.angel.enums.ParamType;
import cn.huwhy.angel.po.Param;

@Component
public class ParamManager {

    @Autowired
    private ParamDao paramDao;

    public Param getDisplayCatParam() {
        Param param = paramDao.get(Param.ITEM_CAT_DISPLAY_NUM_PARAM_ID);
        if (param == null) {
            param = new Param();
            param.setId(Param.ITEM_CAT_DISPLAY_NUM_PARAM_ID);
            param.setName("一级类目显示数");
            param.setType(ParamType.Str);
            param.setVal("5");
            param.setUpdated(new Date());
            param.setCreated(new Date());
            paramDao.save(param);
        }
        return param;
    }

    public Param getDisplaySlideParam() {
        Param param = paramDao.get(Param.SLIDE_DISPLAY_NUM_ID);
        if (param == null) {
            param = new Param();
            param.setId(Param.SLIDE_DISPLAY_NUM_ID);
            param.setName("首页广告图显示数");
            param.setType(ParamType.Str);
            param.setVal("5");
            param.setUpdated(new Date());
            param.setCreated(new Date());
            paramDao.save(param);
        }
        return param;
    }

    public Param getSiteNameParam() {
        Param param = paramDao.get(Param.SITE_NAME_ID);
        if (param == null) {
            param = new Param();
            param.setId(Param.SITE_NAME_ID);
            param.setName("网站名");
            param.setType(ParamType.Str);
            param.setVal("Katyusha");
            param.setUpdated(new Date());
            param.setCreated(new Date());
            paramDao.save(param);
        }
        return param;
    }

    public Param getSiteBeiAnParam() {
        Param param = paramDao.get(Param.SITE_BEI_AN_ID);
        if (param == null) {
            param = new Param();
            param.setId(Param.SITE_BEI_AN_ID);
            param.setName("网站备案号");
            param.setType(ParamType.Str);
            param.setVal("粤ICP备XXXXXXX号");
            param.setUpdated(new Date());
            param.setCreated(new Date());
            paramDao.save(param);
        }
        return param;
    }

    public Param getContactParam() {
        Param param = paramDao.get(Param.CONTACT_ID);
        if (param == null) {
            param = new Param();
            param.setId(Param.CONTACT_ID);
            param.setName("联系页");
            param.setType(ParamType.Str);
            param.setVal("<p>邮箱：test@qq.com</p><p>地址：中国浙江省杭州市XXX街XXX号</p><p>手机号码：12340000222</p>");
            param.setUpdated(new Date());
            param.setCreated(new Date());
            paramDao.save(param);
        }
        return param;
    }
}
