package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repository.CardRepository;
import com.mindhub.homebanking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CardRepository cardRepository;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<String> createCard(Authentication authentication,
                                             @RequestParam CardColor cardColor,
                                             @RequestParam CardType cardType) {

        String email = authentication.getName();
        Client client = clientRepository.findByEmail(email);

        if (client.getCards().stream().filter(card -> card.getType() == cardType).count() >= 3) {
            return new ResponseEntity<>("Maximum number of cards reached for this type", HttpStatus.FORBIDDEN);
        }

        Card newCard = new Card();
        newCard.setClient(client);
        newCard.setColor(cardColor);
        newCard.setType(cardType);
        newCard.setFromDate(LocalDate.now());
        newCard.setThruDate(LocalDate.now().plusYears(5));
        newCard.setCardHolder(client.getFirstName() + " " + client.getLastName());
        newCard.setCvv(generateRandomCVV());
        newCard.setNumber(generateRandomCardNumber());

        cardRepository.save(newCard);

        return new ResponseEntity<>("Card created", HttpStatus.CREATED);
    }

    private String generateRandomCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();

        // Generar cuatro secciones de cuatro números aleatorios cada una
        for (int i = 0; i < 4; i++) {
            cardNumber.append(generateRandomSection());
            if (i < 3) {
                cardNumber.append("-");
            }
        }

        return cardNumber.toString();
    }

    private String generateRandomSection() {
        Random random = new Random();
        StringBuilder section = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            section.append(random.nextInt(10));
        }

        return section.toString();
    }

    private Integer generateRandomCVV() {
        Random random = new Random();
        return 100 + random.nextInt(900); // Generar un número aleatorio de tres dígitos (100-999)
    }

}


