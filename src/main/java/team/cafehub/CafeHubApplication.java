package team.cafehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CafeHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafeHubApplication.class, args);
    }

}
