package cn.huwhy.angel.po;

import java.util.Date;

import cn.huwhy.angel.enums.ParamType;

public class Param {
    public static final int ITEM_CAT_DISPLAY_NUM_PARAM_ID = 2;
    public static final int SLIDE_DISPLAY_NUM_ID = 3;
    public static final int SITE_NAME_ID = 4;
    public static final int SITE_BEI_AN_ID = 5;
    public static final int CONTACT_ID = 6;

    private int id;
    private String name;

    private ParamType type;
    private String    val;
    private Date      updated;
    private Date      created;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParamType getType() {
        return type;
    }

    public void setType(ParamType type) {
        this.type = type;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getPath() {
        if (CONTACT_ID == id) {
            return "/setting/contact";
        } else {
            return null;
        }
    }
}