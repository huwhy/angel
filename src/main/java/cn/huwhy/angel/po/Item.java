package cn.huwhy.angel.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.common.base.Splitter;

import cn.huwhy.angel.enums.ItemStatus;
import cn.huwhy.angel.util.StringUtil;

public class Item implements Serializable {
    private Long       id;
    private Long       sid;
    private String     title;
    private String     subTitle;
    private String     mainImg;
    private String     images;
    private BigDecimal lowPrice;
    private BigDecimal highPrice;
    private Integer    stock;
    private ItemStatus status;
    private String     attributes;
    private String     content;
    private String     tbUrl;
    private Date       updated;
    private Date       created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTbUrl() {
        return tbUrl;
    }

    public void setTbUrl(String tbUrl) {
        this.tbUrl = tbUrl;
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

    public List<String> getImageList() {
        if (StringUtil.isEmpty(images))
            return Collections.emptyList();
        return Splitter.on(',').splitToList(images);
    }
}
