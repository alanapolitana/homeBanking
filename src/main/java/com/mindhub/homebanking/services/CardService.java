package com.mindhub.homebanking.services;
import com.mindhub.homebanking.models.Card;

public interface CardService {
  boolean existCard(String numberCard);
  void saveCard(Card card);
}
