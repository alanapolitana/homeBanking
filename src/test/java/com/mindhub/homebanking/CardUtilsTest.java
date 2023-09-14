package com.mindhub.homebanking;

import com.mindhub.homebanking.Utils.CardUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CardUtilsTest {


    @Test
    public void cardNumberIsCreated() {
        String cardNumber = CardUtils.generateRandomCardNumber(9999);
        String[] sections = cardNumber.split(" ");

        assertThat(sections, arrayWithSize(4)); // Asegura que haya cuatro secciones
        for (String section : sections) {
            assertThat(section, matchesPattern("\\d{4}")); // Asegura que cada sección tenga cuatro dígitos
            // Asegura que los dígitos en cada sección sean números aleatorios en el rango de 0 a 9
            for (char digit : section.toCharArray()) {
                assertThat(digit, is(both(greaterThanOrEqualTo('0')).and(lessThanOrEqualTo('9'))));
            }
        }
    }

        @Test
        public void cvvIsGenerated() {
            Integer cvv = CardUtils.generateRandomCVV();
            assertThat(cvv, greaterThanOrEqualTo(100));
            assertThat(cvv, lessThanOrEqualTo(999));
        }














    }


