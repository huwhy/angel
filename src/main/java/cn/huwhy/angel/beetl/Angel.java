package cn.huwhy.angel.beetl;

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

    public static void main(String[] args) {
        AntPathMatcher ant = new AntPathMatcher("/");
        System.out.println(ant.match("/mp/{id:\\d{6}}", "/mp/123456"));
    }
}
