package cn.huwhy.angel.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Paging<T> implements Serializable {
    public static Paging empty = new Paging<>(0, 15, 1);
    /**
     * 总条数
     */
    private Integer totalNum;
    /**
     * 每页条数 默认10
     */
    private Integer pageSize = 10;
    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 数据
     */
    private List<T> data;

    public Paging() {
    }

    public Paging(Term term, List<T> data) {
        this.totalNum = term.getTotalNum();
        this.pageSize = term.getPageSize();
        this.pageNum = term.getPageNum();
        this.data = data;
    }

    public Paging(Integer totalNum, Integer pageSize, Integer pageNum) {
        this.totalNum = totalNum;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public static Paging empty(Term term) {
        Paging paging = new Paging(0, term.getPageSize(), term.getPageNum());
        paging.setData(new ArrayList<>(0));
        return paging;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getTotalPage() {
        Integer totalPage = this.totalNum / this.pageSize;
        if (this.totalNum % this.pageSize > 0) {
            totalPage += 1;
        }
        return totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
