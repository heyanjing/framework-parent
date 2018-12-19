package com.demo.center;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author heyanjing
 * date:2018-12-18 2018/12/18:14:30
 */
@Slf4j
@EnableEurekaServer
@SpringBootApplication
public class CenterApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(CenterApplication.class, args);
    }
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.debug("Debugging log");
        log.info("Info log");
        log.warn("Hey, This is a warning!");
        log.error("Oops! We have an Error. OK");
    }
}
