package com.hotel.system;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <pre>
 *     SENS run!
 * </pre>
 *
 * @author : wpx
 * @date : 2022/04/14
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
@MapperScan("com.hotel.system.mapper*")
public class HotelSystemApplication {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(HotelSystemApplication.class);

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(HotelSystemApplication.class, args);
        String serverPort = context.getEnvironment().getProperty("server.port");
        log.info("Hotux started at http://localhost:" + serverPort);
    }

}
