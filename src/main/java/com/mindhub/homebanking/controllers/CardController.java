package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repository.CardRepository;
import com.mindhub.homebanking.repository.ClientRepository;
import com.mindhub.homebanking.Utils.CardUtils;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
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
    private ClientService clientService;

    @Autowired
    private CardService cardService;
 
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(Authentication authentication,
                                             @RequestParam CardColor cardColor,
                                             @RequestParam CardType cardType) {
        String email = authentication.getName();
        Client client = clientService.findByEmail(email);

        if (CardUtils.controlCard(client.getCards(), cardType, cardColor)) {
            return new ResponseEntity<>("You have " + cardColor + " " + cardType, HttpStatus.FORBIDDEN);
        }

        String newCardNumber = CardUtils.generateRandomCardNumber();
        if (this.cardService.existCard(newCardNumber)) {
        return new ResponseEntity<>("Card number already exists", HttpStatus.FORBIDDEN);
    }

        Card newCard = new Card(client.getFirstName() + " " + client.getLastName(), cardType, cardColor, CardUtils.generateRandomCardNumber(), CardUtils.generateRandomCVV(), LocalDate.now(), LocalDate.now().plusYears(5));
        client.addCard(newCard);
       this.cardService.saveCard(newCard);

        return new ResponseEntity<>("Card created", HttpStatus.CREATED);
    }
    @GetMapping("/clients/current/cards")
    public ResponseEntity<Object> getClientCards(Authentication authentication) {
        String email = authentication.getName();
        Client client = clientService.findByEmail(email);

        List<CardDTO> cardDTOs = client.getCards().stream()
                .map(CardDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(cardDTOs);
    }
    }


