package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repository.CardRepository;
import com.mindhub.homebanking.repository.ClientRepository;
import com.mindhub.homebanking.Utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CardRepository cardRepository;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(Authentication authentication,
                                             @RequestParam CardColor cardColor,
                                             @RequestParam CardType cardType) {
        String email = authentication.getName();
        Client client = clientRepository.findByEmail(email);

        if (CardUtils.controlCard(client.getCards(), cardType, cardColor)) {
            return new ResponseEntity<>("You have " + cardColor + " " + cardType, HttpStatus.FORBIDDEN);
        }

        Card newCard = new Card(client.getFirstName() + " " + client.getLastName(), cardType, cardColor, CardUtils.generateRandomCardNumber(), CardUtils.generateRandomCVV(), LocalDate.now(), LocalDate.now().plusYears(5));
        client.addCard(newCard);
        cardRepository.save(newCard);

        return new ResponseEntity<>("Card created", HttpStatus.CREATED);
    }
    @GetMapping("/clients/current/cards")
    public ResponseEntity<Object> getClientCards(Authentication authentication) {
        String email = authentication.getName();
        Client client = clientRepository.findByEmail(email);

        List<CardDTO> cardDTOs = client.getCards().stream()
                .map(CardDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(cardDTOs);
    }
    }


