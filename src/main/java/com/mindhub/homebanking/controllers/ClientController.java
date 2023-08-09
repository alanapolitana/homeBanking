package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repository.ClientRepository;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream()
                .map(ClientDTO::new)  // Utiliza el constructor de ClientDTO que recibe Client
                .collect(Collectors.toList());
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClientById(@PathVariable int id) {
        return clientRepository.findById(id)
                .map(ClientDTO::new)  // Utiliza el constructor de ClientDTO que recibe Client
                .orElse(null);
    }

    @GetMapping("/clients/{id}/accounts")
    public List<AccountDTO> getClientAccounts(@PathVariable int id) {
        return clientRepository.findById(id)
                .map(client -> client.getAccounts().stream()
                        .map(account -> new AccountDTO(account))  // Crea un nuevo objeto AccountDTO utilizando el constructor que acepta un objeto Account
                        .collect(Collectors.toList()))
                .orElse(null);  // Si no se encuentra el cliente, retorna null
    }
}
