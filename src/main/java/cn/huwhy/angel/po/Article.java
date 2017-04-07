package cn.huwhy.angel.po;

import cn.huwhy.angel.enums.ArticleStatus;

public class Article extends BaseModel<Integer> {

    private Integer       firstCid;
    private Integer       secondCid;
    private Integer       thirdCid;
    private String        title;
    private String        tags;
    private String        summary;
    private String        content;
    private ArticleStatus status;
    private String        imgUrl;
    private String        author;
    private Integer       comments;
    private Integer       views;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus2() {
        return getStatus().getCnName();
    }

    public ArticleStatus getStatus() {
        return status;
    }

    public void setStatus(ArticleStatus status) {
        this.status = status;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }


    private String firstName;
    private String secondName;
    private String thirdName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }
}
