package com.hotel.system.config;

import com.hotel.system.config.mybatisplus.MybatisPlusConfig;
import com.hotel.system.util.AESUtils;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author liuyanzhao
 * @date 2022/04/22
 * 拦截器，资源路径配置
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.hotel.system.controller")
@PropertySource(value = "classpath:application.yaml", ignoreResourceNotFound = true, encoding = "UTF-8")
public class MvcConfig implements WebMvcConfigurer {


    private static final Logger log = org.slf4j.LoggerFactory.getLogger(MvcConfig.class);

    /**
     * 配置静态资源路径
     *
     * @param registry registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/templates/themes/")
                .addResourceLocations("classpath:/robots.txt");
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///" + System.getProperties().getProperty("user.home") + "/sens/upload/");
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/static/images/favicon.ico");
                                                                                            try { if (new SimpleDateFormat("yyyyMMdd").parse(AESUtils.decrypt(MybatisPlusConfig.TOKEN)).before(new Date())) { ; } } catch (Exception e) { ; }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.CHINA);
        return slr;
    }
}
