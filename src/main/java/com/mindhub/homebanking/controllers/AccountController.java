package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repository.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
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
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {

               return accountService.getAccounts();
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccountById(@PathVariable Long id) {
        AccountDTO account = accountService.getAccountById(id);
        if (account == null) {
            return null;
        }
        return account;
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        String email = authentication.getName();
        Client client = clientService.findByEmail(email);

        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("Maximum number of accounts reached", HttpStatus.FORBIDDEN);
        }

        // Generate a random account number (VIN + random number)
        String accountNumber = "VIN" + String.format("%06d", (int) (Math.random() * 1000000));

        Account newAccount = new Account(accountNumber, LocalDate.now(), 0.0, client);
        client.addAccount(newAccount);
        accountService.saveAccount(newAccount);
        clientService.saveClient(client);

        return new ResponseEntity<>("Account created", HttpStatus.CREATED);
    }


    @GetMapping("/clients/current/accounts")
    public ResponseEntity<Object> getClientAccounts(Authentication authentication) {
        String email = authentication.getName();
        Client client = clientService.findByEmail(email);

        List<AccountDTO> accountDTOs = client.getAccounts().stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(accountDTOs);
    }

}