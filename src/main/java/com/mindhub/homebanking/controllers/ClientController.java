package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.RoleType;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountService accountService;


    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/clients/{clientId}")
    public ClientDTO getClientById(@PathVariable Long clientId) {
        return clientService.getClientById(clientId);
    }

    @GetMapping("/clients/{clientId}/accounts")
    public List<AccountDTO> getClientAccounts(@PathVariable Long clientId) {
        return clientService.getClientAccounts(clientId);
    }
  /*  @GetMapping("/clients/current")
    public ResponseEntity<ClientDTO> getCurrentClient(Authentication authentication) {
        String email = authentication.getName();
        Client client = clientService.findByEmail(email);

        if (client != null) {
            ClientDTO clientDTO = new ClientDTO(client); // Utilizar el constructor con objeto Client
            return ResponseEntity.ok(clientDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
*/
  @GetMapping("/clients/current")
  public ResponseEntity<ClientDTO> getCurrentClient(Authentication authentication) {
      String email = authentication.getName();
      Client client =  this.clientService.findByEmail(email);
      if (client != null) {
          ClientDTO clientDTO = new ClientDTO(client);
          return ResponseEntity.ok(clientDTO);
      } else {
          return ResponseEntity.notFound().build();
      }
  }
    @PostMapping("/clients")
    public ResponseEntity<Object> registerClient(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            return new ResponseEntity<>("Missing data", HttpStatus.BAD_REQUEST);
        }

        if (clientService.findByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN); // Corrected code to return 403
        }
        else {

            Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password), RoleType.CLIENT);


            // Crear una cuenta para el nuevo cliente
            Account newAccount = new Account("VIN" + String.format("%03d", accountService.cont() + 1),LocalDate.now() ,0.0 , newClient);


            newClient.addAccount(newAccount); // Asociar la cuenta al cliente

            clientService.saveClient(newClient);
            accountService.saveAccount(newAccount);

            return new ResponseEntity<>("Usuario nuevo creado" ,HttpStatus.CREATED);
        }

    }
}

