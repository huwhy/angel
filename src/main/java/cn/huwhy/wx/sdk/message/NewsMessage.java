package cn.huwhy.wx.sdk.message;

import java.util.List;

import cn.huwhy.angel.po.Article;

public class NewsMessage extends Message {

    private List<Article> news;

    public NewsMessage() {
        this.setMsgType("news");
    }

    public List<Article> getNews() {
        return news;
    }

    public void setNews(List<Article> news) {
        this.news = news;
    }

    public class News{
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
