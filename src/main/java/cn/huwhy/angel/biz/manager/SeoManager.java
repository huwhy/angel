package cn.huwhy.angel.biz.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.huwhy.angel.dao.SeoDao;
import cn.huwhy.angel.enums.SeoKey;
import cn.huwhy.angel.po.Seo;

@Component
public class SeoManager {

    @Autowired
    private SeoDao seoDao;

    public Seo getHomeSeo() {
        return seoDao.getSeo(SeoKey.Home, 0);
    }

    public Seo getCatSeo(Integer catId) {
        return seoDao.getSeo(SeoKey.Cat, catId);
    }

}
