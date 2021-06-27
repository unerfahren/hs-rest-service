package ru.hs.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Deck {
    private String deckCode;
    private Hero hero;
    private HeroPower heroPower;
    @JsonProperty("class")
    private Clazz clazz;
    private List<Card> cards;
    private int cardCount;
}