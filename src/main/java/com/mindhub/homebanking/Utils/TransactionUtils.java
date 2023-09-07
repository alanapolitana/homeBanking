package com.mindhub.homebanking.Utils;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.TransactionType;

import java.util.Set;

public class TransactionUtils {

    //Verificar Saldo suficiente
    public static boolean availableBalance(Set<Account> accounts, String originNumber, double amount) {
        for (Account account : accounts) {
            if (account.getNumber().equals(originNumber) && account.getBalance() >= amount) {
                return true;
            }
        }
        return false;
    }

    //Actualizar Saldos
    public static double updateBalance(Set<Account> accounts, String originNumber, double amount, TransactionType transactionType) {
        for (Account account : accounts) {
            if (account.getNumber().equals(originNumber)) {
                if (TransactionType.CREDIT.equals(transactionType)) {
                    account.setBalance(account.getBalance() + amount);
                } else {
                    account.setBalance(account.getBalance() - amount);
                }
                return account.getBalance();
            }
        }
        return -5.0;
    }
}
