package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repository.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplements implements ClientService {
@Autowired
private ClientRepository clientRepository;


    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(ClientDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClientById(Long clientId) {
        return clientRepository.findById(clientId)
                .map(ClientDTO::new)
                .orElse(null);
    }

    @Override
    public List<AccountDTO> getClientAccounts(Long clientId) {
        return clientRepository.findById(clientId)
                .map(client -> client.getAccounts().stream()
                        .map(account -> new AccountDTO(account))
                        .collect(Collectors.toList()))
                .orElse(null);
    }

    @Override
    public Client findByEmail(String email) {
        return  clientRepository.findByEmail(email);
    }

    @Override
    public void saveClient(Client newClient) {
        clientRepository.save(newClient);
    }


}
