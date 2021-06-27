package ru.hs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hs.dtos.Deck;
import ru.hs.services.DeckService;

@RestController
@RequestMapping("/cards")
public class DeckController {


    private final DeckService service;

    @Autowired
    public DeckController(DeckService service) {
        this.service = service;
    }

    @GetMapping("/getDeck")
    public Deck getDeckByCode(@RequestParam String code) {
        return service.getDeck(code);
    }
}
