//package com.comblife.water.server.security;
//
//import java.util.Collections;
//
//import javax.annotation.Resource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.access.AccessDecisionManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.authentication.dao.ReflectionSaltSource;
//import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
//
//import com.comblife.water.server.service.UserService;
//
///**
// * @author huwhy
// * @date 2016/12/14
// * @Desc
// */
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/template/assets/**");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests().anyRequest().authenticated().and()
//                .formLogin()
//                .loginPage("/login_by_wx.html")
//                .loginProcessingUrl("/login")
//                .failureUrl("/login_by_wx.html?error")
//                .usernameParameter("username").passwordParameter("password").and()
//                .logout().invalidateHttpSession(true).logoutUrl("login_by_wx.html?logout");
//        FilterSecurityInterceptor filterSecurityInterceptor = getApplicationContext().getBean("myFilterSecurityInterceptor", FilterSecurityInterceptor.class);
//        http.addFilterAt(filterSecurityInterceptor, FilterSecurityInterceptor.class);
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(getApplicationContext().getBean("myDaoAuthenticationProvider", DaoAuthenticationProvider.class));
//    }
//
//    @Bean(name = "myFilterSecurityInterceptor")
//    @Resource
//    public FilterSecurityInterceptor filterSecurityInterceptor(
//            AccessDecisionManager myAccessDecisionManager,
//            FilterInvocationSecurityMetadataSource myMetadataSource
//    ) {
//        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
//        filterSecurityInterceptor.setAccessDecisionManager(myAccessDecisionManager);
//        filterSecurityInterceptor.setSecurityMetadataSource(myMetadataSource);
//        return filterSecurityInterceptor;
//    }
//
//    @Bean
//    @Resource
//    public ProviderManager providerManager(DaoAuthenticationProvider daoAuthenticationProvider) {
//        return new ProviderManager(Collections.singletonList(daoAuthenticationProvider));
//    }
//
//    @Bean(name = "myDaoAuthenticationProvider")
//    @Autowired
//    public DaoAuthenticationProvider daoAuthenticationProvider(UserService userService) {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
//        daoAuthenticationProvider.setUserDetailsService(userService);
//        daoAuthenticationProvider.setPasswordEncoder(new Md5PasswordEncoder());
//        ReflectionSaltSource saltSource = new ReflectionSaltSource();
//        saltSource.setUserPropertyToUse("username");
//        daoAuthenticationProvider.setSaltSource(saltSource);
//        return daoAuthenticationProvider;
//    }
//
//}
