package cn.pzhu.dcims;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@EnableScheduling //开启定时任务
@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
public class DcimsApplication {
    public static ConfigurableApplicationContext context = null;
    public static void main(String[] args) {
        context = SpringApplication.run(DcimsApplication.class, args);
    }

}
