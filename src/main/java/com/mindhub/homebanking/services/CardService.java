package com.mindhub.homebanking.services;
import com.mindhub.homebanking.models.*;
import java.util.Random;
import java.util.Set;
public final class CardService {
    public static boolean controlCard(Set<Card> cards, CardType cardType, CardColor cardColor)
    {
        for (Card card:cards
             ) {
            if (card.getType().equals(cardType)&&card.getColor().equals(cardColor)){
                return true;
            }
        }
        return false;
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
