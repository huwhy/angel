package cn.huwhy.angel.term;

import cn.huwhy.angel.common.Term;
import cn.huwhy.angel.enums.ArticleStatus;

public class MpArticleTerm extends Term {

    private Integer mpId;

    private String title;

    private ArticleStatus status;

    public Integer getMpId() {
        return mpId;
    }

    public void setMpId(Integer mpId) {
        this.mpId = mpId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArticleStatus getStatus() {
        return status;
    }

    public void setStatus(ArticleStatus status) {
        this.status = status;
    }
}
