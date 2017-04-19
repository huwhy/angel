package cn.huwhy.angel.beetl;

import java.io.IOException;

import org.beetl.core.GeneralVarTagBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.huwhy.angel.cache.CacheUtils;

@Component
public class SiteName extends GeneralVarTagBinding {

    @Autowired
    private CacheUtils cacheUtils;

    @Override
    public void render() {
        try {
            ctx.byteWriter.writeString(cacheUtils.siteName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
