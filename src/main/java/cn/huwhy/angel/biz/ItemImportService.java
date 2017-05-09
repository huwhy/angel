package cn.huwhy.angel.biz;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;

import cn.huwhy.angel.common.Json;
import cn.huwhy.angel.po.Item;
import cn.huwhy.angel.util.CollectionUtil;
import cn.huwhy.angel.util.StringUtil;

@Service
public class ItemImportService {

    private static Pattern TB_SKU_P      = Pattern.compile("skuMap\\s*:\\s*(\\{.*\\})[\\s\\S]*,propertyMemoMap\\s*:\\s*(\\{.*\\})");
    private static Pattern TB_DESC_URL_P = Pattern.compile("\\s*descUrl\\s*:\\s*location.protocol\\s*===\\s*'http:' \\s*\\?\\s*'([^']*)'\\s*");
    private static Pattern TB_IMAGES_P   = Pattern.compile("auctionImages[^:]*:\\s*(\\[[^\\]]+\\])");

    private static Pattern TM_SKU_P        = Pattern.compile("skuList.*:(\\[[^\\]]*\\])[^,]*,.*skuMap[^:]*:[^\\{]*(\\{([^\\{\\}]*\\{[^\\{\\}]*\\}[^\\{\\}]*)*\\})");
    private static Pattern TM_DESC_URL_P   = Pattern.compile("httpsDescUrl\":\"([^\"]*)\"");
    private static Pattern TM_IMAGES_P     = Pattern.compile("default[^:]*:\\s*(\\[[^\\]]+\\])[\\s\\S]*rateConfig");
    private static Pattern TMHK_DESC_URL_P = Pattern.compile("httpsDescUrl[^:]*:[^\"]*\"([^\"]+)\"");

    private static Pattern ID_P = Pattern.compile("[&|?]id=(\\d+)");

    public Json importAliItem(String url) throws IOException {
        if (url.matches("http[s]?://\\w+\\.tmall\\.com[\\s\\S]+")) {
            return importTMallItem(url);
        } else if (url.matches("http[s]?://\\w+\\.tmall\\.hk[\\s\\S]+")) {
            return importTMHK(url);
        } else {
            return importTaobaoItem(url);
        }
    }

    private Json importTaobaoItem(String taobaoItemUrl) throws IOException {
        Item item = new Item();
        Matcher idM = ID_P.matcher(taobaoItemUrl);
        if (idM.find()) {
            String idStr = idM.group(1);
            item.setId(Long.valueOf(idStr));
        }
        item.setTbUrl("https://item.taobao.com/item.htm?id=" + item.getId());
        Document doc = Jsoup.connect(item.getTbUrl()).userAgent("Mozilla").get();
        Elements titleEs = doc.select(".tb-main-title");
        if (titleEs.size() > 0) {
            item.setTitle(StringUtil.substring(titleEs.first().text(), 0, 32));
        }
        if (StringUtil.isEmpty(item.getTitle())) {
            return Json.ERROR().setMessage("商品标题没取到");
        }
        Elements attrEs = doc.select(".attributes-list li");
        StringBuilder attrSb = new StringBuilder();
        for (int i = 0; i < attrEs.size(); i++) {
            Element e = attrEs.get(i);
            attrSb.append(e.text()).append("\r\n");
        }
        item.setAttributes(attrSb.toString());

        Elements subTitleEs = doc.select(".tb-subtitle");
        if (subTitleEs.size() > 0) {
            item.setSubTitle(subTitleEs.first().text());
        }

        Elements jsEs = doc.select("script");
        for (int i = 0; i < jsEs.size(); i++) {
            Element e = jsEs.get(i);
            if (StringUtil.isEmpty(item.getImages()) && e.html().contains("auctionImages")) {
                List<String> images = new ArrayList<>();
                Matcher m = TB_IMAGES_P.matcher(e.html());
                if (m.find()) {
                    List<String> list = JSON.parseArray(m.group(1), String.class);
                    for (String img : list) {
                        images.add((img.startsWith("http") ? img : "https:" + img));
                    }
                }
                if (CollectionUtil.isEmpty(images)) {
                    return Json.ERROR().setMessage("商品图片没取到");
                }
                item.setMainImg(images.get(0));
                item.setImages(Joiner.on(',').join(images));
            }
            if (e.html().contains("Hub.config")) {
                Matcher m = TB_SKU_P.matcher(e.html());
                Map<String, Map<String, String>> skuMap;
                Map<String, String> skuNameMap;
                if (m.find()) {
                    BigDecimal lowPrice = BigDecimal.valueOf(Long.MAX_VALUE);
                    BigDecimal highPrice = BigDecimal.ZERO;
                    Integer totalStock = 0;
                    skuMap = JSON.parseObject(m.group(1), Map.class);
                    skuNameMap = JSON.parseObject(m.group(2), Map.class);
                    for (Map<String, String> skuInfo : skuMap.values()) {
                        BigDecimal price = new BigDecimal(skuInfo.get("price"));
                        Integer stock = Integer.valueOf(skuInfo.get("stock"));
                        if (lowPrice.compareTo(price) > 0) {
                            lowPrice = price;
                        }
                        if (highPrice.compareTo(price) < 0) {
                            highPrice = price;
                        }
                        totalStock += stock;
                    }
                    item.setHighPrice(highPrice);
                    item.setLowPrice(lowPrice);
                    item.setStock(totalStock);
                }
            }
            if (StringUtil.isEmpty(item.getContent()) && e.html().contains("g_config")) {
                Matcher m = TB_DESC_URL_P.matcher(e.html());
                if (m.find()) {
                    String content = getItemContents(m);
                    item.setContent(content);
                }
            }
        }
        return Json.SUCCESS().setData(item);
    }

