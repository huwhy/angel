package cn.huwhy.angel.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class Term implements Serializable {
    /**
     * 分页数
     */
    private Long    pageSize = 15L;
    /**
     * 查询页
     */
    private Long    pageNum  = 1L;
    /**
     * 总记录数
     */
    private Long    totalNum = 0L;
    /**
     * 返回总记录数
     */
    private Boolean hasTotal = true;
    /**
     * 排序
     */
    private Map<String, Sort> sorts;
    /**
     * 扩展参数
     */
    private Map<String, Object> args;

    public Map<String, Object> getArgs() {
        return args;
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

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public Boolean getHasTotal() {
        return hasTotal;
    }

    public void setHasTotal(Boolean hasTotal) {
        this.hasTotal = hasTotal;
    }

    public Long getStart() {
        return (this.pageNum - 1) * this.pageSize;
    }

    public Map<String, Sort> getSorts() {
        return sorts;
    }

    public void setSorts(Map<String, Sort> sorts) {
        this.sorts = sorts;
    }

    public void addSort(String field, Sort sort) {
        if (this.sorts == null) {
            this.sorts = new HashMap<>();
        }
        this.sorts.put(field, sort);
    }

    public void addArg(String name, Object value) {
        if (args == null) {
            this.args = new HashMap<>();
        }
        args.put(name, value);
    }

    public enum Sort {
        ASC,
        DESC;

        public String getValue() {
            return name();
        }
    }
}
