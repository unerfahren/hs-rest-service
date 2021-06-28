package ru.hs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hs.dtos.Card;
import ru.hs.services.CardService;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardsController {

    private final CardService cardService;

    @Autowired
    public CardsController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/getByName")
    public List<Card> getCardByName(@RequestParam String name) {
        return cardService.getCardByName(name);
    }
}
