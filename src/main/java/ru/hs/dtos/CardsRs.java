package ru.hs.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardsRs {
    private List<Card> cards;
    private int cardCount;
    private int pageCount;
    private int page;
}
