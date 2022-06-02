package com.qdstorm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ConfigApplication {
    public static void main(String[] args) throws InterruptedException {
//        SpringApplication.run(ConfigApplication.class, args);
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ConfigApplication.class, args);
        while(true){
            String userName = applicationContext.getEnvironment().getProperty("user.name");
            String userAge = applicationContext.getEnvironment().getProperty("user.age");
            String commonConfig = applicationContext.getEnvironment().getProperty("common.config");
            System.err.println("user name :"+userName+"; age: "+userAge + "; common-config: " + commonConfig);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
