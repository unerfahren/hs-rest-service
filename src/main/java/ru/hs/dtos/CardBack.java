package ru.hs.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CardBack {
    private int id;
    private String text;
    private String name;
    private String image;
    private String slug;
}