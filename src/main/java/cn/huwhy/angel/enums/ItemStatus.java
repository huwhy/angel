package cn.huwhy.angel.enums;

import cn.huwhy.angel.common.EnumValue;

public enum ItemStatus implements EnumValue<String> {

    ONLINE,
    OFFLINE;

    @Override
    public String getValue() {
        return name();
    }
}
