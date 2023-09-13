package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repository.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private LoanServices loanServices;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientLoanService clientLoanService;

    @GetMapping("/loans/{loanId}/clients")
    public Set<Client> getClientsForLoan(@PathVariable Long loanId) {
        Loan loan = loanServices.findById(loanId);
        if (loan != null) {
            return loan.getClients().stream().map(ClientLoan::getClient).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        List<LoanDTO> loans;
        loans = this.loanServices.getAllLoans();
        return loans;
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createLoan(
            @RequestBody LoanApplicationDTO loanApplicationDTO,
            Authentication authentication) {

        String email = authentication.getName();
        Client client = clientService.findByEmail(email);

        Long loanId = loanApplicationDTO.getLoanId();
        Double amount = loanApplicationDTO.getAmount();
        Integer payments = loanApplicationDTO.getPayments();
        String toAccountNumber = loanApplicationDTO.getToAccountNumber();

        if (loanId == null || amount == null || payments == null || toAccountNumber.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (amount <= 0) {
            return new ResponseEntity<>("Amount must be positive", HttpStatus.FORBIDDEN);
        }
        if (payments <= 0) {
            return new ResponseEntity<>("Payments must be positive", HttpStatus.FORBIDDEN);
        }

        Loan loan = loanServices.findById(loanId);
        if (loan == null) {
            return new ResponseEntity<>("Loan not found", HttpStatus.FORBIDDEN);
        }

        Account destinationAccount = accountService.findByNumber(toAccountNumber);
        if (destinationAccount == null) {
            return new ResponseEntity<>("The destination account does not exist", HttpStatus.FORBIDDEN);
        }

        String authenticatedUserEmail = authentication.getName();
        if (!destinationAccount.getClient().getEmail().equals(authenticatedUserEmail)) {
            return new ResponseEntity<>("The destination account does not belong to the logged user", HttpStatus.FORBIDDEN);
        }

        if (amount > loan.getMaxAmount()) {
            return new ResponseEntity<>("The amount is greater than the maximum allowed", HttpStatus.FORBIDDEN);
        }

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.CREDIT);
        transaction.setDescription(loan.getName() + " loan approved");
        transaction.setAmount(amount*1.2);
        transaction.setDate(LocalDateTime.now());

        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        destinationAccount.addTransaction(transaction);
        transactionService.saveTransaction(transaction);

        ClientLoan clientLoan = new ClientLoan(amount,payments,client,loan);
        clientLoanService.saveClientLoan(clientLoan);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
