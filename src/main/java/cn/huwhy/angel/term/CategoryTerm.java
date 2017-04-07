package cn.huwhy.angel.term;

import cn.huwhy.angel.common.Term;

public class CategoryTerm extends Term {

    private String name;

    private Integer pid;

    private Integer level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