    private Json importTMallItem(String tMallUrl) throws IOException {
        Item item = new Item();
        Matcher idM = ID_P.matcher(tMallUrl);
        if (idM.find()) {
            String idStr = idM.group(1);
            item.setId(Long.valueOf(idStr));
        }
        item.setTbUrl("https://detail.tmall.com/item.htm?id=" + item.getId());

        Document doc = Jsoup.connect(item.getTbUrl()).userAgent("Mozilla").get();
        Elements titleEs = doc.select(".tb-detail-hd h1");
        if (titleEs.size() > 0) {
            item.setTitle(StringUtil.substring(titleEs.first().text(), 0, 32));
        }

        Elements subTitleEs = doc.select(".tb-detail-hd .newp");
        if (subTitleEs.size() > 0) {
            item.setSubTitle(subTitleEs.first().text());
        }

        Elements attrEs = doc.select("#J_AttrUL li");
        StringBuilder attrSb = new StringBuilder();
        for (int i = 0; i < attrEs.size(); i++) {
            Element e = attrEs.get(i);
            attrSb.append(e.text()).append("\r\n");
        }
        item.setAttributes(attrSb.toString());

        Elements jsEs = doc.select("script");
        for (int i = 0; i < jsEs.size(); i++) {
            Element e = jsEs.get(i);
            if (StringUtil.isEmpty(item.getImages())) {
                Matcher m = TM_IMAGES_P.matcher(e.html());
                if (m.find()) {
                    List<String> images = new ArrayList<>();
                    List<String> list = JSON.parseArray(m.group(1), String.class);
                    for (String img : list) {
                        images.add((img.startsWith("http") ? img : "https:" + img));
                    }
                    item.setMainImg(images.get(0));
                    item.setImages(Joiner.on(',').join(images));
                }
            }
            if (e.html().contains("skuList")) {
                Matcher m = TM_SKU_P.matcher(e.html());
                JSONArray skuMap;
                Map<String, JSONObject> skuNameMap;
                if (m.find()) {
                    skuMap = JSON.parseArray(m.group(1));
                    skuNameMap = JSON.parseObject(m.group(2), Map.class);
                    BigDecimal lowPrice = BigDecimal.valueOf(Long.MAX_VALUE);
                    BigDecimal highPrice = BigDecimal.ZERO;
                    Integer totalStock = 0;
                    for (Object obj : skuNameMap.values()) {
                        JSONObject skuInfo = (JSONObject) obj;
                        BigDecimal price = skuInfo.getBigDecimal("price");
                        Integer stock = skuInfo.getInteger("stock");
                        if (lowPrice.compareTo(price) > 0) {
                            lowPrice = price;
                        }
                        if (highPrice.compareTo(price) < 0) {
                            highPrice = price;
                        }
                        totalStock += stock;
                    }
                    item.setHighPrice(highPrice);
                    item.setLowPrice(lowPrice);
                    item.setStock(totalStock);
                }
            }
            if (StringUtil.isEmpty(item.getContent()) && e.html().contains("httpsDescUrl")) {
                Matcher m = TM_DESC_URL_P.matcher(e.html());
                if (m.find()) {
                    String content = getItemContents(m);
                    item.setContent(content);
                }
            }
        }
        if (StringUtil.isEmpty(item.getImages())) {
            List<String> images = new ArrayList<>();
            Elements picEs = doc.select(".tb-gallery .tb-thumb li img");
            for (int i = 0; i < (picEs.size() > 5 ? 5 : picEs.size()); i++) {
                Element e = picEs.get(i);
                String src = e.attr("src");
                src = src.startsWith("http") ? "" : "https:" + src;
                src = StringUtil.substring(src, 0, src.indexOf(".jpg") + 4);
                images.add(src);
            }
            item.setMainImg(images.get(0));
            item.setImages(Joiner.on(',').join(images));
        }

        return Json.SUCCESS().setData(item);
    }

