package ru.hs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebMvc
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
