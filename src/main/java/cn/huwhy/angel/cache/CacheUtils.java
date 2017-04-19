package cn.huwhy.angel.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

import cn.huwhy.angel.biz.manager.CategoryManager;
import cn.huwhy.angel.biz.manager.ParamManager;
import cn.huwhy.angel.po.Category;
import cn.huwhy.angel.po.Param;

@Component
public class CacheUtils {
    private static final Map<String, Object> cacheMap = Collections.synchronizedMap(new HashMap<String, Object>());

    @Autowired
    private ParamManager paramManager;
    @Autowired
    private CategoryManager categoryManager;

    public void cache(String key, Object val) {
        cacheMap.put(key, val);
    }

    public <T> T getCache(String key) {
        return (T) cacheMap.get(key);
    }

    public void clearCache(String key) {
        cacheMap.remove(key);
    }

    public List<Category> getArticleCats() {
        List<Category> catList = getCache(Category.class.getSimpleName());
        if (catList == null || catList.isEmpty()) {
            Param param = paramManager.getDisplayCatParam();
            catList = categoryManager.findFirsts(1, Integer.valueOf(param.getVal()));
            for (Category cat : catList) {
                cat.setChildren(categoryManager.findChildren(cat.getId()));
                for (Category cat2 : cat.getChildren()) {
                    cat2.setChildren(categoryManager.findChildren(cat2.getId()));
                }
            }
            cache(Category.class.getSimpleName(), catList);
        }
        return catList;
    }

    public String siteName() {
        String siteName = getCache("SITE_NAME");
        if (Strings.isNullOrEmpty(siteName)) {
            Param param = paramManager.getSiteNameParam();
            siteName = param.getVal();
            cache("SITE_NAME", siteName);
        }
        return siteName;
    }

    public String beiAnNo() {
        String beiAnNo = getCache("SITE_BEI_AN_NO");
        if (Strings.isNullOrEmpty(beiAnNo)) {
            Param param = paramManager.getSiteBeiAnParam();
            beiAnNo = param.getVal();
            cache("SITE_BEI_AN_NO", beiAnNo);
        }
        return beiAnNo;
    }
}