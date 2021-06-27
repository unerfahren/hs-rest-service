package ru.hs.services.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "service.deck")
public class DeckServiceProperties {
    private String baseUrl;
    private String locale;
    private String mediaType;
}
