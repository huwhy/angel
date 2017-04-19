package cn.huwhy.angel.enums;

import cn.huwhy.angel.common.EnumValue;

public enum ParamType implements EnumValue<String> {
    unknown("未知"),
    Bool("布尔型"),
    Int("整数型"),
    Db("精度型"),
    Str("字符形");
    private String cnName;

    ParamType(String cnName) {
        this.cnName = cnName;
    }

    public String getValue() {
        return this.name();
    }

    public String getCnName() {
        return cnName;
    }
}
