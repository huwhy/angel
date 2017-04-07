package cn.huwhy.angel.po;

import java.util.Date;

public class BaseModel<T> {
    private T id;
    private Date updated;
    private Date created;

    public BaseModel() {
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
