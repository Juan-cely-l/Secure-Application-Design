package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class Secureweb {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Secureweb.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "6700"));
                app.run(args);
    }
}
