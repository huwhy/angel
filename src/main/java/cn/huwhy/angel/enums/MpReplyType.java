package cn.huwhy.angel.enums;

import cn.huwhy.angel.common.EnumValue;

public enum MpReplyType implements EnumValue<String> {
    TEXT,
    IMAGE,
    VOICE,
    VIDEO,
    music,
    NEWS;

    @Override
    public String getValue() {
        return name();
    }
}
