package ru.hs.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hs.services.CardService;
import ru.hs.services.DeckService;
import ru.hs.services.TokenService;
import ru.hs.services.config.CardServiceProperties;
import ru.hs.services.config.DeckServiceProperties;
import ru.hs.services.config.TokenServiceProperties;

@Configuration
@EnableConfigurationProperties({
        TokenServiceProperties.class,
        DeckServiceProperties.class,
        CardServiceProperties.class})
public class HsServiceConfiguration {

    @Bean
    public TokenService tokenService(TokenServiceProperties properties) {
        TokenService tokenService = new TokenService(properties, new ObjectMapper());
        tokenService.authorize();
        return tokenService;
    }

    @Bean
    public DeckService deckService(TokenService service, DeckServiceProperties properties) {
        return new DeckService(service, properties);
    }

    @Bean
    public CardService cardService(TokenService tokenService, CardServiceProperties properties) {
        return new CardService(tokenService, properties);
    }
}
