package cn.huwhy.angel.enums;

import cn.huwhy.angel.common.EnumValue;

public enum ArticleStatus implements EnumValue<String> {
    unknown("未知道"),
    draft("未发布"),
    display("已发布"),
    hide("隐藏"),
    deleted("删除");

    private String cnName;

    private ArticleStatus(String cnName) {
        this.cnName = cnName;
    }

    @Override
    public String getValue() {
        return name();
    }

    public String getCnName() {
        return cnName;
    }
}
