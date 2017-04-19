package cn.huwhy.angel.enums;

import cn.huwhy.angel.common.EnumValue;

public enum SeoKey implements EnumValue<String> {
    Unknown("未知"),
    Home("首页"),
    Cat("类目页"),
    Item("产品页");

    private String cnName;

    SeoKey(String cnName) {
        this.cnName = cnName;
    }

    public String getValue() {
        return this.name();
    }

    public String getCnName() {
        return cnName;
    }
}
