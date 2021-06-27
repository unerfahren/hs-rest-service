package ru.hs.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import ru.hs.dtos.Deck;
import ru.hs.services.config.DeckServiceProperties;

@Slf4j
public class DeckService {

    private final WebClient webClient;
    private final TokenService tokenService;
    private final DeckServiceProperties properties;
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public DeckService(TokenService tokenService, DeckServiceProperties properties) {
        this.tokenService = tokenService;
        this.properties = properties;
        webClient = WebClient.builder()
                .filter(logRequest())
                .defaultHeader("media-type", properties.getMediaType())
                .baseUrl(properties.getBaseUrl())
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Prepared {} request by Deck Service to url: {}", clientRequest.method(), clientRequest.url());
            return Mono.just(clientRequest);
        });
    }

    public Deck getDeck(String code) {
        String token = tokenService.getToken();
        String rs = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("locale", properties.getLocale())
                        .queryParam("access_token", token)
                        .queryParam("code", code)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> log.info("Received response from deck api: {}", response))
                .doOnError(throwable -> {
                    if (throwable instanceof WebClientResponseException) {
                        log.error(((WebClientResponseException) throwable).getResponseBodyAsString());
                    } else {
                        log.error(throwable.getMessage());
                    }
                })
                .block();
        try {
            return mapper.readValue(rs, Deck.class);
        } catch (JsonProcessingException e) {
            log.error("Ошибка парсинга ответа на запрос авторизации. Ответ : {}", rs);
            throw new RuntimeException("unexpected response from auth api", e);
        }
    }
}
