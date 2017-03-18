package cn.huwhy.angel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author huwhy
 * @date 2016/12/14
 * @Desc
 */
@SpringBootApplication
@EnableConfigurationProperties
public class AdminBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(AdminBootstrap.class, args);
    }
}
