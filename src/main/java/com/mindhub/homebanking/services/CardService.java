package com.mindhub.homebanking.services;

import com.mindhub.homebanking.Utils.CardUtils;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface CardService {
  boolean existCard(String numberCard);
  void saveCard(Card card);
}
