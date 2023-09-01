package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.RoleType;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;


    private Set<AccountDTO> accounts;

    private Set<ClientLoanDTO>loans;

    private Set<CardDTO> cards = new HashSet<>();

    private String password;
    private RoleType role;

    public ClientDTO(){

    }

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toSet());
        this.loans= client.getLoans().stream().map(ClientLoanDTO::new).collect(Collectors.toSet());
        this.cards= client.getCards().stream().map(CardDTO::new).collect(Collectors.toSet());
        this.password= client.getPassword();
        this.role = client.getRole();

    }

    public long getId() {
        return id;
    }



    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public String getPassword() {
        return password;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}
