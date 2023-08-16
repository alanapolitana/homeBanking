package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repository.AccountRepository;
import com.mindhub.homebanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping
    public List<AccountDTO> getAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AccountDTO getAccountById(@PathVariable Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            return null;
        }
        return new AccountDTO(account);
    }
}
