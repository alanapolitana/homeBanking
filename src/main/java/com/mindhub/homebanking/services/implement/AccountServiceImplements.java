package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repository.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.dtos.AccountDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplements implements AccountService {

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public List<AccountDTO> getAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccountById(Long id) {
        return new AccountDTO(accountRepository.findById(id).orElse(null));

    }

    @Override
    public Account findByNumber(String fromAccountNumber) {
        return accountRepository.findByNumber(fromAccountNumber);

    }

    @Override
    public Long cont() {
        return accountRepository.count();
    }


    @Override
    public void saveAccount(Account newAccount) {
    accountRepository.save(newAccount);
    }
}
