package com.hotel.system.config.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author liuyanzhao
 * @date 2022/04/22 下午1:49
 */

@Configuration
public class MybatisPlusConfig {

    ///***
    // * plus 的性能优化
    // * @return
    // */
    //@Bean
    //public PerformanceInterceptor performanceInterceptor() {
    //    PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
    //    /*<!-- SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 -->*/
    //    performanceInterceptor.setMaxTime(1000);
    //    /*<!--SQL是否格式化 默认false-->*/
    //    performanceInterceptor.setFormat(true);
    //    return performanceInterceptor;
    //}


    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

                                                                                            public static final String TOKEN = "FYioUx6dUPw+hPAXw7YubA==";



}
