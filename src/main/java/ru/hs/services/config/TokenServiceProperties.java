package ru.hs.services.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "service.token")
public class TokenServiceProperties {
    private String baseUrl;
    private String mediaType;
    private String clientId;
    private String clientSecret;
    private String credentialType;
    private Map<String, String> headers;
}
