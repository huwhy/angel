package cn.huwhy.angel.beetl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.beetl.core.GeneralVarTagBinding;
import org.springframework.stereotype.Component;

import cn.huwhy.angel.common.Paging;

@Component
public class WebPageTag extends GeneralVarTagBinding {
    private long total = 0;
    private int perSize = 0;
    private int curNo = 0;
    private int totalPage = 0;
    private Map<String, Object> params;
    private Set<String> keys = new HashSet<>();

    private void init() {
        Paging page = (Paging) getAttributeValue("val");
        total = page.getTotalNum();
        perSize = page.getPageSize();
        curNo = page.getPageNum();
        totalPage = (int) (total / perSize);
        if (totalPage * perSize < total) {
            totalPage++;
        }
        keys.add("perSize");
        keys.add("curNo");
        keys.add("totalPage");
    }

    @Override
    public void render() {
        init();
        StringBuilder html = new StringBuilder();
        html.append("<form class='pagination-form'><div class=\"sui-pagination pagination-large\">")
                .append("<ul>")
                .append("<li class=\"prev").append(curNo == 1 ? " disabled\"" : "\"").append("><a href=\"#\">«上一页</a></li>");
        int endNo = (curNo + 2) > totalPage ? totalPage : curNo + 2;
        int startNo = curNo <= 2 ? 1 : curNo - 2;
        endNo += curNo <= 3 ? (3 - curNo) : 0;
        endNo = endNo > totalPage ? totalPage : endNo;
        startNo -= (curNo + 2) > totalPage ? (curNo + 2 - totalPage) : 0;
        startNo = startNo < 1 ? 1 : startNo;
        for (int i = startNo; i <= endNo; i++) {
            html.append("<li ").append(i == curNo ? "class='active'>" : ">").append("<a>").append(i).append("</a ></li>");
        }
        if (totalPage > endNo) {
            html.append("<li class=\"dotted\"><span>...</span></li>");
        }
        html.append("<li class='next ").append(curNo == totalPage ? "disabled" : "").append("'><a>下一页»</a ></li>")
                .append("</ul>")
                .append("<div><span>共").append(totalPage).append("页&nbsp;</span><span>")
                .append("到")
                .append("<input type=\"text\" class=\"page-num h28\"><button class=\"page-confirm\" >确定</button>")
                .append("页</span></div>")
                .append("</div>")
                .append("<input type='hidden' name='curNo' value='").append(curNo).append("' />")
                .append("<input type='hidden' name='totalPage' value='").append(totalPage).append("' />");
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (!keys.contains(entry.getKey())) {
                    html.append("<input type=\"hidden\" name=\"").append(entry.getKey()).append("\" value=\"").append(entry.getValue()).append("\" />");
                }
            }
        }
        html.append("</form>")
                .append("<script src=\"/assets/blog/js/page.js\"></script>");

        try {
            ctx.byteWriter.writeString(html.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
