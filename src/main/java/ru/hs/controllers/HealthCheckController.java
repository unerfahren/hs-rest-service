package ru.hs.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @GetMapping("/check")
    public ResponseEntity<String> check() {
        log.info("checking");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("alive");
    }

    @GetMapping("/monoCheck")
    public Mono<String> monoCheck() {
        log.info("monoCheck");
        return Mono.just("epta mono");
    }
}