package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.RoleType;
import com.mindhub.homebanking.repository.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/admin/clients")
    public List<ClientDTO> getAllClients() {
        return clientService.getAllClients();
    }
/*
    @PatchMapping("/admin/clients/{id}")
    public ResponseEntity<Object> makeClientAdmin(@PathVariable Long id) {
        Client client = clientService.clientById(id);
        if (client != null) {
            client.setRole(RoleType.ADMIN);
            clientService.saveClient(client);

            return new ResponseEntity<>(new ClientDTO(client), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }
    }
*/
//loan controller
    /*
    @GetMapping("/clients/{clientId}/clientLoans")
    public Set<Loan> getClientLoans(@PathVariable Long clientId) {
        ClientDTO client = clientService.getClientById(clientId);
        if (client != null) {
            return client.getClientLoans();
        }
        return Collections.emptySet();
    }*/

}
