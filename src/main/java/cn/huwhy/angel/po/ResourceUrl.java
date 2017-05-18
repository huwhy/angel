package cn.huwhy.angel.po;

import java.io.Serializable;

public class ResourceUrl implements Serializable, Comparable<ResourceUrl> {
    private Integer id;
    private String  title;
    private String  url;
    private String  role;
    private Boolean display;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    @Override
    public int compareTo(ResourceUrl o) {
        return this.id - o.id;
    }
}
