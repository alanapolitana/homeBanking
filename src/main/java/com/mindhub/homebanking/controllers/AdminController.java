package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.RoleType;
import com.mindhub.homebanking.repository.ClientRepository;
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
    private ClientRepository clientRepository;

    @GetMapping("/admin/clients")
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(client -> new ClientDTO(client))
                .collect(Collectors.toList());
    }

    @PatchMapping("/admin/clients/{id}")
    public ResponseEntity<Object> makeClientAdmin(@PathVariable Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client != null) {
            client.setRole(RoleType.ADMIN);
            clientRepository.save(client);

            return new ResponseEntity<>(new ClientDTO(client), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }
    }

}
