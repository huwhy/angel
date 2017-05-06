package cn.huwhy.angel.po;

import java.util.Date;

import cn.huwhy.angel.enums.MpReplyType;

public class MpReply extends BaseModel<Integer> {
    private Integer     id;
    private Date        created;
    private Date        updated;
    private Boolean     deleted;
    private String      keywords;
    private String      content;
    private MpReplyType type;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public Date getUpdated() {
        return updated;
    }

    @Override
    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MpReplyType getType() {
        return type;
    }

    public void setType(MpReplyType type) {
        this.type = type;
    }
}
