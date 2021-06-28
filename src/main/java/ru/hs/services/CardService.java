package ru.hs.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.hs.dtos.Card;
import ru.hs.dtos.CardsRs;
import ru.hs.services.config.CardServiceProperties;

import java.util.List;

@Slf4j
public class CardService {

    private final WebClient webClient;
    private final TokenService tokenService;
    private final CardServiceProperties properties;
    private final ObjectMapper mapper = new ObjectMapper();

    public CardService(TokenService tokenService, CardServiceProperties properties) {
        this.tokenService = tokenService;
        this.properties = properties;
        webClient = WebClient.builder()
                .filter(logRequest())
                .baseUrl(properties.getBaseUrl())
                .build();
    }

    public List<Card> getCardByName(String name) {
        //TODO multiple names search
        String token = tokenService.getToken();
        String rs = webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("access_token", token)
                        .queryParam("locale", properties.getLocale())
                        .queryParam("textFilter", name)
                        .build())
                .accept(MediaType.valueOf(properties.getMediaType()))
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> log.info("Received response from card api: {}", response))
                .block();
        try {
            CardsRs cardsRs = mapper.readValue(rs, CardsRs.class);
            return cardsRs.getCards();
        } catch (JsonProcessingException e) {
            log.error("Unexpected response from card api: {}", rs);
            throw new RuntimeException("Response parse error", e);
        }
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Prepared {} request by Card Service to url: {}", clientRequest.method(), clientRequest.url());
            return Mono.just(clientRequest);
        });
    }
}
