package cn.huwhy.angel;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.beetl.core.Tag;
import org.beetl.core.TagFactory;
import org.beetl.core.resource.FileResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import cn.huwhy.angel.beetl.Angel;
import cn.huwhy.angel.beetl.BeiAn;
import cn.huwhy.angel.beetl.SiteName;
import cn.huwhy.angel.beetl.WebPageTag;
import cn.huwhy.angel.web.WebInterceptor;

/**
 * @author huwhy
 * @date 2016/12/14
 * @Desc
 */
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

    private Logger logger = LoggerFactory.getLogger(MvcConfig.class);

    public final static Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
        super.configureDefaultServletHandling(configurer);
    }

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(
                new MediaType("application", "json", UTF8),
                new MediaType("text", "xml", UTF8)));
        converters.add(converter);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        super.configurePathMatch(configurer);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(webInterceptor());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns("/admin/**");
        super.addInterceptors(registry);
    }

    @Bean
    public WebInterceptor webInterceptor() {
        return new WebInterceptor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**", "/static/**")
                .addResourceLocations("classpath:/assets/", "classpath:/static/");
    }

    @Bean(name = "beetlConfig")
    public BeetlGroupUtilConfiguration beetlGroupUtilConfiguration(
            @Value("${beetl.root}") String templatePath,
            Angel angelFunctions
    ) {
        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
        try {
            //本地环境
            if (!new File(templatePath).exists()) {
                templatePath = MvcConfig.class.getResource("/templates").getPath();
            }
            FileResourceLoader loader = new FileResourceLoader(templatePath, "utf-8");
            beetlGroupUtilConfiguration.setResourceLoader(loader);
            beetlGroupUtilConfiguration.init();
            beetlGroupUtilConfiguration.getGroupTemplate().registerFunctionPackage("angel", angelFunctions);
            beetlGroupUtilConfiguration.getGroupTemplate().registerTagFactory("web_page", pageTagFactory());
            beetlGroupUtilConfiguration.getGroupTemplate().registerTagFactory("site_name", siteNameFactory());
            beetlGroupUtilConfiguration.getGroupTemplate().registerTagFactory("bei_an", baiAnTagFactory());
            return beetlGroupUtilConfiguration;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Bean
    public TagFactory pageTagFactory() {
        return new TagFactory() {

            @Autowired
            private WebPageTag webPageTag;

            @Override
            public Tag createTag() {
                return webPageTag;
            }
        };
    }

    @Bean
    public TagFactory baiAnTagFactory() {
        return new TagFactory() {

            @Autowired
            private BeiAn beiAn;

            @Override
            public Tag createTag() {
                return beiAn;
            }
        };
    }

    @Bean
    public TagFactory siteNameFactory() {
        return new TagFactory() {

            @Autowired
            private SiteName siteName;

            @Override
            public Tag createTag() {
                return siteName;
            }
        };
    }

    @Bean
    public BeetlSpringViewResolver beetlSpringViewResolver(
            @Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        BeetlSpringViewResolver viewResolver = new BeetlSpringViewResolver();
        viewResolver.setContentType("text/html;charset=UTF-8");
        viewResolver.setOrder(0);
        viewResolver.setSuffix(".html");
        viewResolver.setConfig(beetlGroupUtilConfiguration);
        return viewResolver;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(5L * 1024 * 1024);
        resolver.setMaxUploadSizePerFile(5L * 1024 * 1024);
        return resolver;
    }

}
