package io.fouad.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppLauncher {
    public static void main(String[] args) {
        var app = new SpringApplication(AppLauncher.class);
        app.run(args);
    }
}