package com.mindhub.homebanking.services.implement;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repository.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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