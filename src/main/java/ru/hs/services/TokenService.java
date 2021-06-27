package ru.hs.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import ru.hs.dtos.AccessToken;
import ru.hs.services.config.TokenServiceProperties;

import java.time.Instant;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class TokenService {

    private AccessToken token;
    private final WebClient webClient;
    private final TokenServiceProperties properties;
    private final ObjectMapper mapper;
    private final Lock lock;

    public TokenService(TokenServiceProperties properties, ObjectMapper mapper) {
        lock = new ReentrantReadWriteLock().readLock();
        this.properties = properties;
        this.mapper = mapper;

        ClientHttpConnector connector = new ReactorClientHttpConnector();

        webClient = WebClient
                .builder()
                .filter(logRequest())
                .baseUrl(properties.getBaseUrl())
                .clientConnector(connector)
                .build();
    }

    public void authorize() {
        log.info("Обновление токена");
        String rs = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("client_id", properties.getClientId())
                        .queryParam("client_secret", properties.getClientSecret())
                        .build())
                .body(BodyInserters.fromFormData("grant_type", "client_credentials"))
                .accept(MediaType.valueOf(properties.getMediaType()))
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(throwable -> {
                    if (throwable instanceof WebClientResponseException) {
                        log.error(((WebClientResponseException) throwable).getResponseBodyAsString());
                    } else {
                        log.error(throwable.getMessage());
                    }
                })
                .doOnSuccess(response -> log.info("Received response from token api: {}", response))
                .block();
        try {
            token = mapper.readValue(rs, AccessToken.class);
            token.setExpiresAfter(Instant.now().plusSeconds(token.getExpiresIn()));
        } catch (JsonProcessingException e) {
            log.error("Ошибка парсинга ответа на запрос авторизации. Ответ : {}", rs);
            throw new RuntimeException("unexpected response from auth api", e);
        }
    }

    private boolean isTokenExpired() {
        log.info("is token expired: {}", token.getExpiresAfter().isBefore(Instant.now()));
        return false;
    }

    public String getToken() {
        try {
            lock.lock();
            if (isTokenExpired()) {
                authorize();
            }
            return token.getAccessToken();
        } finally {
            lock.unlock();
        }
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Prepared {} request by Token Service to url: {}", clientRequest.method(), clientRequest.url());
            return Mono.just(clientRequest);
        });
    }
}
