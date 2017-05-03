package cn.huwhy.angel.enums;

import cn.huwhy.angel.common.EnumValue;

public enum LinkType implements EnumValue<String> {
    unknown("未知"),
    navigation("导航"),
    friendly("友链");

    private String cnName;
    LinkType(String cnName) {
        this.cnName = cnName;
    }

    public String getValue() {
        return this.name();
    }

    public String getCnName() {
        return cnName;
    }
}