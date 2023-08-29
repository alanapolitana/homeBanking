package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public Card createCard(Client client, CardColor color, CardType type) {

        if (client.getCards().stream().filter(card -> card.getType() == type).count() >= 3) {
            return null;  // El cliente ya tiene 3 tarjetas del mismo tipo
        }

        String cardHolder = client.getFirstName() + " " + client.getLastName();
        String number = generateRandomCardNumber();
        Integer cvv = generateRandomCVV();
        LocalDate fromDate = LocalDate.now();
        LocalDate thruDate = fromDate.plusYears(5);

        Card newCard = new Card(cardHolder, type, color, number, cvv, fromDate, thruDate);
        newCard.setClient(client);
        cardRepository.save(newCard);

        return newCard;
    }


    private String generateRandomCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();

        // Generar cuatro secciones de cuatro números aleatorios cada una
        for (int i = 0; i < 4; i++) {
            cardNumber.append(random.nextInt(10000));
            if (i < 3) {
                cardNumber.append("-");
            }
        }

        return cardNumber.toString();
    }

    private Integer generateRandomCVV() {
        Random random = new Random();
        return 100 + random.nextInt(900); // Generar un número aleatorio de tres dígitos (100-999)
    }
}
