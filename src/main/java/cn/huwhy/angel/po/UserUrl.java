package cn.huwhy.angel.po;

import java.io.Serializable;
import java.util.Date;

public class UserUrl implements Serializable {
    private Long    id;
    private Integer resourceId;
    private Date    created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
