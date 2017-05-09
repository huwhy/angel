package cn.huwhy.angel.common;

import java.io.Serializable;

public class Json implements Serializable {

    /**
     * 处理成功
     */
    public static final Long SUCCESS = 200L;
    /**
     * 失败
     */
    public static final Long ERROR   = 400L;

    public static Json SUCCESS() {
        return new Json(SUCCESS);
    }

    public static Json ERROR() {
        return new Json(ERROR);
    }

    /**
     * 编码
     */
    private Long   code;
    /**
     * 消息
     */
    private String message;
    /**
     * 数据
     */
    private Object data;

    public Json() {
    }

    public Json(Long code) {
        this.code = code;
    }

    public Json(Long code, String message) {
        this.code = code;
        this.message = message;
    }

    public Long getCode() {
        return code;
    }

    public Json setCode(Long code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Json setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Json setData(Object data) {
        this.data = data;
        return this;
    }

    public boolean isOk() {
        return SUCCESS.equals(this.code);
    }

}
