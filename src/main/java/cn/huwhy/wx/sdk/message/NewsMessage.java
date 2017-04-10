package cn.huwhy.wx.sdk.message;

import java.util.List;

import cn.huwhy.angel.po.Article;

public class NewsMessage extends Message {

    private List<Article> articles;

    public NewsMessage() {
        this.setMsgType("news");
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
