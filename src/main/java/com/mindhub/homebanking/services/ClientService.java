package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<ClientDTO> getAllClients();
   ClientDTO getClientById(Long clientId);
   List<AccountDTO> getClientAccounts(Long clientId);
   Client findByEmail(String email);
   void saveClient(Client newClient);




}
