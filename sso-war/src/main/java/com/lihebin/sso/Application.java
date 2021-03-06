package com.lihebin.sso;

/**
 * Created by lihebin on 2018/12/2.
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
//@ImportResource("classpath:spring/*.xml")
@EnableScheduling
@EnableJpaAuditing
//@EnableCaching
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}