package cn.huwhy.angel.security;

import org.springframework.security.core.GrantedAuthority;

import cn.huwhy.angel.po.ResourceUrl;

public class MyGrantedAuthority implements GrantedAuthority {

    private ResourceUrl po;

    public MyGrantedAuthority(ResourceUrl po) {
        this.po = po;
    }

    @Override
    public String getAuthority() {
        return po.getUrl();
    }

    public ResourceUrl getPo() {
        return po;
    }

    public void setPo(ResourceUrl po) {
        this.po = po;
    }
}
