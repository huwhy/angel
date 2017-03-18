package cn.huwhy.angel.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Paging<T> implements Serializable {
    public static Paging empty = new Paging<>(0L, 15L, 1L);
    /**
     * 总条数
     */
    private Long totalNum;
    /**
     * 每页条数 默认10
     */
    private Long pageSize = 10L;
    /**
     * 当前页
     */
    private Long pageNum;

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

    public Paging(Long totalNum, Long pageSize, Long pageNum) {
        this.totalNum = totalNum;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public static Paging empty(Term term) {
        Paging paging = new Paging(0L, term.getPageSize(), term.getPageNum());
        paging.setData(new ArrayList<>(0));
        return paging;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public Long getTotalPage() {
        Long totalPage = this.totalNum / this.pageSize;
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
