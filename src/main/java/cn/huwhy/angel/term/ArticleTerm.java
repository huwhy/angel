package cn.huwhy.angel.term;

import cn.huwhy.angel.common.Term;
import cn.huwhy.angel.enums.ArticleStatus;

public class ArticleTerm extends Term {
    private Integer       firstCid;
    private Integer       secondCid;
    private Integer       thirdCid;
    private ArticleStatus status;
    private String        title;

    public Integer getFirstCid() {
        return firstCid;
    }

    public void setFirstCid(Integer firstCid) {
        this.firstCid = firstCid;
    }

    public Integer getSecondCid() {
        return secondCid;
    }

    public void setSecondCid(Integer secondCid) {
        this.secondCid = secondCid;
    }

    public Integer getThirdCid() {
        return thirdCid;
    }

    public void setThirdCid(Integer thirdCid) {
        this.thirdCid = thirdCid;
    }

    public ArticleStatus getStatus() {
        return status;
    }

    public void setStatus(ArticleStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