    public Json importTMHK(String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
        Item item = new Item();
        Matcher idM = ID_P.matcher(url);
        if (idM.find()) {
            String idStr = idM.group(1);
            item.setId(Long.valueOf(idStr));
        }
        Elements titleEs = doc.select(".tb-detail-hd h1");
        if (titleEs.size() > 0) {
            item.setTitle(titleEs.first().text());
        }

        Elements subTitleEs = doc.select(".tb-detail-hd .newp");
        if (subTitleEs.size() > 0) {
            item.setSubTitle(subTitleEs.first().text());
        } else {
            subTitleEs = doc.select(".tb-detail-hd");
            if (subTitleEs.size() > 0)
                item.setSubTitle(subTitleEs.first().children().eq(1).text());
        }

        Elements attrEs = doc.select("#J_AttrUL li");
        StringBuilder attrSb = new StringBuilder();
        for (int i = 0; i < attrEs.size(); i++) {
            Element e = attrEs.get(i);
            attrSb.append(e.text()).append("\r\n");
        }
        item.setAttributes(attrSb.toString());

        List<String> images = new ArrayList<>();
        Elements picEs = doc.select(".tb-gallery .tb-thumb li img");
        for (int i = 0; i < (picEs.size() > 5 ? 5 : picEs.size()); i++) {
            Element e = picEs.get(i);
            String src = e.attr("src");
            src = src.startsWith("http") ? "" : "https:" + src;
            src = StringUtil.substring(src, 0, src.indexOf(".jpg") + 4);
            images.add(src + "?");
        }
        item.setMainImg(images.get(0));
        item.setImages(Joiner.on(',').join(images));

        Elements jsEs = doc.select("script");
        for (int i = 0; i < jsEs.size(); i++) {
            Element e = jsEs.get(i);
            if (e.html().contains("skuList")) {
                Matcher m = TM_SKU_P.matcher(e.html());
                JSONArray skuMap;
                Map<String, JSONObject> skuNameMap;
                if (m.find()) {
                    skuMap = JSON.parseArray(m.group(1));
                    skuNameMap = JSON.parseObject(m.group(2), Map.class);
                    BigDecimal lowPrice = BigDecimal.valueOf(Long.MAX_VALUE);
                    BigDecimal highPrice = BigDecimal.ZERO;
                    Integer totalStock = 0;
                    for (Object obj : skuMap) {
                        JSONObject skuInfo = (JSONObject) obj;
                        BigDecimal price = skuInfo.getBigDecimal("price");
                        Integer stock = skuInfo.getInteger("stock");
                        if (lowPrice.compareTo(price) > 0) {
                            lowPrice = price;
                        }
                        if (highPrice.compareTo(price) < 0) {
                            highPrice = price;
                        }
                        totalStock += stock;
                    }
                    item.setHighPrice(highPrice);
                    item.setLowPrice(lowPrice);
                    item.setStock(totalStock);
                }
            }
            if (e.html().contains("httpsDescUrl")) {
                Matcher m = TMHK_DESC_URL_P.matcher(e.html());
                if (m.find()) {
                    String contents = getItemContents(m);
                    item.setContent(contents);
                }
            }
        }
        return Json.SUCCESS().setData(item);
    }

    private String getItemContents(Matcher m) throws IOException {
        String descUrl = "http:" + m.group(1);
        Document descDoc = Jsoup.connect(descUrl).ignoreContentType(true).get();
        String json = descDoc.body().html();
        Matcher descM = DESC_P.matcher(json);
        if(descM.find()) {
            String html = descM.group(1);
            return html.replaceAll("<img.*src=\"https://assets.alicdn.com/kissy/1.0.0/build/imglazyload/spaceball.gif\" />", "").replaceAll("\\Q\\\\E", "");
        } else {
            return "";
        }
    }

    private static Pattern DESC_P = Pattern.compile("var desc='([\\r|\\n|\\s|\\S]*)';");

    public static void main(String[] args) {
        String s = "<img class=\"desc_anchor\" id=\"desc-module-1\" src=\"https://assets.alicdn.com/kissy/1.0.0/build/imglazyload/spaceball.gif\" />";
        System.out.println(s.replaceAll("<img.*src=\"https://assets.alicdn.com/kissy/1.0.0/build/imglazyload/spaceball.gif\" />", ""));
    }
}