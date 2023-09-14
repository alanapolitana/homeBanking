package com.mindhub.homebanking.Utils;

import com.mindhub.homebanking.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;




@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class CardUtilsTest {

    @Autowired
    CardRepository cardRepository;


    @Test
    void generateRandomCardNumber() {


    }


    @Test
    void generateRandomCVV() {
    }
}