package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repository.AccountRepository;
import com.mindhub.homebanking.repository.ClientRepository;
import com.mindhub.homebanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccountById(@PathVariable Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            return null;
        }
        return new AccountDTO(account);
    }
    /*@PostMapping("/clients/current/accounts")
    public ResponseEntity<String> createAccount(Authentication authentication) {
        String email = authentication.getName();
        Client client = clientRepository.findByEmail(email);

        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("Maximum number of accounts reached", HttpStatus.FORBIDDEN);
        }

        // Generate a random account number (VIN + random number)
        String accountNumber = "VIN" + String.format("%06d", (int) (Math.random() * 1000000));

        Account newAccount = new Account(accountNumber, LocalDate.now(), 0.0, client);
        client.addAccount(newAccount);
        accountRepository.save(newAccount);
        clientRepository.save(client);

        return new ResponseEntity<>("Account created", HttpStatus.CREATED);
    }*/

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        String email = authentication.getName();
        Client client = clientRepository.findByEmail(email);

        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("Maximum number of accounts reached", HttpStatus.FORBIDDEN);
        }

        // Generate a random account number (VIN + random number)
        String accountNumber = "VIN" + String.format("%06d", (int) (Math.random() * 1000000));

        Account newAccount = new Account(accountNumber, LocalDate.now(), 0.0, client);
        client.addAccount(newAccount);
        accountRepository.save(newAccount);
        clientRepository.save(client);

        return new ResponseEntity<>("Account created", HttpStatus.CREATED);
    }


    @GetMapping("/clients/current/accounts")
    public ResponseEntity<Object> getClientAccounts(Authentication authentication) {
        String email = authentication.getName();
        Client client = clientRepository.findByEmail(email);

        List<AccountDTO> accountDTOs = client.getAccounts().stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(accountDTOs);
    }

}
