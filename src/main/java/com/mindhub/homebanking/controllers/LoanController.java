package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repository.LoanRepository;
import com.mindhub.homebanking.repository.ClientLoanRepository;
import com.mindhub.homebanking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/loans/{loanId}/clients")
    public Set<Client> getClientsForLoan(@PathVariable Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElse(null);
        if (loan != null) {
            return loan.getClients().stream().map(ClientLoan::getClient).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    @GetMapping("/clients/{clientId}/clientLoans")
    public Set<Loan> getClientLoans(@PathVariable Long clientId) {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client != null) {
            return client.getClientLoans();
        }
        return Collections.emptySet();
    }
}
