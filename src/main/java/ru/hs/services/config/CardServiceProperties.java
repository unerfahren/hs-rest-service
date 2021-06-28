package ru.hs.services.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "service.card")
public class CardServiceProperties {
    private String baseUrl;
    private String mediaType;
    private String locale;
}
