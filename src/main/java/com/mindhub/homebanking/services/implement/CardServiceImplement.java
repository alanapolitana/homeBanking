package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repository.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.Utils.CardUtils.generateRandomCVV;
import static com.mindhub.homebanking.Utils.CardUtils.generateRandomCardNumber;

@Service
public class CardServiceImplement implements CardService {

    @Autowired
    private CardRepository cardRepository;


    @Override
    public boolean existCard(String numberCard) {
        return  this.cardRepository.existsByNumber(numberCard);
    }

    @Override
    public void saveCard(Card card) {
        this.cardRepository.save(card);
    }
}