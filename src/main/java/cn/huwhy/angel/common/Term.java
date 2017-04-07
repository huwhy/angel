package cn.huwhy.angel.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class Term implements Serializable {
    /**
     * 分页数
     */
    private Integer pageSize = 15;
    /**
     * 查询页
     */
    private Integer pageNum  = 1;
    /**
     * 总记录数
     */
    private Integer totalNum = 0;
    /**
     * 是否快速查询分布
     * totalNum 返回剩余的数据总量
     */
    private Boolean hasStart = false;
    /**
     * 返回总记录数
     */
    private Boolean hasTotal = true;
    /**
     * 排序
     */
    private Map<String, Sort>   sorts;
    /**
     * 扩展参数
     */
    private Map<String, Object> args;

    public Map<String, Object> getArgs() {
        return args;
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

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Boolean getHasTotal() {
        return hasTotal;
    }

    public void setHasTotal(Boolean hasTotal) {
        this.hasTotal = hasTotal;
    }

    public Boolean getHasStart() {
        return hasStart;
    }

    public void setHasStart(Boolean hasStart) {
        this.hasStart = hasStart;
    }

    public Integer getStart() {
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
