package team.cafehub;

import org.springframework.boot.SpringApplication;

public class TestCafeHubApplication {

    public static void main(String[] args) {
        SpringApplication.from(CafeHubApplication::main).run(args);
    }
}
