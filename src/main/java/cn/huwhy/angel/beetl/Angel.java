package cn.huwhy.angel.beetl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.markdown4j.Markdown4jProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import cn.huwhy.angel.util.StringUtil;

@Component
public class Angel {

    public static int random(int bits) {
        double j = Math.pow(10, bits);
        double x = Math.random() + 0.1;
        int y = (int) (x * j);
        return y > j ? y >> 1 : y;
    }
    private static Markdown4jProcessor markdown4jProcessor = new Markdown4jProcessor();

    public static String markdown(String content) {
        try {
            return markdown4jProcessor.process(content);
        } catch (IOException e) {
            return "error";
        }
    }

    /**
     * 获取带域名参数的访问路径
     * @param request
     * @return
     */
    public static String getUrl(String domain, HttpServletRequest request) {
        StringBuilder url = new StringBuilder(domain);
        url.append(request.getRequestURI());
        String queryString = request.getQueryString();
        if (!StringUtil.isEmpty(queryString))
            url.append("?").append(queryString);
        return url.toString();
    }

    public static void main(String[] args) {
        AntPathMatcher ant = new AntPathMatcher("/");
        System.out.println(ant.match("/mp/{id:\\d{6}}", "/mp/123456"));
    }
}
