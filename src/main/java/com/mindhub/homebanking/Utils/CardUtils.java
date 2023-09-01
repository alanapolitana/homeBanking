package com.mindhub.homebanking.Utils;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repository.CardRepository;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public final class CardUtils {
    public static boolean controlCard(Set<Card> cards, CardType cardType, CardColor cardColor) {
        Set<Card> matchingCards = cards.stream()
                .filter(card -> card.getType().equals(cardType) && card.getColor().equals(cardColor))
                .collect(Collectors.toSet());

        return !matchingCards.isEmpty();
    }

    public static boolean controlCard(String cardNumber, CardRepository cardRepository) {
        return cardRepository.existsByNumber(cardNumber);
    }

public static String generateRandomCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();

        // Generar cuatro secciones de cuatro números aleatorios cada una
        for (int i = 0; i < 4; i++) {
            cardNumber.append(generateRandomSection());
            if (i < 3) {
                cardNumber.append(" ");
            }
        }
        return cardNumber.toString();
    }
 public static String generateRandomSection() {
        Random random = new Random();
        StringBuilder section = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            section.append(random.nextInt(10));
        }
        return section.toString();
    }
    public static Integer generateRandomCVV() {
        Random random = new Random();
        return 100 + random.nextInt(900); // Generar un número aleatorio de tres dígitos (100-999)
    }
}
