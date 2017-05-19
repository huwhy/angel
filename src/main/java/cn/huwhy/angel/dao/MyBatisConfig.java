package cn.huwhy.angel.dao;

import java.util.Collections;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import cn.huwhy.angel.config.DbConfig;
import cn.huwhy.angel.po.Article;
import cn.huwhy.angel.po.Category;
import cn.huwhy.angel.po.Item;
import cn.huwhy.angel.po.Link;
import cn.huwhy.angel.po.MpArticle;
import cn.huwhy.angel.po.MpConfig;
import cn.huwhy.angel.po.MpReply;
import cn.huwhy.angel.po.Param;
import cn.huwhy.angel.po.ResourceUrl;
import cn.huwhy.angel.po.Seo;
import cn.huwhy.angel.po.Shop;
import cn.huwhy.angel.po.User;
import cn.huwhy.angel.po.UserUrl;

/**
 * @author huwhy
 * @date 2016/12/14
 * @Desc
 */
@Configuration
@EnableTransactionManagement
public class MyBatisConfig implements TransactionManagementConfigurer {

    @Autowired
    private DbConfig dbConfig;

    @Bean(name = "dataSource")
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(dbConfig.getDriverClassName());
        dataSource.setUrl(dbConfig.getUrl());
        dataSource.setUsername(dbConfig.getUsername());
        dataSource.setPassword(dbConfig.getPassword());
        dataSource.setDefaultAutoCommit(dbConfig.getDefaultAutoCommit());
        dataSource.setMaxActive(dbConfig.getMaxActive());
        dataSource.setMaxIdle(dbConfig.getMaxIdle());
        dataSource.setMaxWait(dbConfig.getMaxWait());
        dataSource.setValidationQuery("select 1");
        dataSource.setConnectionInitSqls(Collections.singletonList("set names utf8mb4"));
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(true);
        dataSource.setTimeBetweenEvictionRunsMillis(3600000L);
        dataSource.setMinEvictableIdleTimeMillis(18000000L);
        return dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource());
        bean.setTypeAliasesPackage("cn.huwhy.angel.po");
        bean.setTypeAliases(new Class[]{
                User.class, Category.class, Article.class,
                MpArticle.class, MpConfig.class, Param.class, Seo.class,
                Link.class, MpReply.class, Item.class, UserUrl.class, ResourceUrl.class,
                Shop.class
        });
        bean.setConfigLocation(new ClassPathResource("cn/huwhy/angel/dao/sqlmap/sqlmap.xml"));
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath:cn/huwhy/angel/dao/sqlmap/mapping/*-mapping.xml"));
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
