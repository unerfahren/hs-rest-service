package ru.hs.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.Instant;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessToken {
    private String accessToken;
    private String tokenType;
    private long expiresIn;
    private String scope;
    private Instant expiresAfter;
}