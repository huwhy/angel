package cn.huwhy.angel.beetl;

import java.io.IOException;

import org.markdown4j.Markdown4jProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

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
    public static void main(String[] args) {
        AntPathMatcher ant = new AntPathMatcher("/");
        System.out.println(ant.match("/mp/{id:\\d{6}}", "/mp/123456"));
    }
}
