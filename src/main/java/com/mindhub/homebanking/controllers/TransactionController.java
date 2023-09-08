package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Utils.TransactionUtils;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repository.AccountRepository;
import com.mindhub.homebanking.repository.ClientRepository;
import com.mindhub.homebanking.repository.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Set;
@RestController
public class TransactionController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Transactional
    @PostMapping("/api/transactions")
    public ResponseEntity<Object> createTransaction(Authentication authentication,
                                                    @RequestParam Double amount,
                                                    @RequestParam String description,
                                                    @RequestParam String fromAccountNumber,
                                                    @RequestParam String toAccountNumber) {
        String email = authentication.getName();
        Client client = clientService.findByEmail(email);

        // Validaciones de datos en la solicitud
        if (amount == null || amount < 0 || description.isBlank() || fromAccountNumber.isBlank() || toAccountNumber.isBlank()) {
            return new ResponseEntity<>("Missing data in request", HttpStatus.FORBIDDEN);
        }

        Account fromAccount = accountService.findByNumber(fromAccountNumber);
        Account toAccount = accountService.findByNumber(toAccountNumber);

        if (fromAccount == null || toAccount == null) {
            return new ResponseEntity<>("Destination account is invalid", HttpStatus.FORBIDDEN);
        }

        // Verificar si el cliente autenticado tiene acceso a la cuenta de origen
        if (!fromAccount.getClient().getId().equals(client.getId())) {
            return new ResponseEntity<>("The origin account does not belong to the authenticated client", HttpStatus.FORBIDDEN);
        }

        Set<Account> toClientAccounts = toAccount.getClient().getAccounts();
        if (!toClientAccounts.contains(toAccount)) {
            return new ResponseEntity<>("The destination account does not belong to the recipient client", HttpStatus.FORBIDDEN);
        }

        if (!TransactionUtils.availableBalance(client.getAccounts(), fromAccountNumber, amount)) {
            return new ResponseEntity<>("Insufficient balance in origin account", HttpStatus.FORBIDDEN);
        }

        // Actualizar los saldos de las cuentas
        double newBalanceOrigin = TransactionUtils.updateBalance(client.getAccounts(), fromAccountNumber, amount, TransactionType.DEBIT);
        double newBalanceDestination = TransactionUtils.updateBalance(toClientAccounts, toAccountNumber, amount, TransactionType.CREDIT);

        if (newBalanceOrigin == -5.0 || newBalanceDestination == -5.0) {
            // Revertir cambios y retornar respuesta de error
            return new ResponseEntity<>("Error in transaction", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Crear transacciones
        Transaction debitTransaction = new Transaction(TransactionType.DEBIT, amount*-1, description, LocalDateTime.now(), fromAccount);
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, amount, description, LocalDateTime.now(), toAccount);

        // Guardar transacciones
        transactionService.saveTransaction(debitTransaction);
        transactionService.saveTransaction(creditTransaction);

        // Actualizar saldos de cuentas
        accountService.saveAccount(fromAccount);
        accountService.saveAccount(toAccount);

        return new ResponseEntity<>("Transaction successful", HttpStatus.CREATED);
    }
}
