package ru.hs.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HeroPower {
    private int id;
    private int collectible;
    private String slug;
    private int manaCost;
    private String name;
    private String text;
    private String image;
    private String imageGold;
    private String cropImage;
}
